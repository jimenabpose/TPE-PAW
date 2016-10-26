package ar.edu.itba.it.paw.web.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.User;

public class JobReportForm {
	private User user;
	private String st;
	private String et;
	private Date date_st;
	private Date date_et;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	
}
