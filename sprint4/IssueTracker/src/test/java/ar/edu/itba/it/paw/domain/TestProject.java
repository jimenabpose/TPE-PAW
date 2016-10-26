package ar.edu.itba.it.paw.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.VersionNameRepeatedException;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.domain.user.Type;
import ar.edu.itba.it.paw.domain.user.User;

public class TestProject {

	private Project project;
	private User leader;
	
	@Before
	public void init(){
		leader = new User("mcasan", "Moria", "Casan", "123456", Type.REGULAR, true);
		project = new Project("primero", "PRIM", null, leader, false);		
	}

	@Test(expected=IssueNameRepeatedException.class)
	public void duplicatedIssueNameThrowsException(){
		Issue issue = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		Issue issue2 = new Issue("primera", null, null, Priority.LOW, leader, null,
				project, null, null, null);
		project.addIssue(issue);
		project.addIssue(issue2);
	}
	
	@Test(expected=VersionNameRepeatedException.class)
	public void duplicatedVersionNameThrowsException(){
		DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("2010-10-15");
		} catch (ParseException e) {
		}
		Version version1 = new Version("primera", null, date, project);
		Version version2 = new Version("primera", null, date, project);
		version2.setState(VersionState.ONCOURSE);
	}

	@Test
	public void getUnreleasedVersionsTest(){
		DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("2010-10-15");
		} catch (ParseException e) {
		}
		Version version1 = new Version("primera", null, date, project);
		Version version2 = new Version("segunda", null, date, project);
		version2.setState(VersionState.ONCOURSE);
		Version version3 = new Version("tercera", null, date, project);
		version3.setState(VersionState.RELEASED);
		
		Set<Version> versions = project.getUnreleasedVersions();
		
		Assert.assertTrue(versions.contains(version1));
		Assert.assertTrue(versions.contains(version2));
		Assert.assertFalse(versions.contains(version3));
	}
	
	@Test(expected=NotDeletableUserException.class)
	public void deleteLeaderThrowsException(){
		project.add(leader);
		project.delete(leader);
	}
	
	@Test
	public void UserBelongsTest(){
		User user1 = new User("sgimenez", "Susana", "Gimenez", "123456", Type.REGULAR, true);
		User user2 = new User("rbalboa", "Rocky", "Balboa", "123456", Type.REGULAR, true);
		project.add(user1);
		project.add(user2);
		Assert.assertTrue(project.userBelongs(user1));
		project.delete(user1);
		Assert.assertFalse(project.userBelongs(user1));
	}

	@Test
	public void VersionTest(){
		Version version1 = new Version("primera", "primera", new Date(), project);
		Version version2 = new Version("segunda", "segunda", new Date(), project);
		Version version3 = new Version("tercera", "tercera", new Date(), project);
		
		version3.setState(VersionState.RELEASED);
		
		Assert.assertTrue(project.getUnreleasedVersions().size() == 2);
	}
}
