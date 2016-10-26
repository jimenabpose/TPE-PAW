package ar.edu.itba.it.paw.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableVersionException;
import ar.edu.itba.it.paw.domain.exceptions.VersionNameRepeatedException;

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
		Version version1 = new Version("primera", null, date, VersionState.OPEN);
		Version version2 = new Version("primera", null, date, VersionState.ONCOURSE);
		
		project.add(version1);
		project.add(version2);
	}

	@Test
	public void getUnreleasedVersionsTest(){
		DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("2010-10-15");
		} catch (ParseException e) {
		}
		Version version1 = new Version("primera", null, date, VersionState.OPEN);
		Version version2 = new Version("segunda", null, date, VersionState.ONCOURSE);
		Version version3 = new Version("tercera", null, date, VersionState.RELEASED);
		
		project.add(version1);
		project.add(version2);
		project.add(version3);
		
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
	
	@Test(expected=NotDeletableVersionException.class)
	public void deleteActiveVersionThrowsException(){
		DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("2010-10-15");
		} catch (ParseException e) {
		}
		
		Version version1 = new Version("primera", null, date, VersionState.OPEN);
		Version version2 = new Version("segunda", null, date, VersionState.ONCOURSE);
		Version version3 = new Version("tercera", null, date, VersionState.RELEASED);
		
		Issue issue = new Issue("primera", null, null, Priority.HIGH, leader, null,
				project, null, null, null);
		
		Set<Version> set1 = new HashSet<Version>();
		set1.add(version1);
		set1.add(version2);
		Set<Version> set2 = new HashSet<Version>();
		set2.add(version3);
		
		issue.setResolutionVersions(set1, leader);
		issue.setAffectedVersions(set2, leader);
		
		project.addIssue(issue);
		project.add(version1);
		project.add(version2);
		project.add(version3);
		
		project.delete(version1);
	}
}
