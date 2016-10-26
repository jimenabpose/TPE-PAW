package ar.edu.itba.it.paw.web.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public class CreateIssueForm {

	private Issue issue;
	private String title;
	private String description;
	private Float estimatedTime;
	private Priority priority;
	private User reporter;
	private User assignee;
	private Project project;

	public CreateIssueForm() {
	}
	
	public CreateIssueForm(User reporter, Project project, Date creationDate) {
		this.setReporter(reporter);
		this.setProject(project);
	}

	public CreateIssueForm(Issue issue) {
		this.setIssue(issue);
		this.setTitle(issue.getTitle());
		this.setDescription(issue.getDescription());
		this.setEstimatedTime(issue.getEstimatedTime());
		this.setReporter(issue.getReporter());
		this.setAssignee(issue.getAssignee());
		this.setPriority(issue.getPriority());
		this.setProject(issue.getProject());

	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
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

	public Issue getRelatedIssue() {
		
		if(this.getIssue() == null) {
			return new Issue(this.getTitle(), this.getDescription(), 
					this.getEstimatedTime(), this.getPriority(), 
					this.getReporter(), this.getAssignee(), this.getProject());
			
		} else {
			this.getIssue().setTitle(this.getTitle());
			this.getIssue().setDescription(this.getDescription());
			this.getIssue().setEstimatedTime(this.getEstimatedTime());
			this.getIssue().setAssignee(this.getAssignee());
			return this.getIssue();
		}
	}
}
