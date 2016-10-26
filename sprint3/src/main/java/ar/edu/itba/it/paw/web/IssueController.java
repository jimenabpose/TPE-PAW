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
import ar.edu.itba.it.paw.domain.IssueSearchCriteria;
import ar.edu.itba.it.paw.domain.IssueType;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Relation;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.CantAssignUserException;
import ar.edu.itba.it.paw.domain.exceptions.InvalidOperationException;
import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.IssueRelationException;
import ar.edu.itba.it.paw.domain.exceptions.UserCantVoteException;
import ar.edu.itba.it.paw.repo.IssueRepo;
import ar.edu.itba.it.paw.repo.ProjectRepo;
import ar.edu.itba.it.paw.repo.UserRepo;
import ar.edu.itba.it.paw.web.command.CommentForm;
import ar.edu.itba.it.paw.web.command.CreateIssueForm;
import ar.edu.itba.it.paw.web.command.IssueFilter;
import ar.edu.itba.it.paw.web.command.JobForm;
import ar.edu.itba.it.paw.web.command.JobReportForm;
import ar.edu.itba.it.paw.web.command.RelateIssueForm;
import ar.edu.itba.it.paw.web.command.ResolveIssueForm;
import ar.edu.itba.it.paw.web.validator.CommentFormValidator;
import ar.edu.itba.it.paw.web.validator.CreateIssueFormValidator;
import ar.edu.itba.it.paw.web.validator.DatesRangeValidator;
import ar.edu.itba.it.paw.web.validator.JobFormValidator;
import ar.edu.itba.it.paw.web.validator.RelateIssueFormValidator;

@Controller
public class IssueController {

	private UserRepo userRepo;
	private IssueRepo issueRepo;
	private ProjectRepo projectRepo;
	private CreateIssueFormValidator create_issue_validator;
	private DatesRangeValidator dates_range_validator;
	private JobFormValidator jobFormValidator;
	private CommentFormValidator commentFormValidator;
	private RelateIssueFormValidator relateIssueFormValidator;

