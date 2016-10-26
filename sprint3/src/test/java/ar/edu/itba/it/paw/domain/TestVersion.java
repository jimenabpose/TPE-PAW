package ar.edu.itba.it.paw.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

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
		
		Version version1 = new Version("primera", null, date, VersionState.OPEN);
		version1.setProject(project1);
		Version version2 = new Version("primera", null, date, VersionState.ONCOURSE);
		version2.setProject(project2);
		
		project1.add(version1);
		project2.add(version2);
		
		Assert.assertFalse(version1.equals(version2));
	}

}
