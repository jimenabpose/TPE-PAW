package ar.edu.itba.it.paw.domain;

import org.junit.Assert;
import org.junit.Test;

public class TestUser {
	
	@Test
	public void VersionEqualsTest(){
		User user1 = new User("mcasan","Moria","Casan", "123456", Type.ADMIN, true);
		User user2 = new User("mcasan","Susana","Gimenez", "123456", Type.REGULAR, false);
		
		Assert.assertTrue(user1.equals(user2));
	}

}
