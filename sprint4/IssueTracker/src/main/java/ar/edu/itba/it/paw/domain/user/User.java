package ar.edu.itba.it.paw.domain.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.edu.itba.it.paw.domain.PersistentAttributes;
import ar.edu.itba.it.paw.domain.ValidationUtils;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.project.Project;

@Entity
@Table(name="users")
public class User extends PersistentAttributes{

	@Column(nullable=false, unique=true)
	private String username;
	@Column(nullable=false)
	private String firstName;
	@Column(nullable=false)
	private String lastName;
	@Column(name="pass", nullable=false)
	private String password;
	@Column(name="usertype")
	@Enumerated(EnumType.STRING)
	private Type type;
	private boolean active;
	@ManyToMany(mappedBy="followers")
	private Set<Issue> followingIssues = new HashSet<Issue>();
	
	@SuppressWarnings("unused")
	private User() {
		
	}
	
	public User(String username, String firstName, String lastName, String password, Type type, boolean active) {
		this.setUsername(username);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
		this.setType(type);
		this.setActive(active);
	}
	
	public void update(String username, String firstName, String lastName, String password) {
		this.setUsername(username);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		if(password != null && password != "") {
			this.setPassword(password);
		}
	}
	
	public String getUsername(){
		return username;
	}
	
	private void setUsername(String username){
		ValidationUtils.checkRequiredMaxText(username, 50);
		this.username = username;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	private void setFirstName(String firstName){
		ValidationUtils.checkRequiredMaxText(firstName, 50);
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	private void setLastName(String lastName) {
		ValidationUtils.checkRequiredMaxText(lastName, 50);
		this.lastName = lastName;
	}
	
	public String getPassword(){
		return password;
	}

	private void setPassword(String password){
		ValidationUtils.checkRequiredMaxText(password, 10);
		this.password = password;
	}
	
	public Type getType(){
		return type;
	}
	
	private void setType(Type type){
		this.type = type;
	}
	
	public boolean isAdmin(){
		return type.equals(Type.ADMIN);
	}
	
	public String getCompleteName() {
		return this.firstName + " " + this.lastName;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	
	public Set<Issue> getFollowingIssues() {
		return followingIssues;
	}
	
	public Set<Issue> getFollowingIssuesForProject(Project project) {
		Set<Issue> projectFollowingIssues = new HashSet<Issue>();
		for(Issue issue : this.followingIssues) {
			if(issue.getProject().equals(project)) {
				projectFollowingIssues.add(issue);
			}
		}
		return projectFollowingIssues;
	}

	private void setFollowingIssues(Set<Issue> followingIssues) {
		this.followingIssues = followingIssues;
	}
	
	public void addFolliwingIssue(Issue issue) {
		this.followingIssues.add(issue);
		if(issue.getFollowers().contains(this)) {
			issue.addFollower(this);
		}
	}
	
	public void removeFollowingIssue(Issue issue) {
		this.followingIssues.remove(issue);
		if(issue.getFollowers().contains(this)) {
			issue.removeFollower(this);
		}
	}
	
	@Override
	public String toString(){
		return username + " " + this.getId();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
