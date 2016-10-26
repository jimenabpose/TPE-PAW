package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public class CreateProjectForm {
	private Project project;
	private String name;
	private String code;
	private String description;
	private User user;	
	private boolean is_public;
	
	public CreateProjectForm() {
	}
	
	public CreateProjectForm(Project project) {
		this.setProject(project);
		this.setCode(project.getCode());
		this.setDescription(project.getDescription());
		this.setUser(project.getLeader());
		this.setName(project.getName());
		this.setIs_public(project.isPublic());
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIs_public() {
		return is_public;
	}
	public void setIs_public(boolean is_public) {
		this.is_public = is_public;
	}
	
	public Project getRelatedProject() {
		
		if(this.getProject() == null) {
			return new Project(this.getName() ,this.getCode(),this.getDescription(),
					this.getUser(),this.isIs_public());
		} else {
			this.getProject().setName(this.getName());
			this.getProject().setCode(this.getCode());
			this.getProject().setDescription(this.getDescription());
			this.getProject().setLeader(this.getUser());
			this.getProject().setPublic(this.is_public);
			
			return this.getProject();
		}
	}
	
	
}
