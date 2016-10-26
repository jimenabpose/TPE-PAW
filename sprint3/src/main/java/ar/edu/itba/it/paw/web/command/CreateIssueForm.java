package ar.edu.itba.it.paw.web.command;

import java.util.HashSet;
import java.util.Set;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueType;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Version;

public class CreateIssueForm {

	private Issue issue;
	private String title;
	private String description;
	private Float estimatedTime;
	private Priority priority;
	private User reporter;
	private User assignee;
	private Project project;
	private User loggedUser;
	private IssueType issueType;
	private Set<Version> resolutionVersions = new HashSet<Version>();
	private Set<Version> affectedVersions = new HashSet<Version>();
	
	public CreateIssueForm() {
	}
	
	public CreateIssueForm(User reporter, Project project) {
		this.setReporter(reporter);
		this.setProject(project);
		this.setLoggedUser(reporter);
	}

	public CreateIssueForm(Issue issue, User loggedUser) {
		this.setIssue(issue);
		this.setTitle(issue.getTitle());
		this.setDescription(issue.getDescription());
		this.setEstimatedTime(issue.getEstimatedTime());
		this.setReporter(issue.getReporter());
		this.setAssignee(issue.getAssignee());
		this.setPriority(issue.getPriority());
		this.setProject(issue.getProject());
		this.setIssueType(issue.getIssueType());
		this.setLoggedUser(loggedUser);
		this.setResolutionVersions(issue.getResolutionVersions());
		this.setAffectedVersions(issue.getAffectedVersions());
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
	
	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public Issue getRelatedIssue() {
		
		if(this.issue == null) {
			return new Issue(this.title, this.description, 
					this.estimatedTime, this.priority, 
					this.reporter, this.assignee, this.project, 
					this.issueType, this.resolutionVersions, this.affectedVersions);
			
		} else {
			this.issue.setTitle(this.title, loggedUser);
			this.issue.setDescription(this.description, loggedUser);
			this.issue.setEstimatedTime(this.estimatedTime, loggedUser);
			this.issue.setAssignee(this.assignee,loggedUser);
			this.issue.setIssueType(this.issueType, loggedUser);
			this.issue.setResolutionVersions(this.resolutionVersions, loggedUser);
			this.issue.setAffectedVersions(this.affectedVersions, loggedUser);
			return this.issue;
		}
	}

	public void setResolutionVersions(Set<Version> resolutionVersions) {
		this.resolutionVersions = resolutionVersions;
	}

	public Set<Version> getResolutionVersions() {
		return resolutionVersions;
	}

	public void setAffectedVersions(Set<Version> affectedVersions) {
		this.affectedVersions = affectedVersions;
	}

	public Set<Version> getAffectedVersions() {
		return affectedVersions;
	}

}
