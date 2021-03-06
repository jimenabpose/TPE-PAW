package ar.edu.itba.it.paw.domain.issue;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.edu.itba.it.paw.domain.PersistentAttributes;
import ar.edu.itba.it.paw.domain.ValidationUtils;
import ar.edu.itba.it.paw.domain.user.User;

@Entity
@Table(name="jobs")
public class Job extends PersistentAttributes {

	@Column(name="duration", nullable=false)
	private Integer elapsedTime; // En horas
	@Column(nullable=false)
	private String description;
	@Column(name="resolved_date")
	private Date date;
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User user;
	@ManyToOne
	@JoinColumn(name="issue_fk")
	private Issue issue;
	
	@SuppressWarnings("unused")
	private Job() {
		
	}
	public Job(int elapsedTime, String description, User user, Date date, 
			Issue issue) {
		this.setElapsedTime(elapsedTime);
		this.setDescription(description);
		this.setUser(user);
		this.setDate(date);
		this.setIssue(issue);
		issue.addJob(this);
	}
	
	public Integer getElapsedTime() {
		return elapsedTime;
	}
	private void setElapsedTime(Integer elapsedTime) {
		ValidationUtils.checkNotNullAndGreaterThanZero(elapsedTime);
		this.elapsedTime = elapsedTime;
	}
	public String getDescription() {
		return description;
	}
	private void setDescription(String description) {
		ValidationUtils.checkRequiredMaxText(description, 250);
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	private void setUser(User user) {
		ValidationUtils.checkNotNull(user);
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	private void setDate(Date date) {
		this.date = date;
	}
	public Issue getIssue() {
		return issue;
	}
	private void setIssue(Issue issue) {
		ValidationUtils.checkNotNull(issue);
		this.issue = issue;
	}
}
