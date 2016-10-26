package ar.edu.itba.it.paw.web;

import java.util.Iterator;
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
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
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
		List<Project> projects;
		if(user == null){
			projects = project_services.getPublicProjects();
		}
		else if(user.isAdmin()){
			projects = project_services.getProjects();
		}
		else{
			projects = project_services.getUserProjects(user);
			List<Project> publicProjects = project_services.getPublicProjects();
			Iterator<Project> iterator = publicProjects.iterator();
			while(iterator.hasNext()){
				Project publicProject = (Project) iterator.next();
				if(!projects.contains(publicProject)){
					projects.add(publicProject);
				}
			}
		}
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
		int countOpen=0, countOnCourse=0, countFinished=0, countClosed=0;
		float timeOpen=0, timeOnCourse=0, timeFinished=0, timeClosed=0;
		ModelAndView mav = new ModelAndView();
		List<Issue> issues = issue_services.getIssues(project);
		
		Iterator<Issue> iterator = issues.iterator();
		
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(issue.getState().equals(State.OPEN)){
				countOpen++;
				timeOpen += issue.getEstimatedTime();
			}
			else if(issue.getState().equals(State.ONCOURSE)){
				countOnCourse++;
				timeOnCourse += issue.getEstimatedTime();
			}
			else if(issue.getState().equals(State.FINISHED)){
				countFinished++;
				timeFinished += issue.getEstimatedTime();
			}
			else{
				countClosed++;
				timeClosed += issue.getEstimatedTime();
			}
		}
		
		mav.addObject("countOpen", countOpen);
		mav.addObject("countOnCourse", countOnCourse);
		mav.addObject("countFinished", countFinished);
		mav.addObject("countClosed", countClosed);
		mav.addObject("timeOpen", timeOpen);
		mav.addObject("timeOnCourse", timeOnCourse);
		mav.addObject("timeFinished", timeFinished);
		mav.addObject("timeClosed", timeClosed);
		
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView listUsers(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Project project = (Project) session.getAttribute("project");
		List<User> allUsers = user_services.getActiveUsers();
		List<User> projectUsers = user_services.getUsersFromProject(project);
		
		/*Elimino ya están en el proyecto*/
		Iterator<User> iterator = allUsers.iterator();
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			if(projectUsers.contains(user)){
				iterator.remove();
			}
		}
		List<User> notProjectUsers = allUsers;
		mav.addObject("notProjectUsers", notProjectUsers);
		mav.addObject("projectUsers", projectUsers);
		
		return mav;
	}
	
	@RequestMapping()
	public String selectUser(@RequestParam("id") User user, HttpSession session){
		project_services.addUserToProject(user, (Project)session.getAttribute("project"));
		return "redirect:listUsers";
	}
	
	@RequestMapping()
	public String deleteUser(@RequestParam("id") User user, HttpSession session){
		project_services.deleteUserFromProject(user, (Project)session.getAttribute("project"));
		return "redirect:listUsers";
	}
	
	@RequestMapping()
	public ModelAndView edit(HttpSession session) {
		ModelAndView mav = new ModelAndView("project/create");
		Project project = (Project) session.getAttribute("project");
		mav.addObject("createProjectForm", new CreateProjectForm(project) );
		mav.addObject("users", user_services.getUsersFromProject(project));

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

		Project project = createProjectForm.getProject(createProjectForm);
		
		try {
			project_services.saveProject(project);
		} catch (Exception e) {
			errors.rejectValue("code", "repeated");
			return mav;
		}
		session.setAttribute("project", project);
		return new ModelAndView("redirect:view");
	}
}
