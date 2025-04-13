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
	void testAddRemoveStudents() {
		Course one = new Course("335");
		Student stu1 = new Student("Rees", "Hart");
		
		one.addStudents(stu1);
		assertEquals(1, one.getStudents().getStudents().size());
		one.removeStudents(stu1);
		assertEquals(0, one.getStudents().getStudents().size());
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
	
	

}
