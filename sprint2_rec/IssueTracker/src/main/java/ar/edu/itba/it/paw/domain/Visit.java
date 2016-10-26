package ar.edu.itba.it.paw.domain;

import java.util.Date;

import org.joda.time.LocalDate;

public class Visit extends PersistentAttributes {

	private Issue issue;
	private LocalDate date;
	
	public Visit(Issue issue) {
		this.issue = issue;
		this.date = new LocalDate();
	}
	
	public Visit(Issue issue, Date date) {
		this.issue = issue;
		this.date = new LocalDate(date);
	}

	public Issue getIssue() {
		return issue;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Visit [issue=" + issue + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((issue == null) ? 0 : issue.hashCode());
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
		Visit other = (Visit) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (issue == null) {
			if (other.issue != null)
				return false;
		} else if (!issue.equals(other.issue))
			return false;
		return true;
	}
	
}