	@Autowired
	public IssueController(UserRepo userRepo, IssueRepo issueRepo,
			ProjectRepo projectRepo,
			CreateIssueFormValidator create_issue_validator,
			JobFormValidator jobFormValidator,
			CommentFormValidator commentFormValidator,
			DatesRangeValidator dates_range_validator,
			RelateIssueFormValidator relateIssueFormValidator) {
		this.userRepo = userRepo;
		this.issueRepo = issueRepo;
		this.projectRepo = projectRepo;
		this.create_issue_validator = create_issue_validator;
		this.dates_range_validator = dates_range_validator;
		this.jobFormValidator = jobFormValidator;
		this.commentFormValidator = commentFormValidator;
		this.relateIssueFormValidator = relateIssueFormValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("id") Issue issue,
			HttpSession session) {
		User user = null;
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		ModelAndView mav = new ModelAndView("issue/create");
		mav.addObject("priorities", Priority.values());
		mav.addObject("issueTypes", IssueType.values());
		mav.addObject("users", project.getUsers());
		mav.addObject("versions", project.getUnreleasedVersions());
		mav.addObject("createIssueForm", new CreateIssueForm(issue, user));

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView create(HttpSession session) {

		ModelAndView mav = new ModelAndView();
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		User reporter = null;
		if (session.getAttribute("userId") != null) {
			reporter = userRepo.getUser((Integer) session
					.getAttribute("userId"));
		}

		mav.addObject("issueTypes", IssueType.values());
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", project.getUsers());
		mav.addObject("versions", project.getUnreleasedVersions());
		mav.addObject("createIssueForm", new CreateIssueForm(reporter, project));

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(HttpSession session,
			CreateIssueForm createIssueForm, Errors errors) {

		Issue issue = null;
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));

		ModelAndView mav = new ModelAndView();
		mav.addObject("issueTypes", IssueType.values());
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", project.getUsers());
		mav.addObject("createIssueForm", createIssueForm);
		mav.addObject("versions", project.getUnreleasedVersions());

		create_issue_validator.validate(createIssueForm, errors);

		if (errors.hasErrors()) {
			return mav;
		}

		try {
			issue = createIssueForm.getRelatedIssue();
			if (issue.isNew()) {
				project.addIssue(issue);
			}
		} catch (IssueNameRepeatedException e) {
			errors.rejectValue("title", "repeated");
			return mav;
		}
		if (!issue.isNew()) {
			return view(issue, session);
		}
		return new ModelAndView("redirect:list");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		ModelAndView mav = new ModelAndView();
		mav.addObject("issues", project.getIssues());
		mav.addObject("users", project.getUsers());
		mav.addObject("states", State.values());
		mav.addObject("issueTypes", IssueType.values());
		mav.addObject("resolutions", Resolution.values());
		mav.addObject("issueFilter", new IssueFilter());
		mav.addObject("job_report", new JobReportForm());
		mav.addObject("all_users", userRepo.getAllUsers());
		mav.addObject("resolutionVersions", project.getVersions());
		mav.addObject("affectedVersions", project.getVersions());

		User me = null;
		if (session.getAttribute("userId") != null) {
			me = userRepo.getUser((Integer) session.getAttribute("userId"));
			mav.addObject("am_i_leader", project.getLeader().equals(me));
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView list(IssueFilter issue_filter, Errors errors,
			HttpSession session) {
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", project.getUsers());
		mav.addObject("states", State.values());
		mav.addObject("issueTypes", IssueType.values());
		mav.addObject("resolutions", Resolution.values());
		mav.addObject("issueFilter", issue_filter);
		mav.addObject("all_users", userRepo.getAllUsers());
		mav.addObject("resolutionVersions", project.getVersions());
		mav.addObject("affectedVersions", project.getVersions());
		User me = null;
		if (session.getAttribute("userId") != null) {
			me = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		mav.addObject("am_i_leader", project.getLeader().equals(me));

		IssueSearchCriteria criteria = new IssueSearchCriteria();
		criteria.setCode(issue_filter.getCode());
		criteria.setTitle(issue_filter.getTitle());
		criteria.setDescription(issue_filter.getDescription());
		criteria.setReporter(issue_filter.getReporter());
		criteria.setAsignee(issue_filter.getAsignee());
		criteria.setState(issue_filter.getState());
		criteria.setDate_st(issue_filter.getDate_st());
		criteria.setDate_et(issue_filter.getDate_et());
		criteria.setResolution(issue_filter.getResolution());
		criteria.setIssueType(issue_filter.getIssueType());
		criteria.setResolutionVersion(issue_filter.getResolutionVersion());
		criteria.setAffectedVersion(issue_filter.getAffectedVersion());

		dates_range_validator.validate(issue_filter, errors);
		if (errors.hasErrors()) {
			issue_filter.setDate_st(null);
			issue_filter.setDate_et(null);
			mav.addObject("issues", project.getIssues());
		} else {
			mav.addObject("issues",
					issueRepo.getIssuesWithFilter(project, criteria));
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("id") Issue issue,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("/issue/view");

		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}

		List<Comment> comments = issue.getComments();
		List<Job> jobs = issue.getJobs();

		if (user != null) {

			/*
			 * Si la tarea no tiene usuario asignado le damos la posibilidad al
			 * usuario de que se ofrezca para esta tarea. De estar este a cargo
			 * se le da la opcion de que resuelva su estado.
			 */
			if (issue.getAssignee() == null
					|| !issue.getAssignee().equals(user)) {
				// no tiene a nadie asignado o no es el usuario logueado
				mav.addObject("id_for_asignation", issue.getId());
			} else {
				/*
				 * En este caso el usuario logueado esta a cargo. Puedo resolver
				 * la tarea.
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
		}

		mav.addObject("issueId", issue.getId());
		mav.addObject("hasVoted", issue.getVoters().contains(user));
		mav.addObject("issue", issue);
		mav.addObject("activeIssue", issue);
		mav.addObject("voters", issue.getVoters());
		mav.addObject("comments", comments);
		mav.addObject("jobs", jobs);
		mav.addObject("commentForm", new CommentForm(issue));
		mav.addObject("jobForm", new JobForm(issue));
		mav.addObject("resolveIssueForm", new ResolveIssueForm(issue));
		mav.addObject("versions", issue.getProject().getUnreleasedVersions());

		mav.addObject("relateIssueForm", new RelateIssueForm(issue));
		mav.addObject("issues", issue.getProject().getIssues());
		mav.addObject("relations", Relation.values());

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView changeState(@RequestParam("id") Issue issue,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("forward:view");
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		try{
			if (issue.getState().equals(State.OPEN)) {
				issue.setState(State.ONCOURSE, user);
			} else if (issue.getState().equals(State.ONCOURSE)) {
				issue.setState(State.OPEN, user);
			}
		}catch(InvalidOperationException e){
			mav.addObject("error", "No puede realizar la operación");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView asign(@RequestParam("id") Issue issue, HttpSession session) {
		ModelAndView mav = null;
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
			try {
				issue.setAssignee(user, user);
				mav = view(issue, session);
			} catch (CantAssignUserException e) {
				mav = view(issue, session);
				mav.addObject("error", "Debe pertenecer al proyecto para asignarse una tarea");
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView resolve(ResolveIssueForm issue_form, HttpSession session) {
		User loggedUser = null;
		if (session.getAttribute("userId") != null) {
			loggedUser = userRepo.getUser((Integer) session
					.getAttribute("userId"));
		}
		Issue issue = issue_form.getIssue();
		issue.resolve(issue_form.getResolution(), loggedUser);
		return new ModelAndView("redirect:view?id=" + issue.getId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView relate(HttpSession session,
			RelateIssueForm relateIssueForm, Errors errors) {
		Issue issue = relateIssueForm.getIssue();
		ModelAndView mav = new ModelAndView("redirect:view?id=" + issue.getId());
		relateIssueFormValidator.validate(relateIssueForm, errors);
		if (errors.hasErrors()) {
			return view(relateIssueForm.getIssue(), session);
		}
		try {
			issue.relate(relateIssueForm.getRelatedIssue(),
					relateIssueForm.getRelation());
		} catch (IssueRelationException e) {
			mav.addObject("error",
					"Una tarea no se puede relacionar con ella misma");
			return mav;
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView close(@RequestParam("id") Issue issue, HttpSession session) {
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}

		try {
			issue.setState(State.CLOSED, user);
		} catch(InvalidOperationException e) {
			ModelAndView mav = view(issue, session);
			mav.addObject("error", "Usted no tiene permisos para cerrar la tarea");
			return mav;
		}
		return view(issue, session);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addJob(HttpSession session, JobForm jobForm,
			Errors errors) {
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		jobFormValidator.validate(jobForm, errors);
		if (errors.hasErrors()) {
			return view(jobForm.getIssue(), session);
		}

		Job job = new Job(jobForm.getElapsedTime(), jobForm.getDescription(),
				user, new Date(), jobForm.getIssue());

		jobForm.getIssue().addJob(job);

		return new ModelAndView("redirect:view?id="
				+ jobForm.getIssue().getId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addComment(HttpSession session,
			CommentForm commentForm, Errors errors) {

		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		commentFormValidator.validate(commentForm, errors);

		if (errors.hasErrors()) {
			return view(commentForm.getIssue(), session);
		}

		Comment comment = new Comment(new Date(), commentForm.getText(), user,
				commentForm.getIssue());

		commentForm.getIssue().addComment(comment);

		return new ModelAndView("redirect:view?id="
				+ commentForm.getIssue().getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listActive(HttpSession session) {
		Project project = projectRepo.getProjectById((Integer) session
				.getAttribute("projectId"));

		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
			List<Issue> issues = issueRepo.getUserProjectActiveIssue(user,
					project);
			ModelAndView mav = new ModelAndView();
			mav.addObject("issues", issues);
			return mav;
		}
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView addVoter(@RequestParam("id") Issue issue,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("forward:view?id=" + issue.getId());
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
			try {
				issue.addVoter(user);
			} catch (UserCantVoteException e) {
				mav.addObject("votingError", "Usted no puede votar");
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView removeVoter(@RequestParam("id") Issue issue,
			HttpSession session) {
		User user = null;
		if (session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
			issue.removeVoter(user);
		}
		return new ModelAndView("redirect:view?id=" + issue.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView sumJobReport(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		int p_id = (Integer) session.getAttribute("projectId");
		Project project = projectRepo.getProjectById(p_id);

		mav.addObject("users", project.getUsers());
		mav.addObject("jobReportForm", new JobReportForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView sumJobReport(JobReportForm jobReportForm,
			Errors errors, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		int p_id = (Integer) session.getAttribute("projectId");
		Project project = projectRepo.getProjectById(p_id);

		mav.addObject("users", project.getUsers());
		mav.addObject("job_report", jobReportForm);

		/* validacion del rango de fechas */
		IssueFilter issueFilter = new IssueFilter();
		issueFilter.setDate_st(jobReportForm.getDate_st());
		issueFilter.setDate_et(jobReportForm.getDate_et());
		dates_range_validator.validate(issueFilter, errors);
		Integer sum = 0;

		if (errors.hasErrors()) {
			jobReportForm.setDate_st(null);
			jobReportForm.setDate_et(null);
		} else {
			Date st = jobReportForm.getDate_st();
			Date et = jobReportForm.getDate_et();
			
			sum = project.timeWorked(jobReportForm.getUser(), st, et);
			
			mav.addObject("duration_reported", sum);
		}
		return mav;
	}
}
