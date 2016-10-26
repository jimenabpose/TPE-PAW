package ar.edu.itba.it.paw.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ar.edu.itba.it.paw.domain.Version;
import ar.edu.itba.it.paw.domain.VersionCalculations;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableVersionException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.VersionNameRepeatedException;
import ar.edu.itba.it.paw.repo.IssueRepo;
import ar.edu.itba.it.paw.repo.ProjectRepo;
import ar.edu.itba.it.paw.repo.UserRepo;
import ar.edu.itba.it.paw.web.command.CreateProjectForm;
import ar.edu.itba.it.paw.web.command.CreateVersionForm;
import ar.edu.itba.it.paw.web.command.SelectProjectForm;
import ar.edu.itba.it.paw.web.validator.CreateProjectFormValidator;
import ar.edu.itba.it.paw.web.validator.CreateVersionFormValidator;

@Controller
public class ProjectController {

	private ProjectRepo projectRepo;
	private UserRepo userRepo;
	private CreateProjectFormValidator create_project_validator;
	private CreateVersionFormValidator createVersionValidator;

	@Autowired
	public ProjectController(ProjectRepo projectRepo, IssueRepo issueRepo,
			UserRepo userRepo,
			CreateProjectFormValidator create_project_validator,
			CreateVersionFormValidator createVersionValidator) {
		this.projectRepo = projectRepo;
		this.userRepo = userRepo;
		this.create_project_validator = create_project_validator;
		this.createVersionValidator = createVersionValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView select(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.setAttribute("projectId", null);
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		/* Si es un usuario no logueado */
		List<Project> projects;
		if (user == null) {
			projects = projectRepo.getPublicProjects();
		} else if (user.isAdmin()) {
			projects = projectRepo.getAllProjects();
		} else {
			projects = projectRepo.getUserProjects(user);
			List<Project> publicProjects = projectRepo.getPublicProjects();
			for (Project project : publicProjects) {
				if (!projects.contains(project)) {
					projects.add(project);
				}
			}
		}
		mav.addObject("projects", projects);
		mav.addObject("selectProjectForm", new SelectProjectForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String select(HttpSession session, SelectProjectForm select_form) {
		session.setAttribute("projectId", select_form.getProject().getId());
		return "redirect:../user/logged_index";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(HttpSession session) {
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		Set<User> users = project.getUsers();
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
		mav.addObject("changes", projectRepo.getLastFiveIssueChanges(project));
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView consultState(HttpSession session) {
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		ModelAndView mav = new ModelAndView();
		List<Issue> issues = project.getIssues();

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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listUsers(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		User leader = null;
		if (session.getAttribute("userId") != null) {
			leader = userRepo.getUser((Integer) session.getAttribute("userId"));
		}

		Set<User> projectUsers = new HashSet<User>(project.getUsers());
		// /*saco el lider en el controller, no se justifica crear un nuevo
		// servicio sólo para sacar el lider*/
		projectUsers.remove(leader);

		List<User> notProjectUsers = userRepo.getActiveUsersNotFromProject(project);

		mav.addObject("notProjectUsers", notProjectUsers);
		mav.addObject("projectUsers", projectUsers);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView selectUser(@RequestParam("id") User user,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("forward:listUsers");
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		project.add(user);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam("id") User user,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("forward:listUsers");
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		try {
			project.delete(user);
		} catch (NotDeletableUserException e) {
			mav.addObject("error", "No se puede borrar el líder del proyecto");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView edit(HttpSession session) {
		ModelAndView mav = new ModelAndView("project/create");
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		mav.addObject("createProjectForm", new CreateProjectForm(project));
		mav.addObject("users", userRepo.getActiveUsers());

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("project/create");
		mav.addObject("createProjectForm", new CreateProjectForm());
		mav.addObject("users", userRepo.getActiveUsers());

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(HttpSession session,
			CreateProjectForm createProjectForm, Errors errors) {
		ModelAndView mav = new ModelAndView("project/create");
		mav.addObject("createProjectForm", new CreateProjectForm());
		mav.addObject("users", userRepo.getActiveUsers());

		create_project_validator.validate(createProjectForm, errors);

		if (errors.hasErrors()) {
			return mav;
		}

		Project project = createProjectForm.getRelatedProject();

		try {
			projectRepo.saveProject(project);
		} catch (ProjectCodeRepeatedException e) {
			errors.rejectValue("code", "repeated");
		} catch (ProjectNameRepeatedException e) {
			errors.rejectValue("name", "repeated");
		}

		if (errors.hasErrors()) {
			return mav;
		}

		session.setAttribute("projectId", project.getId());
		return new ModelAndView("redirect:view");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView versionView(@RequestParam("id") Version version,
			HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		VersionCalculations vc = new VersionCalculations(version.getIssues());
		
		mav.addObject("states", vc.getStates_count());
		mav.addObject("tte", vc.getTTE());
		mav.addObject("ttt", vc.getTTT());
		mav.addObject("tefv", vc.getTEFV());
		mav.addObject("version", version);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView versionNotas(@RequestParam("id") Version version,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, List<Issue>> issues_types = version.getIssuesByType();
		mav.addObject("issues_types", issues_types);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView createVersion(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("createVersionForm", new CreateVersionForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createVersion(CreateVersionForm createVersionForm,
			Errors errors, HttpSession session) {
		createVersionValidator.validate(createVersionForm, errors);

		if (errors.hasErrors()) {
			return null;
		}

		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));

		Version version = createVersionForm.getRelatedVersion();		
		
		try{
			if(version.isNew()){
				project.add(version);
			}
		}catch(VersionNameRepeatedException e){
			errors.rejectValue("verName", "repeated");
			return null;
		}
		version.setProject(project);

		return "redirect:listVersions";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listVersions(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		mav.addObject("versions", project.getVersions());
		mav.addObject("listUnreleasedVersion", false);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView deleteVersion(@RequestParam("id") Version version,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("forward:listVersions");
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		try{
			project.delete(version);
		}catch(NotDeletableVersionException e){
			mav.addObject("error", "No se puede borrar la versión");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editVersion(@RequestParam("id") Version version,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("project/createVersion");
		mav.addObject("createVersionForm", new CreateVersionForm(version));
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listUnreleasedVersions(HttpSession session) {
		ModelAndView mav = new ModelAndView("project/listVersions");
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));

		mav.addObject("versions", project.getUnreleasedVersions());
		mav.addObject("listUnreleasedVersion", true);
		return mav;
	}

}
