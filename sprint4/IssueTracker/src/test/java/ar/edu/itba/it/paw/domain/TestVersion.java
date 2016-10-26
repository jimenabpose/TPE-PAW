package ar.edu.itba.it.paw.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.domain.user.Type;
import ar.edu.itba.it.paw.domain.user.User;

public class TestVersion {

	
	@Test
	public void VersionEqualsTest(){
		User leader = new User("mcasan", "Moria", "Casan", "123456", Type.REGULAR, true);
		Project project1 = new Project("primero", "PRIM", null, leader, false);
		Project project2 = new Project("segundo", "SEG", null, leader, false);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("2010-10-15");
		} catch (ParseException e) {
		}
		
		Version version1 = new Version("primera", null, date, project1);
		Version version2 = new Version("primera", null, date, project2);
		version2.setState(VersionState.ONCOURSE);
		
		Assert.assertFalse(version1.equals(version2));
	}

}
