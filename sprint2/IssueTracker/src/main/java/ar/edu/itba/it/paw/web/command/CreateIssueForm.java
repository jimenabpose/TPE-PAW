package ar.edu.itba.it.paw.web.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;

public class CreateIssueForm {

	private int id;
	private String title;
	private String description;
	private Float estimatedTime;
	private Date creationDate;
	private User reporter;
	private User assignee;
	private State state;
	private Resolution resolution;
	private Priority priority;
	private Project project;
	
	public CreateIssueForm() {
		this.setId(-1);
		this.setState(State.OPEN);
	}
	
	public CreateIssueForm(User reporter, Project project, Date creationDate) {
		this.setId(-1);
		this.setReporter(reporter);
		this.setProject(project);
		this.setCreationDate(creationDate);
		this.setState(State.OPEN);
	}
	
	public CreateIssueForm(int id, String title, String description, Float estimatedTime, Date creationDate, 
			User reporter, User assignee, State state, Resolution resolution, 
			Priority priority, Project project) {
		
		this.setId(id);
		this.setTitle(title);
		this.setDescription(description);
		this.setEstimatedTime(estimatedTime);
		this.setCreationDate(creationDate);
		this.setReporter(reporter);
		this.setAssignee(assignee);
		this.setState(state);
		this.setResolution(resolution);
		this.setPriority(priority);
		this.setProject(project);
		
	}
	
	public CreateIssueForm(Issue issue) {
		this.setId(issue.getId());
		this.setTitle(issue.getTitle());
		this.setDescription(issue.getDescription());
		this.setEstimatedTime(issue.getEstimatedTime());
		this.setCreationDate(issue.getCreationDate());
		this.setReporter(issue.getReporter());
		this.setAssignee(issue.getAssignee());
		this.setState(issue.getState());
		this.setResolution(issue.getResolution());
		this.setPriority(issue.getPriority());
		this.setProject(issue.getProject());
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(Float estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public User getAssignee() {
		return assignee;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Issue getIssue(CreateIssueForm createIssueForm) {
		Date creationDate = new Date();
		
		if(createIssueForm.getId() != -1) {
			creationDate = createIssueForm.getCreationDate();
		}
		
		Issue issue = new Issue(createIssueForm.getTitle(), createIssueForm.getDescription(),
				createIssueForm.getEstimatedTime(), creationDate, createIssueForm.getReporter(),
				createIssueForm.getAssignee(), createIssueForm.getState(), 
				createIssueForm.getResolution(), createIssueForm.getPriority(), createIssueForm.getProject());
		issue.setId(createIssueForm.getId());
		issue.setCode(issue.getProject().getCode() + "-" + issue.getId());
		System.out.println(5);
		return issue;
	}
	
}
