package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="version")
public class Version extends PersistentAttributes {

	private String name;
	private String description;
	private Date releaseDate;
	@Enumerated(EnumType.STRING)
	private VersionState state;
	@ManyToOne
	private Project project;
	
	@SuppressWarnings("unused")
	private Version() {
		
	}
	
	public Version(String name, String description, Date releaseDate, VersionState state){
		this.setName(name);
		this.setDescription(description);
		this.setReleaseDate(releaseDate);
		this.setState(state);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		ValidationUtils.checkNotNull(name);
		ValidationUtils.checkRequiredMaxText(name, 20);
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public String getFullDescription(){
		return name + "|" + description + "|" + releaseDate + "|" + state;
	}

	public void setDescription(String description) {
		ValidationUtils.checkTextMaxLength(description, 250);
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		ValidationUtils.checkNotNull(releaseDate);
		this.releaseDate = releaseDate;
	}

	public VersionState getState() {
		return state;
	}

	public void setState(VersionState state) {
		ValidationUtils.checkNotNull(releaseDate);
		this.state = state;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Issue> getIssues(){
		List<Issue> issues = new ArrayList<Issue>(this.project.getIssues());
		
		Iterator<Issue> iterator = issues.iterator();

		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(!issue.getResolutionVersions().contains(this)){
				iterator.remove();
			}
		}
		
		return issues;
	}
	
	public List<Issue> getNotActiveIssues(){
		List<Issue> issues = new ArrayList<Issue>(this.getIssues());
		
		Iterator<Issue> iterator = issues.iterator();

		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(!issue.getState().equals(State.CLOSED) && !issue.getState().equals(State.FINISHED)){
				iterator.remove();
			}
		}
		
		return issues;
	}
	
	public float getProgress(){
		List<Issue> issues = this.getIssues();
		List<Issue> notActiveIssues = this.getNotActiveIssues();
		
		float ans = ((float) notActiveIssues.size() / (float)issues.size())*100;
		
		if(issues.size() == 0)
			ans = -1;
		
		return ans;
	}
	
	public HashMap<String, List<Issue>> getIssuesByType(){
		HashMap<String, List<Issue>> issues_types = new HashMap<String, List<Issue>>();
		
		/* inicializo el mapa de tipos */
		IssueType types[] = IssueType.values();
		for (int i = 0; i < types.length; i++) {
			issues_types.put(types[i].getName(), new ArrayList<Issue>());
		}
		String SinTipo = "no definido";
		
		issues_types.put(SinTipo, new ArrayList<Issue>());

		List<Issue> issues_aux;
		List<Issue> issues = this.getIssues();
		for (Issue issue : issues) {
			if (issue.getIssueType() != null){
				issues_aux = issues_types.get(issue.getIssueType().getName());
				issues_aux.add(issue);
				Collections.sort(issues_aux, new Comparator<Issue>() {
					public int compare(Issue a, Issue b) {
						return a.compareTo(b);
					}
				});
				issues_types.put(issue.getIssueType().getName(), issues_aux);
			}else{
				/*para las tareas sin tipo asignado*/
				issues_aux = issues_types.get(SinTipo);
				issues_aux.add(issue);
				Collections.sort(issues_aux, new Comparator<Issue>() {
					public int compare(Issue a, Issue b) {
						return a.compareTo(b);
					}
				});
				issues_types.put(SinTipo, issues_aux);
			}
		}
		return issues_types;
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
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
		Version other = (Version) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}
	
}
