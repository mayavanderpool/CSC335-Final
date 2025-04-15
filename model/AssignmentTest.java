package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AssignmentTest{

	
	@Test
	void testGetTotalPoints() {
		Assignment one = new Assignment("one", 100.0);
		assertEquals(one.getTotalPoints(), 100.0);
	}
	
	
	@Test
	void testGetName() {
		Assignment one = new Assignment("one", 100.0);
		
		assertTrue(one.getName().equals("one"));
	}
	
	@Test
	void testCopy() {
		Assignment one = new Assignment("one", 100.0);
		Assignment copy = new Assignment(one);
		
		assertTrue(copy.getName().equals(one.getName()));
		assertEquals(copy.getTotalPoints(), one.getTotalPoints());
		
	}
	

}