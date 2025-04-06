package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeacherTest {

	@Test
	void testAddCourse() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		
		assertEquals(first.getCourses().size(), 0);
		first.addCourse(science, first.getStudentList("science"));
		assertEquals(first.getCourses().size(), 1);
	}
	
	@Test
	void testGetCurrentOrCompletedCourses() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		first.addCourse(science, first.getStudentList("science"));
		
		assertEquals(first.getCompletedOrCurrentCourses(true).size(), 0);
		assertEquals(first.getCompletedOrCurrentCourses(false).size(), 1);
		science.setCompleted();
		assertEquals(first.getCompletedOrCurrentCourses(true).size(), 1);
		assertEquals(first.getCompletedOrCurrentCourses(false).size(), 0);
		
	}
	
	@Test
	void testGetCourse() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		
		assertTrue(first.getCourse("science") == null);
		assertTrue(first.getCourse(science) == null);

		first.addCourse(science, first.getStudentList("science"));
		
		assertTrue(first.getCourse("science") == science);
		assertTrue(first.getCourse(science) == science);
		
	}
	
	@Test
	void testAddStudent() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		first.addCourse(science, first.getStudentList("science"));
		Student stu = new Student("john", "smith");
		
		first.addStudent(science, stu);
		StudentList list = first.getStudentList("science");
		
		
	}

}
