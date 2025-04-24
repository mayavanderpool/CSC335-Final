package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.AssgType;
import model.Assignment;

class AssignmentTest{

	
	@Test
	void testGetTotalPoints() {
		Assignment one = new Assignment("one", 100.0, AssgType.HOMEWORK);
		assertEquals(one.getTotalPoints(), 100.0);
	}
	
	
	@Test
	void testGetName() {
		Assignment one = new Assignment("one", 100.0, AssgType.HOMEWORK);
		
		assertTrue(one.getName().equals("one"));
	}
	
	@Test
	void testCopy() {
		Assignment one = new Assignment("one", 100.0, AssgType.HOMEWORK);
		Assignment copy = new Assignment(one);
		
		assertTrue(copy.getName().equals(one.getName()));
		assertEquals(copy.getTotalPoints(), one.getTotalPoints());
		
	}
	
	@Test
	void testType() {
		Assignment one = new Assignment("one", 100.0, AssgType.HOMEWORK);
		assertEquals(one.getType(), AssgType.HOMEWORK);
	}
	
	@Test
	void testDrop() {
		Assignment one = new Assignment("one", 100.0, AssgType.HOMEWORK);
		one.dropAssg();
		assertTrue(one.getDropped());
	}

}