package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Issue;

public class CommentForm {

	private String text;
	private Issue issue;
	
	public CommentForm() {
	}
	
	public CommentForm(Issue issue) {
		this.issue = issue;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
}
