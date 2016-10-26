package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Issue;

public class JobForm {

	private Integer elapsedTime;
	private String description;
	private Issue issue;
	
	public JobForm() {
	}
	
	public JobForm(Issue issue) {
		this.issue = issue;
	}
	
	public Integer getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	
}
