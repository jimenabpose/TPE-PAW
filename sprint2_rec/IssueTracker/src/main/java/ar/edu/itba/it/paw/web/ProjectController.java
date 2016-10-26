package ar.edu.itba.it.paw.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.ProjectState;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.ProjectServices;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.web.command.CreateProjectForm;
import ar.edu.itba.it.paw.web.command.SelectProjectForm;
import ar.edu.itba.it.paw.web.validator.CreateProjectFormValidator;


@Controller
public class ProjectController {
	
	private ProjectServices project_services;
	private IssueServices issue_services;
	private UserServices user_services;
	private CreateProjectFormValidator create_project_validator;
	
	@Autowired
	public ProjectController(ProjectServices project_services, IssueServices issue_services, UserServices user_services,
			CreateProjectFormValidator create_project_validator){
		this.project_services = project_services;
		this.issue_services = issue_services;
		this.user_services = user_services;
		this.create_project_validator = create_project_validator;
	}
	
	@RequestMapping()
	public ModelAndView select(HttpSession session){
		ModelAndView mav = new ModelAndView();
		session.setAttribute("project", null);
		User user = (User)session.getAttribute("user");
		/*Si es un usuario no logueado*/
		List<Project> projects = project_services.getProjectsForUser(user);
		mav.addObject("projects", projects);
		mav.addObject("selectProjectForm", new SelectProjectForm());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String select(HttpSession session, SelectProjectForm select_form){
		session.setAttribute("project", select_form.getProject());
		return "redirect:../user/logged_index";
	}
	
	@RequestMapping()
	public ModelAndView view(HttpSession session){
		List<User> users = user_services.getUsersFromProject((Project)session.getAttribute("project"));
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView consultState(HttpSession session){
		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView();
		List<Issue> issues = issue_services.getIssues(project);
		
		ProjectState projectState = new ProjectState();
		projectState.consult(issues);
		
		mav.addObject("countOpen", projectState.getCountOpen());
		mav.addObject("countOnCourse", projectState.getCountOnCourse());
		mav.addObject("countFinished", projectState.getCountFinished());
		mav.addObject("countClosed", projectState.getCountClosed());
		mav.addObject("timeOpen", projectState.getTimeOpen());
		mav.addObject("timeOnCourse", projectState.getTimeOnCourse());
		mav.addObject("timeFinished", projectState.getTimeFinished());
		mav.addObject("timeClosed", projectState.getTimeClosed());
		
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView listUsers(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Project project = (Project) session.getAttribute("project");
		User leader = (User) session.getAttribute("user");
		
		List<User> projectUsers = user_services.getUsersFromProject(project);
		/*saco el lider en el controller, no se justifica crear un nuevo servicio sólo para sacar el lider*/
		projectUsers.remove(leader);
		List<User> notProjectUsers = user_services.getUsersNotFromProject(project);
		
		mav.addObject("notProjectUsers", notProjectUsers);
		mav.addObject("projectUsers", projectUsers);
		
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView selectUser(@RequestParam("id") User user, HttpSession session){
		ModelAndView mav = new ModelAndView("forward:listUsers");
		try{
			project_services.addUserToProject(user, (Project)session.getAttribute("project"));
		}catch(DatabaseException e){
			mav.addObject("error", "El usuario ya se encuentra agregado al proyecto");
		}
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView deleteUser(@RequestParam("id") User user, HttpSession session){
		ModelAndView mav = new ModelAndView("forward:listUsers");
		try{
			project_services.deleteUserFromProject(user, (Project)session.getAttribute("project"));
		}catch(NotDeletableUserException e){
			mav.addObject("error", "No se puede borrar el líder del proyecto");
		}
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView edit(HttpSession session) {
		ModelAndView mav = new ModelAndView("project/create");
		Project project = (Project) session.getAttribute("project");
		mav.addObject("createProjectForm", new CreateProjectForm(project) );
		mav.addObject("users", user_services.getAllUsers());

		return mav;
	}
	
	@RequestMapping()
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("createProjectForm", new CreateProjectForm() );
		mav.addObject("users", user_services.getAllUsers());

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(HttpSession session, CreateProjectForm createProjectForm, Errors errors){
		ModelAndView mav = new ModelAndView();
		mav.addObject("createProjectForm", new CreateProjectForm() );
		mav.addObject("users", user_services.getAllUsers());
		
		create_project_validator.validate(createProjectForm, errors);
		
		if ( errors.hasErrors() ){
			return mav;
		}

		Project project = createProjectForm.getRelatedProject();
		
		try {
			project_services.saveProject(project);
		} catch (ProjectCodeRepeatedException e) {
			errors.rejectValue("code", "repeated");
		} catch (ProjectNameRepeatedException e) {
			errors.rejectValue("name", "repeated");
		}
		
		if(errors.hasErrors()) {
			return mav;
		}
		
		session.setAttribute("project", project);
		return new ModelAndView("redirect:view");
	}
}
