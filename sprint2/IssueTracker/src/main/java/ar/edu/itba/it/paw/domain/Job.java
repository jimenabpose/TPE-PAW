package ar.edu.itba.it.paw.domain;

import java.util.Date;

public class Job extends PersistentAttributes {

	private int elapsedTime; // En horas
	private String description;
	private Date date;
	private User user;
	private Issue issue;
	
	public Job() {
		this.setDate(new Date());
	}
	
	public Job(int elapsedTime, String description, User user, Date date, 
			Issue issue) {
		this.setElapsedTime(elapsedTime);
		this.setDescription(description);
		this.setUser(user);
		this.setDate(date);
		this.setIssue(issue);
	}
	
	public int getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		if(user == null) {
			throw new IllegalArgumentException();
		}
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		if(issue == null) {
			throw new IllegalArgumentException();
		}
		this.issue = issue;
	}
	
	
	
}
