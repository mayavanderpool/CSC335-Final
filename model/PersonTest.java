package model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void test() {
		Person rees = new Person("rees", "hart");
		assertEquals(rees.getFirstName(), "rees");
		assertEquals(rees.getLastName(), "hart");
		assertEquals("", rees.getUserName());
		rees.setUser("reeshart4");
		assertEquals("reeshart4", rees.getUserName());

	}

}
