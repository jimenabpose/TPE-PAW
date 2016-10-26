package ar.edu.itba.it.paw.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.exceptions.CantAssignUserException;
import ar.edu.itba.it.paw.domain.exceptions.InvalidOperationException;
import ar.edu.itba.it.paw.domain.exceptions.UserCantVoteException;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.issue.RelationType;
import ar.edu.itba.it.paw.domain.issue.Resolution;
import ar.edu.itba.it.paw.domain.issue.State;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.user.Type;
import ar.edu.itba.it.paw.domain.user.User;

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

	@Test
	public void issueResolutionTest(){
		Issue issue1 = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		Assert.assertTrue(issue1.getResolution() == null);
		issue1.resolve(Resolution.SOLVED, leader);
		Assert.assertTrue(issue1.getResolution().equals(Resolution.SOLVED));
		Assert.assertTrue(issue1.getState().equals(State.FINISHED));
	}
	
	@Test
	public void votersTest(){
		Issue issue = new Issue("segunda", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		User user1 = new User("rbalboa","Rocky","Balboa", "123456", Type.ADMIN, true); 
		User user2 = new User("acreed","Apollo","Creed", "123456", Type.ADMIN, true);
		project.add(user1);
		project.add(user2);
		
		issue.addVoter(user1);
		issue.addVoter(user2);
		
		Assert.assertTrue(issue.getVoters().size() == 2);
		issue.removeVoter(user1);
		Assert.assertTrue(issue.getVoters().size() == 1);
	}
	
	@Test
	public void relationsTest(){
		Issue issue1 = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		Issue issue2 = new Issue("segunda", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		
		issue1.relate(issue2, RelationType.NECESSARY);
		
		Assert.assertFalse(issue1.isRelatedWith(issue2, RelationType.DEPENDS));
		Assert.assertTrue(issue1.isRelatedWith(issue2, RelationType.NECESSARY));
		Assert.assertFalse(issue2.isRelatedWith(issue1, RelationType.NECESSARY));
	}
}
