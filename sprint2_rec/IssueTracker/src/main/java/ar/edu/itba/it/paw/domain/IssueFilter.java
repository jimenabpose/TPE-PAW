package ar.edu.itba.it.paw.domain;


import java.util.Date;


public class IssueFilter {
	
	private String code;
	private String title;
	private String description;
	private User reporter;
	private User asignee;
	private State state;
	private Date date_st;
	private Date date_et;
	private Resolution resolution;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public User getAsignee() {
		return asignee;
	}

	public void setAsignee(User asignee) {
		this.asignee = asignee;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setDate_st(Date date_st) {
		this.date_st = date_st;
	}

	public Date getDate_st() {
		return date_st;
	}

	public void setDate_et(Date date_et) {
		this.date_et = date_et;
	}

	public Date getDate_et() {
		return date_et;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Resolution getResolution() {
		return resolution;
	}

	
	
}
