package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CourseTest {

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
	void testGetOverallGrade() {
		Course one = new Course("335");
		Assignment assg = new Assignment("assg1", 100.0);
		
		one.addAssg(assg);
		assg.setStudentGrade(100.0);
		
		assertEquals(one.getOverallGrade(), 100.0);
		
	}
	
	@Test
	void testGetLetterGrade() {
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
	void testGetUngradedAssignments() {
		Course one = new Course("335");
		Assignment assg1 = new Assignment("assg1", 100.0);
		Assignment assg2 = new Assignment("assg2", 100.0);
		Assignment assg3 = new Assignment("assg3", 100.0);
		Assignment assg4 = new Assignment("assg4", 100.0);
		
		one.addAssg(assg1);
		one.addAssg(assg2);
		one.addAssg(assg3);
		one.addAssg(assg4);
		
		
		assertEquals(one.getUngradedAssignments().size(), 4);
		one.removeAssg(assg4);
		assertEquals(one.getUngradedAssignments().size(), 3);
		
		assg3.setStudentGrade(97.0);
		
		assertEquals(one.getUngradedAssignments().size(), 2);
	}
	
	@Test
	void testGetAssignments() {
		Course one = new Course("335");
		Assignment assg1 = new Assignment("assg1", 100.0);
		Assignment assg2 = new Assignment("assg2", 100.0);
		Assignment assg3 = new Assignment("assg3", 100.0);
		Assignment assg4 = new Assignment("assg4", 100.0);
		
		one.addAssg(assg1);
		one.addAssg(assg2);
		one.addAssg(assg3);
		one.addAssg(assg4);
		
		assertEquals(one.getAssignments().size(), 4);
		one.removeAssg(assg4);
		assertEquals(one.getAssignments().size(), 3);
	}
	
	@Test
	void testWhatGrade() {
		Course one = new Course("335");
		Assignment assg1 = new Assignment("assg1", 100.0);
		Assignment assg2 = new Assignment("assg2", 100.0);
		Assignment assg3 = new Assignment("assg3", 100.0);
		
		one.addAssg(assg1);
		one.addAssg(assg2);
		one.addAssg(assg3);
		
		assg1.setStudentGrade(70.0);
		assg2.setStudentGrade(70.0);
		assg3.setStudentGrade(70.0);
		
		assertEquals(one.gradeNeeded(80.0), 110.0);
		
		assertEquals(one.gradeNeeded(70.0), 0.0);
		
	}

}
