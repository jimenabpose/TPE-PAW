package ar.edu.itba.it.paw.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueAccesses;
import ar.edu.itba.it.paw.domain.IssueFilter;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Range;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Visit;
import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.ProjectServices;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.web.command.CommentForm;
import ar.edu.itba.it.paw.web.command.CreateIssueForm;
import ar.edu.itba.it.paw.web.command.JobForm;
import ar.edu.itba.it.paw.web.command.JobReportForm;
import ar.edu.itba.it.paw.web.command.RangeForm;
import ar.edu.itba.it.paw.web.command.ResolveIssueForm;
import ar.edu.itba.it.paw.web.validator.CommentFormValidator;
import ar.edu.itba.it.paw.web.validator.CreateIssueFormValidator;
import ar.edu.itba.it.paw.web.validator.DatesRangeValidator;
import ar.edu.itba.it.paw.web.validator.JobFormValidator;

@Controller
public class IssueController {
	
	private UserServices user_services;
	private IssueServices issue_services;
	private ProjectServices projectServices;
	private CreateIssueFormValidator create_issue_validator;
	private DatesRangeValidator dates_range_validator;
	private JobFormValidator jobFormValidator;
	private CommentFormValidator commentFormValidator;
	private final int PUPULAR_QUANTITY = 5;
	
