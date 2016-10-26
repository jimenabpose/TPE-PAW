package ar.edu.itba.it.paw.domain.issue;

import java.util.Date;

import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.user.User;

public class IssueSearchCriteria {
	private String code;
	private String title;
	private String description;
	private User reporter;
	private User asignee;
	private State state;
	private Date date_st;
	private Date date_et;
	private Resolution resolution;
	private IssueType issueType;
	private Version resolutionVersion; 
	private Version affectedVersion;
	
	
	public IssueType getIssueType() {
		return issueType;
	}
	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}
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
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Date getDate_st() {
		return date_st;
	}
	public void setDate_st(Date date_st) {
		this.date_st = date_st;
	}
	public Date getDate_et() {
		return date_et;
	}
	public void setDate_et(Date date_et) {
		this.date_et = date_et;
	}
	public Resolution getResolution() {
		return resolution;
	}
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	public void setResolutionVersion(Version resolutionVersion) {
		this.resolutionVersion = resolutionVersion;
	}
	public Version getResolutionVersion() {
		return resolutionVersion;
	}
	public void setAffectedVersion(Version affectedVersion) {
		this.affectedVersion = affectedVersion;
	}
	public Version getAffectedVersion() {
		return affectedVersion;
	}
}
