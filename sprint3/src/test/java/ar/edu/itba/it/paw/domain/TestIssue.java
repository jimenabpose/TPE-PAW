package ar.edu.itba.it.paw.domain;

import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.exceptions.CantAssignUserException;
import ar.edu.itba.it.paw.domain.exceptions.InvalidOperationException;
import ar.edu.itba.it.paw.domain.exceptions.UserCantVoteException;

public class TestIssue {
	
	private Project project;
	private User leader;

	@Before
	public void init(){
		leader = new User("mcasan", "Moria", "Casan", "123456", Type.REGULAR, true);
		project = new Project("primero", "PRIM", null, leader, false);	
	}
	
	@Test(expected=CantAssignUserException.class)
	public void notProjectUserAssignedIssueThrowsException(){
		User user = new User("susana", "Moria", "Casan", "123456", Type.REGULAR, true);
		Issue issue = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		
		issue.setAssignee(user, user);
	}
	
	@Test(expected=InvalidOperationException.class)
	public void notIssueAssigneeChangesStateThrowsException(){
		Issue issue = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);

		issue.setState(State.OPEN, leader);
	}
	
	@Test(expected=UserCantVoteException.class)
	public void reporterVoteThrowsException(){
		Issue issue = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		issue.addVoter(leader);
	}
	
	@Test(expected=UserCantVoteException.class)
	public void invalidVoterThrowsException(){
		User user = new User("susana", "Moria", "Casan", "123456", Type.REGULAR, true);
		Issue issue = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		issue.addVoter(user);
	}

}