	@Autowired
	public IssueController(UserServices user_services, IssueServices issue_services, ProjectServices projectServices,
			CreateIssueFormValidator create_issue_validator, 
			JobFormValidator jobFormValidator, CommentFormValidator commentFormValidator,
			DatesRangeValidator dates_range_validator){
		this.user_services = user_services;
		this.issue_services = issue_services;
		this.projectServices = projectServices;
		this.create_issue_validator = create_issue_validator;
		this.dates_range_validator = dates_range_validator;
		this.jobFormValidator = jobFormValidator;
		this.commentFormValidator = commentFormValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("id") Issue issue, HttpSession session) {

		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView("issue/create");
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", user_services.getUsersFromProject(project));
		mav.addObject("createIssueForm", new CreateIssueForm(issue));

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView create(HttpSession session) {

		ModelAndView mav = new ModelAndView();
		Project project = (Project) session.getAttribute("project");
		User reporter = (User) session.getAttribute("user");

		mav.addObject("priorities", Priority.values());
		mav.addObject("users", user_services.getUsersFromProject(project));
		mav.addObject("createIssueForm", new CreateIssueForm(reporter, project,
				null));

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(HttpSession session,
			CreateIssueForm createIssueForm, Errors errors) {

		Project project = (Project) session.getAttribute("project");
		Issue issue = null;

		ModelAndView mav = new ModelAndView();
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", user_services.getUsersFromProject(project));
		mav.addObject("createIssueForm", createIssueForm);

		create_issue_validator.validate(createIssueForm, errors);

		if(errors.hasErrors()){
			return mav;
		}

		try {
			issue = createIssueForm.getRelatedIssue();
			issue_services.saveIssue(issue);
		} catch (IssueNameRepeatedException e) {
			errors.rejectValue("title", "repeated");
			return mav;
		}
		if (issue.getId() != -1) {
			return new ModelAndView("redirect:view?id=" + issue.getId());
		}
		return new ModelAndView("redirect:list");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView sumJobReport(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Project project = (Project) session.getAttribute("project");
		mav.addObject("users", user_services.getUsersFromProject(project));
		mav.addObject("jobReportForm", new JobReportForm());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView sumJobReport(JobReportForm jobReportForm,Errors errors, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Project project = (Project) session.getAttribute("project");
		mav.addObject("users", user_services.getUsersFromProject(project));
		mav.addObject("job_report", jobReportForm);
		
		/*validacion del rango de fechas*/
		IssueFilter issueFilter = new IssueFilter();
		issueFilter.setDate_st(jobReportForm.getDate_st());
		issueFilter.setDate_et(jobReportForm.getDate_et());
		dates_range_validator.validate(issueFilter, errors);
		Integer sum = 0;
		
		if (errors.hasErrors()) {
			jobReportForm.setDate_st(null);
			jobReportForm.setDate_et(null);
			//mav.addObject("issues", issue_services.getIssuesWithFilter(project, issue_filter));
			 
		} else {
			Date st = jobReportForm.getDate_st();
			Date et = jobReportForm.getDate_et();
			sum = projectServices.projectTimeWorked(project, jobReportForm.getUser(), st, et);
			mav.addObject("duration_reported", sum);
		}
		return mav;

	}

	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView();
		mav.addObject("issues", issue_services.getIssues(project));
		mav.addObject("users", user_services.getAllUsers());
		mav.addObject("states", State.values());
		mav.addObject("resolutions", Resolution.values());
		mav.addObject("issueFilter", new IssueFilter());

		User me = (User) session.getAttribute("user");
		mav.addObject("am_i_leader", project.getLeader().equals(me));
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView list(IssueFilter issue_filter,Errors errors, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", user_services.getAllUsers());
		mav.addObject("states", State.values());
		mav.addObject("resolutions", Resolution.values());
		mav.addObject("issueFilter", issue_filter);

		User me = (User) session.getAttribute("user");
		mav.addObject("am_i_leader", project.getLeader().equals(me));

		dates_range_validator.validate(issue_filter, errors);
		if (errors.hasErrors()) {
			 issue_filter.setDate_st(null);
			 issue_filter.setDate_et(null);
			mav.addObject("issues", issue_services.getIssuesWithFilter(project, issue_filter));
			 
		} else {
			mav.addObject("issues",
					issue_services.getIssuesWithFilter(project, issue_filter));
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("id") Issue issue,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("/issue/view");
		
		User user = (User) session.getAttribute("user");
		
		if(issue != null) {
			Visit visit = new Visit(issue);
			issue_services.saveVisit(visit);
		}
		List<Comment> comments = issue_services.getIssueComments(issue);
		List<Job> jobs = issue_services.getIssueJobs(issue);

		/*
		 * Si la tarea no tiene usuario asignado le damos la posibilidad al
		 * usuario de que se ofrezca para esta tarea. De estar este a cargo se
		 * le da la opcion de que resuelva su estado.
		 */
		if (issue.getAssignee() == null || !issue.getAssignee().equals(user)) {
			// no tiene a nadie asignado o no es el usuario logueado
			mav.addObject("id_for_asignation", issue.getId());
		} else {
			/*
			 * En este caso el usuario logueado esta a cargo. Puedo resolver la
			 * tarea.
			 */
			if (issue.getState().equals(State.ONCOURSE)) {
				mav.addObject("id_for_resolution", issue.getId());
				mav.addObject("resolutions", Resolution.values());
			} else {
				mav.addObject("list_for_resolution");
			}
		}
		/*
		 * si el que esta logueado es el el lider del proyecto darle la
		 * posibilidad para cerrar la tarea
		 */
		User leader = issue.getProject().getLeader();
		if (leader != null && leader.equals(user)
				&& !issue.getState().equals(State.CLOSED)) {
			mav.addObject("id_for_closing_issue", issue.getId());
		}

		mav.addObject("changeState", 0);
		/* Cambia el estado de la tarea de OPEN a ONCOURSE y viceversa */
		if (issue.getAssignee() != null && issue.getAssignee().equals(user)) {
			mav.addObject("changeState", 1);
			if (issue.getState().equals(State.OPEN)) {
				mav.addObject("isOnCourse", 0);
			} else if (issue.getState().equals(State.ONCOURSE)) {
				mav.addObject("isOnCourse", 1);
			}
		}

		mav.addObject("issue", issue);
		mav.addObject("activeIssue", issue);
		mav.addObject("comments", comments);
		mav.addObject("jobs", jobs);
		mav.addObject("commentForm", new CommentForm(issue));
		mav.addObject("jobForm", new JobForm(issue));
		mav.addObject("resolveIssueForm", new ResolveIssueForm(issue));

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String changeState(@RequestParam("id") Issue issue) {

		if (issue.getState().equals(State.OPEN)) {
			issue.setState(State.ONCOURSE);
		} else if (issue.getState().equals(State.ONCOURSE)) {
			issue.setState(State.OPEN);
		}
		issue_services.saveIssue(issue);

		return "forward:view";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String asign(@RequestParam("id") Issue issue, HttpSession session) {
		User user = (User) session.getAttribute("user");
		user_services.asignIssue(user, issue);
		return "forward:view";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView resolve(ResolveIssueForm issue_form){
		Issue issue = issue_form.getIssue();
		issue_services.resolveIssue(issue, issue_form.getResolution());
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("message", "Tarea cerrada exitosamente");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String close(@RequestParam("id") Issue issue, HttpSession session) {
		User leader = issue.getProject().getLeader();
		User user = (User) session.getAttribute("user");

		if (leader != null && leader.equals(user)) {
			issue.setState(State.CLOSED);
			issue_services.saveIssue(issue);
		}

		// req.setAttribute("message", "La tarea se ha cerrado.");
		return "forward:view";
		// return "redirect:view?id="+issue.getId();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addJob(HttpSession session, JobForm jobForm, Errors errors){
		User user = (User) session.getAttribute("user");
		jobFormValidator.validate(jobForm, errors);
		if(errors.hasErrors()){
			return view(jobForm.getIssue(), session);
		}
		
		Job job = new Job(jobForm.getElapsedTime(), jobForm.getDescription(), 
				user, new Date(), jobForm.getIssue());
		
		issue_services.saveJob(job);
		
		return new ModelAndView("redirect:view?id=" + jobForm.getIssue().getId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addComment(HttpSession session, 
			CommentForm commentForm, Errors errors){
		

		User user = (User) session.getAttribute("user");		
		commentFormValidator.validate(commentForm, errors);
		

		if(errors.hasErrors()){
			return view(commentForm.getIssue(), session);
		}

		Comment comment = new Comment(new Date(), commentForm.getText(), user, commentForm.getIssue());
		
		issue_services.saveComment(comment);
		
		return new ModelAndView("redirect:view?id=" + commentForm.getIssue().getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listActive(HttpSession session) {
		List<Issue> issues = issue_services.getUserActiveIssues((User) session
				.getAttribute("user"));
		ModelAndView mav = new ModelAndView();
		mav.addObject("issues", issues);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listPopular(HttpSession session) {
		ModelAndView mav = new ModelAndView("issue/listPopular");
		mav.addObject("rangeForm", new RangeForm());
		mav.addObject("ranges", Range.values());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView listPopular(HttpSession session, RangeForm rangeForm) {
		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView("issue/listPopular");
		mav.addObject("rangeForm", new RangeForm());
		mav.addObject("ranges", Range.values());
		List<IssueAccesses> issueAccesses = issue_services.getPopularIssues(project, PUPULAR_QUANTITY, rangeForm.getRange());
		mav.addObject("issueAccesses", issueAccesses);
		return mav;
	}
}
