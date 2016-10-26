package ar.edu.itba.it.paw.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class IssueChanges extends PersistentAttributes {

	private String changer;
	private Date changeDate;
	private String field;
	private String oldValue;
	private String newValue;
	@ManyToOne
	private Issue issue;
	
	@SuppressWarnings("unused")
	private IssueChanges() {
		
	}
	
	public IssueChanges(String field, String oldValue, String newValue, 
			String changer, Issue issue) {
		this.changeDate = new Date();
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.changer = changer;
		this.issue = issue;
	}
	
	public String getChanger() {
		return changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	
}
