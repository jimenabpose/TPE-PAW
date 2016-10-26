package ar.edu.itba.it.paw.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.web.command.CommentForm;
import ar.edu.itba.it.paw.web.command.CreateIssueForm;
import ar.edu.itba.it.paw.web.command.JobForm;
import ar.edu.itba.it.paw.web.command.JobReportForm;
import ar.edu.itba.it.paw.web.command.ResolveIssueForm;
import ar.edu.itba.it.paw.web.command.issueFilter;
import ar.edu.itba.it.paw.web.validator.CreateIssueFormValidator;

@Controller
public class IssueController {

	private UserServices user_services;
	private IssueServices issue_services;
	private CreateIssueFormValidator create_issue_validator;
	
	@Autowired
	public IssueController(UserServices user_services, IssueServices issue_services,
			CreateIssueFormValidator create_issue_validator){
		this.user_services = user_services;
		this.issue_services = issue_services;
		this.create_issue_validator = create_issue_validator;
	}
	
	@RequestMapping()
	public ModelAndView edit(@RequestParam("id")Issue issue) {
		
		ModelAndView mav = new ModelAndView("issue/create");
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", user_services.getAllUsers());
		mav.addObject("createIssueForm", new CreateIssueForm(issue));
		
		return mav;
	}
	
	@RequestMapping()
	public ModelAndView create(HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		Project project = (Project) session.getAttribute("project");
		User reporter = (User) session.getAttribute("user");
		
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", user_services.getAllUsers());
		mav.addObject("createIssueForm", new CreateIssueForm(reporter, project, null));
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(HttpSession session, CreateIssueForm createIssueForm, Errors errors){
		
		Issue issue = null;
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("priorities", Priority.values());
		mav.addObject("users", user_services.getAllUsers());
		mav.addObject("createIssueForm", createIssueForm);
		
		create_issue_validator.validate(createIssueForm, errors);
		
		if(errors.hasErrors()){
			if(!(errors.getErrorCount() == 1 && createIssueForm.getAssignee() == null)) {
				return mav;
			}
		}
		

		try {
			issue = createIssueForm.getIssue(createIssueForm);
			issue_services.saveIssue(issue);
		} catch (Exception e) {
			errors.rejectValue("title", "repeated");
			return mav;
		}
		if(issue.getId() != -1) {
			return new ModelAndView("redirect:view?id=" + issue.getId());
		} 
		return new ModelAndView("redirect:list");
	}
	
	
	@RequestMapping()
	public ModelAndView list(HttpSession session){
		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView();
		mav.addObject("issues", issue_services.getIssues(project));
		mav.addObject("users", user_services.getAllUsersForForm() );
		mav.addObject("states", issue_services.getStates());
		mav.addObject("resolutions", issue_services.getResolutions());
		mav.addObject("issue_filter", new issueFilter());
		mav.addObject("job_report", new JobReportForm());
		mav.addObject("all_users", user_services.getAllUsers() );
		
		User me  = (User)session.getAttribute("user");
		mav.addObject("am_i_leader", project.getLeader().equals(me));
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView list(HttpSession session, issueFilter issue_filter, JobReportForm job_report){
		Project project = (Project) session.getAttribute("project");
		ModelAndView mav = new ModelAndView();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date st = null;
		try {
			st = df.parse(issue_filter.getSt());  
			issue_filter.setSt( df.format(st) );
			issue_filter.setDate_st(st);
		} catch (ParseException e) {
			e.printStackTrace();
			issue_filter.setSt(null);
		}
		
		Date et = null;
		try {
			et = df.parse(issue_filter.getEt());  
			issue_filter.setEt( df.format(et) );
			issue_filter.setDate_et(et);
			System.out.println("ET = " + df.format(et));
		} catch (ParseException e) {
			e.printStackTrace();
			issue_filter.setEt(null);
		}
		
		if (job_report != null){
			DateFormat jdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jst = null;
			try {
				jst = jdf.parse(job_report.getSt());  
				job_report.setSt( jdf.format(jst) );
				job_report.setDate_st(jst);
			} catch (ParseException e) {
				e.printStackTrace();
				job_report.setSt(null);
			}
			
			Date jet = null;
			try {
				jet = jdf.parse(job_report.getEt());  
				job_report.setEt( jdf.format(jet) );
				job_report.setDate_et(jet);
				System.out.println("ET = " + jdf.format(jet));
			} catch (ParseException e) {
				e.printStackTrace();
				job_report.setEt(null);
			}

		}

		mav.addObject("issues", issue_services.getIssuesWithFilter(project, issue_filter));
		mav.addObject("users", user_services.getAllUsersForForm() );
		mav.addObject("states", issue_services.getStates());
		mav.addObject("resolutions", issue_services.getResolutions());
		mav.addObject("issue_filter", issue_filter);
		mav.addObject("job_report", job_report);
		mav.addObject("all_users", user_services.getAllUsers() );
		
		User me  = (User)session.getAttribute("user");
		mav.addObject("am_i_leader", project.getLeader().equals(me));
		return mav;
	}
	
	
	@RequestMapping()
	public ModelAndView view(@RequestParam("id") Issue issue, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		User user = (User) session.getAttribute("user");
		
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
		/*Cambia el estado de la tarea de OPEN a ONCOURSE y viceversa*/
		if(issue.getAssignee() != null && issue.getAssignee().equals(user)){
			mav.addObject("changeState", 1);
			if(issue.getState().equals(State.OPEN)){
				mav.addObject("isOnCourse", 0);
			}
			else if(issue.getState().equals(State.ONCOURSE)){
				mav.addObject("isOnCourse", 1);
			}
		}
		
		mav.addObject("issue", issue);
		mav.addObject("activeIssue", issue);
		mav.addObject("comments", comments);
		mav.addObject("jobs", jobs);
		mav.addObject("commentForm", new CommentForm());
		mav.addObject("jobForm", new JobForm());
		mav.addObject("resolveIssueForm", new ResolveIssueForm());

		return mav;
	}

	@RequestMapping()
	public String changeState(@RequestParam("id")Issue issue){
		
		if(issue.getState().equals(State.OPEN)){
			issue.setState(State.ONCOURSE);
		}
		else if(issue.getState().equals(State.ONCOURSE)){
			issue.setState(State.OPEN);
		}
		issue_services.saveIssue(issue);
		
		return "forward:view";
	}
	
	@RequestMapping()
	public String asign(@RequestParam("id") Issue issue, HttpSession session){
		User user = (User)session.getAttribute("user");
		user_services.asignIssue(user.getUsername(), issue);
		return "forward:view";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView resolve(ResolveIssueForm issue_form){
		Issue issue = issue_services.getIssue(issue_form.getId());
		issue.setResolution(issue_form.getResolution());
		issue.setState(State.CLOSED);
		issue_services.saveIssue(issue);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("message", "Tarea cerrada exitosamente");
		return mav;
	}
	
	@RequestMapping
	public String close(@RequestParam("id") Issue issue, HttpSession session){
		User leader = issue.getProject().getLeader();
		User user=(User)session.getAttribute("user");
		
		if ( leader != null && leader.equals(user)){
			issue.setState(State.CLOSED);
			issue_services.saveIssue(issue);
		}
		
//		req.setAttribute("message", "La tarea se ha cerrado.");
		return "forward:view";
//		return "redirect:view?id="+issue.getId();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addJob(@RequestParam("issueId") Issue issue, HttpSession session, 
			JobForm jobForm, Errors errors){
		
		User user = (User) session.getAttribute("user");
		
		Job job = new Job(jobForm.getElapsedTime(), jobForm.getDescription(), 
				user, new Date(), issue);
		
		try {
			issue_services.saveJob(job);
		} catch(Exception e) {
			return null;
		}
		return "redirect:view?id=" + issue.getId();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addComment(@RequestParam("issueId") Issue issue, HttpSession session, 
			CommentForm commentForm, Errors errors){
		
		User user = (User) session.getAttribute("user");
		
		Comment comment = new Comment(new Date(), commentForm.getText(), user, issue);
		
		try {
			issue_services.saveComment(comment);
		} catch(Exception e) {
			errors.rejectValue("text", "invalid");
			return null;
		}
		
		return "redirect:view?id=" + issue.getId();
	}
	
	@RequestMapping()
	public ModelAndView listActive(HttpSession session){
		List<Issue> issues = issue_services.getUserActiveIssues((User)session.getAttribute("user"));
		ModelAndView mav = new ModelAndView();
		mav.addObject("issues", issues);
		return mav;
	}
	
}
