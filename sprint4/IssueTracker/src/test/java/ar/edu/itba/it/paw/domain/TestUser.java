package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.user.Type;
import ar.edu.itba.it.paw.domain.user.User;

public class TestUser {
	
	private User user1;
	private User user2;
	private Project project1;
	private Project project2;
	private Issue issue1;
	private Issue issue2;
	
	@Before
	public void init(){
		user1 = new User("mcasan","Moria","Casan", "123456", Type.ADMIN, true);
		user2 = new User("mcasan","Susana","Gimenez", "123456", Type.REGULAR, false);
		project1 = new Project("primero", "PRIM", null, user1, false);
		project2 = new Project("segundo", "SEG", null, user1, false);	
		issue1 = new Issue("primera", null, null, Priority.HIGH, user1, null,
				project1, null, null, null);
		issue2 = new Issue("segunda", null, null, Priority.HIGH, user1, null,
				project2, null, null, null);
	}
	
	@Test
	public void VersionEqualsTest(){
		Assert.assertTrue(user1.equals(user2));
	}

	@Test
	public void FollowersTest(){
		user1.addFolliwingIssue(issue1);
		user1.addFolliwingIssue(issue2);
		user1.addFolliwingIssue(issue2);
		Assert.assertTrue(user1.getFollowingIssues().size() == 2);
		Assert.assertTrue(user1.getFollowingIssuesForProject(project1).size() == 1);
		user1.removeFollowingIssue(issue1);
		Assert.assertTrue(user1.getFollowingIssues().size() == 1);
	}
}
