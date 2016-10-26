package ar.edu.itba.it.paw.web.command;


import java.util.Date;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Version;

public class IssueFilter {
	
	private String code;
	private String title;
	private String description;
	private User reporter;
	private User asignee;
	private String state;
	private String st;
	private String et;
	private Date date_st;
	private Date date_et;
	private String resolution;
	private String issueType;
	private Version resolutionVersion;
	private Version affectedVersion;
	
	
	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
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

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
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

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getResolution() {
		return resolution;
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
