package ar.edu.itba.it.paw.domain.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import ar.edu.itba.it.paw.domain.PersistentAttributes;
import ar.edu.itba.it.paw.domain.ValidationUtils;
import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableVersionException;
import ar.edu.itba.it.paw.domain.exceptions.VersionNameRepeatedException;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Job;
import ar.edu.itba.it.paw.domain.user.User;

@Entity
@Table(name="projects")
public class Project extends PersistentAttributes {

	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String code;
	private String description;
	@ManyToOne
	@JoinColumn(name="leaderid")
	private User leader;
	private boolean isPublic;
	@ManyToMany
	@JoinTable(name="rel_users_projects", 
			joinColumns = @JoinColumn(name="project_fk"),
			inverseJoinColumns = @JoinColumn(name = "user_fk"))
	private Set<User> users = new HashSet<User>();
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Issue> issues = new ArrayList<Issue>();
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Version> versions = new HashSet<Version>();
	
	@SuppressWarnings("unused")
	private Project() {
		
	}
	public Project(String name, String code, String description, User leader, boolean isPublic){
		this.setName(name);
		this.setCode(code);
		this.setDescription(description);
		this.setLeader(leader);
		this.setPublic(isPublic);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		ValidationUtils.checkRequiredMaxText(name, 20);
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		ValidationUtils.checkRequiredMaxText(code, 10);
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description)  {
		ValidationUtils.checkTextMaxLength(description, 250);
		this.description = description;
	}
	
	public User getLeader() {
		return leader;
	}
	
	public void setLeader(User leader)  {
		ValidationUtils.checkNotNull(leader);
		this.add(leader);
		this.leader = leader;
	}
	
	public boolean isPublic(){
		return isPublic;
	}
	
	public void setPublic(boolean state){
		this.isPublic = state;
	}
	
	public List<Issue> getIssues(){
		return issues;
	}
	
	public void addIssue(Issue issue) {
		for (Issue i : this.issues) {
			if (i.equals(issue)) {
				throw new IssueNameRepeatedException();
			}
		}
		this.issues.add(issue);
	}
	
	public String getFullDescription(){
		String ans="";
		ans = this.code + " | " + this.name;
		return ans;
	}

	public Set<User> getUsers() {
		return users;
	}
	
	public void add(User user){
		users.add(user);
	}
	
	public boolean userBelongs(User user) {
		return this.users.contains(user);
	}
	
	public void delete(User user){
		if(this.leader.equals(user))
			throw new NotDeletableUserException();
		else
			users.remove(user);
	}

	public Set<Version> getVersions() {
		return versions;
	}
	
	public Set<Version> getUnreleasedVersions(){
		Set<Version> versions = new HashSet<Version>(this.versions);
		Iterator<Version> iterator = versions.iterator();
		
		while(iterator.hasNext()){
			Version version = (Version) iterator.next();
			if(version.getState().equals(VersionState.RELEASED)){
				iterator.remove();
			}
		}
		
		return versions;
	}
	
	public void add(Version version){
		for(Version versionl : versions){
			if(versionl.getName().equals(version.getName()))
				throw new VersionNameRepeatedException();
		}
		versions.add(version);
		version.setProject(this);
	}
	
	public void delete(Version version){
		for(Issue issue : this.issues){
			if(issue.getResolutionVersions().contains(version) || 
					issue.getAffectedVersions().contains(version)){
				throw new NotDeletableVersionException();
			}
		}
		versions.remove(version);
	}

	
	@Override
	public String toString(){
		return code + " " + name + " " + this.getId();
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
		Project other = (Project) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	/*devuelve el tiempo trabajado por el usuario en horas para el proyecto*/
	public Integer timeWorked(User user, Date st, Date et){
		Integer sum = 0;
		
		if (user == null){
			return sum;
		}
		
		for (Issue i : this.getIssues()) {
			for (Job j : i.getJobs()) {
				if (j.getUser().equals(user)
						&& (st == null || j.getDate().after(st) || j
								.getDate().equals(st))
						&& (et == null || j.getDate().before(et) || j
								.getDate().equals(et))) {

					sum += j.getElapsedTime();
				}
			}
		}
		return sum;
	}

}
