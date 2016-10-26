package ar.edu.itba.it.paw.domain;

import java.util.Date;

public class Issue extends PersistentAttributes {
	
	private String code;
	private String title;
	private String description;
	private Float estimatedTime;
	private Date creationDate;
	private User reporter;
	private User assignee;
	private State state;
	private Resolution resolution;
	private Priority priority;
	private Project project;
	
	public Issue(String title, String description, Float estimatedTime, Date creationDate, User reporter,
			User assignee, Project project) throws IllegalArgumentException {
			
			this.setTitle(title);
			this.setProject(project);
			this.setDescription(description);
			this.setEstimatedTime(estimatedTime);
			this.setCreationDate(creationDate);
			this.setReporter(reporter);
			this.setAssignee(assignee);
			this.setState(State.OPEN);
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
		if(title == null || title.equals("") || title.length() > 30){
			throw new IllegalArgumentException();
		} else{
			this.title = title;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description.length() > 250){
			throw new IllegalArgumentException();
		}else{
			this.description = description;
		}
	}

	public Float getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(Float estimatedTime) {
		if(estimatedTime != null && (estimatedTime < 0 || estimatedTime >= 10000)){
			throw new IllegalArgumentException();
		}else{
			this.estimatedTime = estimatedTime;
		}
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		if(reporter == null){
			throw new IllegalArgumentException();
		} else{
			this.reporter = reporter;
		}
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		if(priority == null){
			throw new IllegalArgumentException();
		}
		this.priority = priority;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		if(project == null){
			throw new IllegalArgumentException();
		}else{
			this.project = project;
		}
	}
	
	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Issue other = (Issue) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
