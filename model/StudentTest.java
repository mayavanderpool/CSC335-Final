package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class StudentTest {

	@Test
	void testGetters() {
		Student s1 = new Student("Rees", "Hart");
		assertEquals(s1.getFirstName(), "Rees");
		assertEquals(s1.getLastName(), "Hart");
		assertEquals(s1.getGPA(), 0.0);
		assertEquals(s1.getCurrentCourses().size(), 0);
		assertEquals(s1.getCompletedCourses().size(), 0);
		assertEquals(s1.getAssignments().size(), 0);
		assertEquals(s1.getGraded().size(), 0);
	}
	
	@Test
	void testSet() {
		Student s1 = new Student("Rees", "Hart");
		Course c1 = new Course("Math");
		Teacher t1 = new Teacher("Jane", "Smith");
		
		t1.addCourse(c1);
		t1.addStudent(c1, s1);
		s1.addCourse(c1);
		
		assertEquals(s1.getCurrentCourses().size(), 1);
		
		c1.setCompleted();
		assertEquals(s1.getCurrentCourses().size(), 0);
		assertEquals(s1.getCompletedCourses().size(), 1);
		
		
		Course c2 = new Course("Art");
		t1.addCourse(c2);
		t1.addStudent(c2, s1);
		s1.addCourse(c2);
		
		Assignment a1 = new Assignment("a1", 100.0);
		t1.addAssignmentToCourse("Art", a1);
		
		assertEquals(s1.getGraded().size(), 0);
		t1.addAssignmentGrade(s1, "Art", a1, 100);
		
		assertEquals(s1.getAssignments().size(), 1);
		assertEquals(s1.getGraded().size(), 1);
	}
	
	@Test
	void GPA() {
		Student s1 = new Student("Rees", "Hart");
		Teacher t1 = new Teacher("Jane", "Smith");
		Course c1 = new Course("Math");
		Assignment a1 = new Assignment("a1", 100.0);
		Assignment a2 = new Assignment("a2", 100.0);
		t1.addCourse(c1);
		t1.addStudent(c1, s1);
		s1.addCourse(c1);
		t1.addAssignmentToCourse("Math", a1);
		t1.addAssignmentGrade(s1, "Math", a1, 100);
		c1.setCompleted();
		assertEquals(s1.getGPA(), 4.0);
		
		
		
		
	}
	
	@Test
	void testGradeNeeded() {
		Student s1 = new Student("Rees", "Hart");
		Course c1 = new Course("Math");
		Course c2 = new Course("Art");
		Teacher t1 = new Teacher("Jane", "Smith");
		Assignment a1 = new Assignment("a1", 100.0);
		
		t1.addCourse(c1);
		t1.addStudent(c1, s1);
		s1.addCourse(c1);
		t1.addAssignmentToCourse("Math", a1);
		t1.addAssignmentGrade(s1, "Math", a1, 0);
		c1.setCompleted();
		t1.addCourse(c2);
		t1.addStudent(c2, s1);
		s1.addCourse(c2);
		
		
		assertEquals(200, s1.courseGradeNeeded(100.0, c2));
		
		
		
		
		
	}
	
	
	
	

}
