import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAssignment {

	
	
	@Test
	void testGetTotalPoints() {
		Assignment one = new Assignment("one", 100.0);
		assertEquals(one.getTotalPoints(), 100.0);
	}
	
	@Test
	void testGraded() {
		Assignment one = new Assignment("one", 100.0);
		assertFalse(one.isGraded());
		one.setStudentGrade(97.0);
		assertEquals(one.getStudentGrade(), 97.0);
	}
	
	@Test
	void testGetName() {
		Assignment one = new Assignment("one", 100.0);
		
		assertTrue(one.getName().equals("one"));
	}
	

}
