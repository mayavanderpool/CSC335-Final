package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeacherTest {

	@Test
	void testAddCourse() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		
		assertEquals(first.getCourses().size(), 0);
		first.addCourse(science);
		assertEquals(first.getCourses().size(), 1);
	}
	
	@Test
	void testGetCurrentOrCompletedCourses() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		first.addCourse(science);
		
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

		first.addCourse(science);
		
		assertTrue(first.getCourse("science") == science);
		assertTrue(first.getCourse(science) == science);
		
	}
	
	@Test
	void testGetStudentList() {
		Teacher first = new Teacher("jane", "doe");
		Course science = new Course("science");
		first.addCourse(science);
		Student stu = new Student("john", "smith");
		
		StudentList list = first.getStudentList(science);
		assertEquals(list.getStudents().size(), 0);
		first.addStudent(science, stu);
		
		list = first.getStudentList(science);
		assertEquals(list.getStudents().size(), 1);
	}
	
	
	
	
}
