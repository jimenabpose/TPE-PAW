package ar.edu.itba.it.paw.domain.issue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;
import ar.edu.itba.it.paw.domain.PersistentAttributes;
import ar.edu.itba.it.paw.domain.ValidationUtils;
import ar.edu.itba.it.paw.domain.exceptions.CantAssignUserException;
import ar.edu.itba.it.paw.domain.exceptions.InvalidOperationException;
import ar.edu.itba.it.paw.domain.exceptions.IssueRelationException;
import ar.edu.itba.it.paw.domain.exceptions.UserCantVoteException;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.domain.user.User;


@Entity
@Table(name="issues")
public class Issue extends PersistentAttributes {

	@Column(nullable=false)
	private String title;
	private String description;
	private Float estimatedTime;
	private Date creationDate;
	@ManyToOne
	@JoinColumn(name="reporterid")
	private User reporter;
	@ManyToOne
	@JoinColumn(name="assigneeid")
	private User assignee;
	@Enumerated(EnumType.STRING)
	private State state;
	@Enumerated(EnumType.STRING)
	private Resolution resolution;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	@Enumerated(EnumType.STRING)
	private IssueType issueType;
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "issue")
	private List<Comment> comments = new ArrayList<Comment>();
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "issue")
	private List<Job> jobs = new ArrayList<Job>();
	@ManyToMany
	@JoinTable(name="issues_resversion", 
			joinColumns = @JoinColumn(name="issue_fk"),
			inverseJoinColumns = @JoinColumn(name = "version_fk"))
	private Set<Version> resolutionVersions = new HashSet<Version>();
	@ManyToMany
	@JoinTable(name="issues_affversion", 
			joinColumns = @JoinColumn(name="issue_fk"),
			inverseJoinColumns = @JoinColumn(name = "version_fk"))
	private Set<Version> affectedVersions = new HashSet<Version>();
	@OneToMany
	@JoinColumn(name="voterId")
	private Set<User> voters = new HashSet<User>();
	@CollectionOfElements
	private Set<Relation> relations = new HashSet<Relation>();
	@OneToMany(cascade=CascadeType.ALL, mappedBy="issue")
	private List<IssueChanges> changes = new ArrayList<IssueChanges>();
	@ManyToMany
	@JoinTable(name="issues_followers", 
			joinColumns = @JoinColumn(name="issue_fk"),
			inverseJoinColumns = @JoinColumn(name = "user_fk"))
	private Set<User> followers = new HashSet<User>();
	
	@SuppressWarnings("unused")
	private Issue() {
		
	}
	
	public Issue(String title, String description, Float estimatedTime, 
			Priority priority, User reporter, User assignee, 
			Project project, IssueType issueType, Set<Version> resolutionVersions, Set<Version> affectedVersions) {
			
			this.setTitle(title);
			this.setProject(project);
			this.setDescription(description);
			this.setEstimatedTime(estimatedTime);
			this.setCreationDate(new Date());
			this.setReporter(reporter);
			this.setAssignee(assignee);
			this.setState(State.OPEN);
			this.setPriority(priority);
			this.setCreationDate(new Date());
			this.setIssueType(issueType);
			this.setResolutionVersions(resolutionVersions);
			this.setAffectedVersions(affectedVersions);
			project.addIssue(this);
	}
	
	public int compareTo(Issue issue){
		return this.getCode().compareTo(issue.getCode());
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

	public void setTitle(String title, User loggedUser) {
		ValidationUtils.checkRequiredMaxText(title, 30);
		if(loggedUser != null) {
			if(!this.title.equals(title)) {
				this.registerChange(new IssueChanges("Título", this.title, title,
						loggedUser.getCompleteName(), this));
			}
		}
		this.setTitle(title);
	}
	
	private void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description, User loggedUser) {
		ValidationUtils.checkTextMaxLength(description, 250);
		if(loggedUser != null) {
			if(!this.description.equals(description)) {
				this.registerChange(new IssueChanges("Descripción", this.description, 
						description, loggedUser.getCompleteName(), this));
			}
		}
		this.setDescription(description);
	}
	
	private void setDescription(String description) {
		this.description = description;
	}

	public Float getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(Float estimatedTime, User loggedUser) {
		ValidationUtils.checkNumberBetween(estimatedTime, null, 10000);
		if(loggedUser != null) {
			if((this.estimatedTime != null && !this.estimatedTime.equals(estimatedTime))
					|| (this.estimatedTime != null && estimatedTime == null) ||
					(this.estimatedTime == null && estimatedTime != null)) {
				this.registerChange(
						new IssueChanges("Tiempo Estimado", 
						this.estimatedTime ==  null? "" : String.valueOf(this.estimatedTime), 
							estimatedTime == null? "" : String.valueOf(estimatedTime), 
							loggedUser.getCompleteName(), this));
			}
		}
		this.setEstimatedTime(estimatedTime);
	}
	
	private void setEstimatedTime(Float estimatedTime) {
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

	public void setAssignee(User assignee, User loggedUser) {
		if(assignee !=  null && !this.project.getUsers().contains(assignee)) {
			throw new CantAssignUserException();
		}
		if(loggedUser != null) {
			if((this.assignee != null && !this.assignee.equals(assignee)) ||
					(this.assignee == null && assignee != null) ||
					(this.assignee != null && assignee == null)) {
				this.registerChange(
						new IssueChanges("Usuario asignado", 
								this.assignee == null? "" : this.assignee.getCompleteName(), 
										assignee == null? "" : assignee.getCompleteName(),
												loggedUser.getCompleteName(), this));
			}
		}
		this.setAssignee(assignee);
	}
	
	private void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public State getState() {
		return state;
	}

	public void setState(State state, User loggedUser) {
		if(loggedUser != null) {
			if(this.state == null || !this.state.equals(state)) {
				this.registerChange(
						new IssueChanges("Estado", 
								this.state == null? "" : this.state.getName(), 
										state.getName(), loggedUser.getCompleteName(), this));
			}
		}
		
		if((state.equals(State.OPEN) || state.equals(State.ONCOURSE)) && 
				!loggedUser.equals(this.assignee)) {
			throw new InvalidOperationException();
		}
		
		if(state.equals(State.CLOSED) && !loggedUser.equals(this.getProject().getLeader())) {
			throw new InvalidOperationException();
		}
		
		this.setState(state);
	}
	
	private void setState(State state) {
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

	private void setResolution(Resolution resolution, User loggedUser) {
		if(loggedUser != null) {
			if(this.resolution == null || !this.resolution.equals(resolution)) {
				this.registerChange(
						new IssueChanges("Resolución", 
								this.resolution == null? "" : this.resolution.getName(), 
										resolution.getName(), loggedUser.getCompleteName(), this));
			}
		}
		this.resolution = resolution;
	}
	
	public List<Job> getJobs() {
		return this.jobs;
	}
	
	public void addJob(Job job) {
		this.jobs.add(job);
	}
	
	public List<Comment> getComments() {
		return this.comments;
	}
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public void resolve(Resolution resolution, User loggedUser) {
		this.setResolution(resolution, loggedUser);
		this.setState(State.FINISHED, loggedUser);
	}
	
	public Set<User> getVoters() {
		return this.voters;
	}
	
	public void addVoter(User user) {
		if((!this.project.isPublic() && !this.project.userBelongs(user)) ||
				user.equals(this.reporter) || this.voters.contains(user) ||
				this.resolution != null) {
			throw new UserCantVoteException();
		}
		this.voters.add(user);
	}
	
	public void removeVoter(User user) {
		this.voters.remove(user);
	}
	
//	public Set<Issue> getDependsOn() {
//		return dependsOn;
//	}
//	public Set<Issue> getNecessaryFor() {
//		return necessaryFor;
//	}
//	public Set<Issue> getRelatedWith() {
//		return relatedWith;
//	}
//	public Set<Issue> getDuplicatedWith() {
//		return duplicatedWith;
//	}
	
	public void registerChange(IssueChanges change) {
		this.changes.add(change);
	}
	
	public void setResolutionVersions(Set<Version> resolutionVersions, User loggedUser) {
		Set<Version> oldResolutionVersions = new HashSet<Version>(this.resolutionVersions);
		this.setResolutionVersions(resolutionVersions);
		if(checkRegisterVersionChange(oldResolutionVersions, this.resolutionVersions, loggedUser)) {
			String newVersions = getVersionsNames(this.resolutionVersions);
			String oldVersions = getVersionsNames(oldResolutionVersions);
			
			this.registerChange(
					new IssueChanges("Versiones de resolución",oldVersions,
							newVersions, loggedUser.getCompleteName(), this));
		}
	}
	
	
	public void setAffectedVersions(Set<Version> affectedVersions, User loggedUser) {
		Set<Version> oldAffectedVersions = new HashSet<Version>(this.affectedVersions);
		this.setAffectedVersions(affectedVersions);
		if(checkRegisterVersionChange(oldAffectedVersions, this.affectedVersions, loggedUser)) {
			String newVersions = getVersionsNames(this.affectedVersions);
			String oldVersions = getVersionsNames(oldAffectedVersions);
			
			this.registerChange(
					new IssueChanges("Versiones afectadas", 
							oldAffectedVersions == null? "" : oldVersions, 
									newVersions, loggedUser.getCompleteName(), this));
		}
	}
	
	private boolean checkRegisterVersionChange(Set<Version> oldVersions, Set<Version> newVersions, User loggedUser) {
		if(loggedUser != null) {
			if(((oldVersions != null && newVersions != null) 
					&& !oldVersions.equals(newVersions)) ||
					(oldVersions == null && (newVersions != null && !newVersions.isEmpty())) ||
					((oldVersions != null && !oldVersions.isEmpty()) && newVersions == null)) {
				
				return true;
			}
		}
		return false;
	}
	
	private String getVersionsNames(Set<Version> versions) {
		String versionsNames = "";
		
		if(versions == null) {
			return versionsNames;
		}
		
		for(Version v : versions) {
			versionsNames += v.getName() + ", ";
		}
		if(!versionsNames.isEmpty()) {
			versionsNames = versionsNames.substring(0,versionsNames.length() - 2);
		}
		return versionsNames;
	}
	
	private void setResolutionVersions(Set<Version> resolutionVersions) {
		if(resolutionVersions == null){
			resolutionVersions = new HashSet<Version>();
		}
		for (Version version : resolutionVersions) {
				if (!this.resolutionVersions.contains(version)) {
					this.resolutionVersions.add(version);
				}
			}
		
		Iterator<Version> iterator = this.resolutionVersions.iterator();

		while (iterator.hasNext()) {
			Version version = (Version) iterator.next();
			if (!resolutionVersions.contains(version)
					&& !version.getState().equals(VersionState.RELEASED)) {
				iterator.remove();
			}
		}
	}
	
	public Set<Version> getResolutionVersions() {
		return resolutionVersions;
	}
	
	private void setAffectedVersions(Set<Version> affectedVersions) {
		if (affectedVersions == null) {
			affectedVersions = new HashSet<Version>();
		}
			for (Version version : affectedVersions) {
				if (!this.affectedVersions.contains(version)) {
					this.affectedVersions.add(version);
				}
			}
		
		Iterator<Version> iterator = this.affectedVersions.iterator();

		while (iterator.hasNext()) {
			Version version = (Version) iterator.next();
			if (!affectedVersions.contains(version)
					&& !version.getState().equals(VersionState.RELEASED)) {
				iterator.remove();
			}
		}
	}
	
	public Set<Version> getAffectedVersions() {
		return affectedVersions;
	}
	
	public List<IssueChanges> getChanges() {
		return this.changes;
	}
	
	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType, User loggedUser) {
		if(loggedUser != null) {
			if((this.issueType != null && !this.issueType.equals(issueType)) ||
					(this.issueType == null && issueType != null) ||
					(this.issueType != null && issueType == null)) {
				this.registerChange(
						new IssueChanges("Tipo", 
								this.issueType == null? "" : this.issueType.getName(), 
										issueType == null? "" : issueType.getName(),
												loggedUser.getCompleteName(), this));
			}
		}
		this.setIssueType(issueType);
	}
	
	private void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public void relate(Issue relatedIssue, RelationType relationType) {
		if(this.equals(relatedIssue)) {
			throw new IssueRelationException();
		}
		
		Relation relation = new Relation(relationType, relatedIssue, this);
		this.relations.add(relation);
		
		switch (relationType) {
		case RELATED:
			if(!relatedIssue.isRelatedWith(this, RelationType.RELATED)) {
				relatedIssue.relate(this, RelationType.RELATED);
			}
			break;
		case DEPENDS:
			if(!relatedIssue.isRelatedWith(this, RelationType.NECESSARY)) {
				relatedIssue.relate(this, RelationType.NECESSARY);
			}
			break;
		case DUPLICATED:
			if(!relatedIssue.isRelatedWith(this, RelationType.DUPLICATED)) {
				relatedIssue.relate(this, RelationType.DUPLICATED);
			}
			break;
		case NECESSARY:
			if(!relatedIssue.isRelatedWith(this, RelationType.DEPENDS)) {
				relatedIssue.relate(this, RelationType.DEPENDS);
			}
			break;

		}
		
	}
	
	public List<Issue> getIssuesByRelationType(RelationType relationType) {
		List<Issue> issues = new ArrayList<Issue>();
		
		for(Relation relation : this.relations) {
			if(relation.getRelationType().equals(relationType)) {
				issues.add(relation.getRelatedIssue());
			}
		}
		
		return issues;
	}
	
	public boolean isRelatedWith(Issue issue, RelationType relationType) {
		for(Issue relIssue : this.getIssuesByRelationType(relationType)) {
			if(relIssue.equals(issue)) {
				return true;
			}
		}
		return false;
	}
	
	public void removeRelation(Issue relatedIssue, RelationType relationType) {
		Iterator<Relation> it = this.relations.iterator();
		
		while(it.hasNext()) {
			Relation relation = (Relation) it.next();
			if(relation.getRelatedIssue().equals(relatedIssue) && relation.getRelationType().equals(relationType)) {
				it.remove();
				
				switch (relationType) {
				case RELATED:
					if(relatedIssue.isRelatedWith(this, RelationType.RELATED)) {
						relatedIssue.removeRelation(this, RelationType.RELATED);
					}
					break;
				case DEPENDS:
					if(relatedIssue.isRelatedWith(this, RelationType.NECESSARY)) {
						relatedIssue.removeRelation(this, RelationType.NECESSARY);
					}
					break;
				case DUPLICATED:
					if(relatedIssue.isRelatedWith(this, RelationType.DUPLICATED)) {
						relatedIssue.removeRelation(this, RelationType.DUPLICATED);
					}
					break;
				case NECESSARY:
					if(relatedIssue.isRelatedWith(this, RelationType.DEPENDS)) {
						relatedIssue.removeRelation(this, RelationType.DEPENDS);
					}
					break;
				}
			}
		}
	}
	
	public Set<User> getFollowers() {
		return followers;
	}

	private void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	
	public void addFollower(User user) {
		this.followers.add(user);
		if(!user.getFollowingIssues().contains(this)){
			user.addFolliwingIssue(this);
		}
	}
	
	public void removeFollower(User user) {
		this.followers.remove(user);
		if(user.getFollowingIssues().contains(this)) {
			user.removeFollowingIssue(this);
		}
	}

	@Override
	public String toString(){
		return this.title + "|" + this.state;
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
