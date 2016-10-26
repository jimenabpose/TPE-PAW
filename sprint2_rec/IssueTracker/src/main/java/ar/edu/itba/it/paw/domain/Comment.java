package ar.edu.itba.it.paw.domain;

import java.util.Date;

public class Comment extends PersistentAttributes {

	private Date creationDate;
	private String text;
	private User user;
	private Issue issue;
	
	public Comment(Date creationDate, String text, User user, Issue issue) {
		this.setCreationDate(creationDate);
		this.setText(text);
		this.setUser(user);
		this.setIssue(issue);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	private void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getText() {
		return text;
	}

	private void setText(String text) {
		ValidationUtils.checkRequiredMaxText(text, 250);
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {
		ValidationUtils.checkNotNull(user);
		this.user = user;
	}

	public Issue getIssue() {
		return issue;
	}

	private void setIssue(Issue issue) {
		ValidationUtils.checkNotNull(issue);
		this.issue = issue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((issue == null) ? 0 : issue.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (issue == null) {
			if (other.issue != null)
				return false;
		} else if (!issue.equals(other.issue))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
