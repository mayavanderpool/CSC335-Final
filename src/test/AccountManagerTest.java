package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.AccountManager;

class AccountManagerTest {
	private static AccountManager aManager;
	
	@BeforeEach
	void setup() {
		aManager = new AccountManager();
		aManager.inputPeople("testAccountManager.txt");
	}

	@Test
	void testPersonExists() {
	    assertTrue(aManager.personExists("John", "Smith"));
	    assertFalse(aManager.personExists("Snuffy", "Snuffleupagus"));
	}
	
	@Test
	void testAccountExists() {
		assertFalse(aManager.accountExists("John", "Smith"));
	    aManager.addPassword("John", "Smith", "jsmith", "password123");
	    assertTrue(aManager.accountExists("John", "Smith"));
	    aManager.addPassword("John", "Smith", "jsmith", "password123");
	}
	
	@Test
	void testCreateUsername() {
		String username = aManager.createUsername("John", "Smith");
		assertNotNull(username);
		assertTrue(username.equals("jsmith"));
		aManager.addPassword("John", "Smith", "jsmith", "password123");
	}
	
	@Test
	void testCheckCredentials() {
	    aManager.addPassword("John", "Smith", "JSmith", "password123");
	    assertTrue(aManager.checkCredentials("JSmith", "password123"));
	    assertFalse(aManager.checkCredentials("JSmith", "wrongpassword"));
	    assertFalse(aManager.checkCredentials("wrongusername", "password123"));
	}

}