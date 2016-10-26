package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Resolution;

public class ResolveIssueForm {

	private Issue issue;
	private Resolution resolution;
	
	public ResolveIssueForm() {
		
	}
	
	public ResolveIssueForm(Issue issue) {
		this.issue = issue;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
	public Resolution getResolution() {
		return resolution;
	}
}
