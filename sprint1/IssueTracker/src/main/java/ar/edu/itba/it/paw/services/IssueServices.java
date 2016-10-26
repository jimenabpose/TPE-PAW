package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface IssueServices {
	
	public void saveIssue(Issue issue);
	
	/**
	 * Este metodo es usado cuando se requiere un objeto Issue y solamente
	 * contamos con su codigo.
	 * 
	 * @param code es el codigo de la tarea
	 * @return devuelve un objeto Issue que representa a la tarea
	 */
	//public Issue getIssue(String code);
	
	public Issue getIssue(int id);

	public List<Issue> getUserActiveIssues(User user);
	
	public List<Issue> getIssues(Project project);
}
