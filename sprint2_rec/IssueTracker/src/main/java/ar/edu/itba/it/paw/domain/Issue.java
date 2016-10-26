package ar.edu.itba.it.paw.domain;

import java.util.Date;

public class Issue extends PersistentAttributes {
	
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
	
	
	public Issue(String title, String description, Float estimatedTime, 
			Priority priority, User reporter, User assignee, Project project) {
			
			this.setTitle(title);
			this.setProject(project);
			this.setDescription(description);
			this.setEstimatedTime(estimatedTime);
			this.setCreationDate(new Date());
			this.setReporter(reporter);
			this.setAssignee(assignee);
			this.setState(State.OPEN);
			this.setPriority(priority);
	}
	
	public Issue(String title, String description, Float estimatedTime, Date creationDate, 
			User reporter, User assignee, State state, Resolution resolution, 
			Priority priority, Project project) {
		
			this.setTitle(title);
			this.setDescription(description);
			this.setEstimatedTime(estimatedTime);
			this.setCreationDate(creationDate);
			this.setReporter(reporter);
			this.setAssignee(assignee);
			this.setState(state);
			this.setResolution(resolution);
			this.setPriority(priority);
			this.setProject(project);
	}
	
	private String buildCode() {
		return this.getProject().getCode() + "-" + this.getId();
	}
	
	public String getCode() {
		return this.buildCode();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		ValidationUtils.checkRequiredMaxText(title, 30);
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		ValidationUtils.checkTextMaxLength(description, 250);
		this.description = description;
	}

	public Float getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(Float estimatedTime) {
		ValidationUtils.checkNumberBetween(estimatedTime, 0, 10000);
		this.estimatedTime = estimatedTime;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	private void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getReporter() {
		return reporter;
	}

	private void setReporter(User reporter) {
		ValidationUtils.checkNotNull(reporter);
		this.reporter = reporter;
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

	private void setPriority(Priority priority) {
		ValidationUtils.checkNotNull(priority);
		this.priority = priority;
	}
	
	public Project getProject() {
		return project;
	}

	private void setProject(Project project) {
		ValidationUtils.checkNotNull(project);
		this.project = project;
	}
	
	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	@Override
	public String toString() {
		return "Issue [title=" + title + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
