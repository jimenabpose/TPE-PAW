package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public class CreateProjectForm {
	private int id;
	private String name;
	private String code;
	private String description;
	private User user;	
	private boolean is_public;
	
	public CreateProjectForm() {
		this.setId(-1);
	}
	
	public CreateProjectForm(Project project) {
		this.setId(project.getId());
		this.setCode(project.getCode());
		this.setDescription(project.getDescription());
		this.setUser(project.getLeader());
		this.setName(project.getName());
		this.setIs_public(project.isPublic());
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public Project getProject(CreateProjectForm createProjectForm) {
		
		Project project = new Project(
				createProjectForm.getName() ,
				createProjectForm.getCode(),
				createProjectForm.getDescription(),
				createProjectForm.getUser(),
				createProjectForm.isIs_public()
				);
		project.setId(createProjectForm.getId());
		
		return project;
	}
	
	
}
