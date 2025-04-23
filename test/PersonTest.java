package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Person;

class PersonTest {

	@Test
	void test() {
		Person rees = new Person("rees", "hart", "reeshart", "student");
		assertEquals(rees.getFirstName(), "rees");
		assertEquals(rees.getLastName(), "hart");
		assertEquals("reeshart", rees.getUserName());
		rees.setUser("reeshart4");
		assertEquals("reeshart4", rees.getUserName());
		assertEquals("rees hart", rees.toString());
	}

}