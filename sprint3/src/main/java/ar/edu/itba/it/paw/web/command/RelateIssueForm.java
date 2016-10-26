package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Relation;

public class RelateIssueForm {

	private Issue issue;
	private Issue relatedIssue;
	private Relation relation;
	private String relationError;
	
	public RelateIssueForm() {
		
	}
	
	public RelateIssueForm(Issue issue) {
		this.issue = issue;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	
	public Relation getRelation() {
		return this.relation;
	}

	public Issue getRelatedIssue() {
		return relatedIssue;
	}

	public void setRelatedIssue(Issue relatedIssue) {
		this.relatedIssue = relatedIssue;
	}

	public String getRelationError() {
		return relationError;
	}

	public void setRelationError(String relationError) {
		this.relationError = relationError;
	}
	
	
}
