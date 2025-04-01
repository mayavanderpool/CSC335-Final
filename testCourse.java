import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestCourse {

	@Test
	void testCompleted() {
		Course one = new Course("335");
		
		assertFalse(one.isCompleted());
		one.setCompleted();
		assertTrue(one.isCompleted());
	}
	
	@Test
	void testGetName() {
		Course one = new Course("335");
		
		assertTrue(one.getName().equals("335"));
	}
	
	@Test
	void getOverallGrade() {
		Course one = new Course("335");
		Assignment assg = new Assignment("assg1", 100.0);
		
		one.addAssg(assg);
		assg.setStudentGrade(100.0);
		
		assertEquals(one.getOverallGrade(), 100.0);
		
	}
	
	@Test
	void getLetterGrade() {
		Course one = new Course("335");
		Assignment assg = new Assignment("assg1", 100.0);
		
		one.addAssg(assg);
		
		assg.setStudentGrade(100.0);
		assertTrue(one.getLetterGrade().equals("A"));
		
		assg.setStudentGrade(90.0);
		assertTrue(one.getLetterGrade().equals("A"));
		
		assg.setStudentGrade(80.0);
		assertTrue(one.getLetterGrade().equals("B"));
		
		assg.setStudentGrade(70.0);
		assertTrue(one.getLetterGrade().equals("C"));
		
		assg.setStudentGrade(60.0);
		assertTrue(one.getLetterGrade().equals("D"));
		
		assg.setStudentGrade(59.0);
		assertTrue(one.getLetterGrade().equals("F"));
		
	}
	
	@Test
	void getUngradedAssignments() {
		Course one = new Course("335");
		Assignment assg1 = new Assignment("assg1", 100.0);
		Assignment assg2 = new Assignment("assg2", 100.0);
		Assignment assg3 = new Assignment("assg3", 100.0);
		Assignment assg4 = new Assignment("assg4", 100.0);
		
		one.addAssg(assg1);
		one.addAssg(assg2);
		one.addAssg(assg3);
		one.addAssg(assg4);
		
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		assignments.add(assg1);
		assignments.add(assg2);
		assignments.add(assg3);
		assignments.add(assg4);
		
		assertEquals(assignments, one.getUngradedAssignments());
		
		one.removeAssg(assg4);
		assertEquals(one.getUngradedAssignments().size(), 3);
		
	
	}

}
