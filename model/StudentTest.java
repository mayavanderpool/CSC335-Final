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
		
		s1.addCourse(c1);
		assertEquals(s1.getCurrentCourses().size(), 1);
		
		c1.setCompleted();
		assertEquals(s1.getCurrentCourses().size(), 0);
		assertEquals(s1.getCompletedCourses().size(), 1);
		
		
		Course c2 = new Course("Art");
		s1.addCourse(c2);
		
		Assignment a1 = new Assignment("a1", 100.0);
		assertEquals(s1.getGraded().size(), 0);
		a1.setStudentGrade(90.0);
		c2.addAssg(a1);
		
		assertEquals(s1.getAssignments().size(), 1);
		assertEquals(s1.getGraded().size(), 1);
	}
	
	@Test
	void GPA() {
		Student s1 = new Student("Rees", "Hart");
		Course c1 = new Course("Math");
		Assignment a1 = new Assignment("a1", 100.0);
		Assignment a2 = new Assignment("a2", 100.0);
		c1.addAssg(a1);
		c1.addAssg(a2);
		a1.setStudentGrade(90.0);
		c1.setCompleted();
		s1.addCourse(c1);
		
		s1.setAssignmentGrade("Math", a2, 90.0);
		assertEquals(s1.getCompletedCourses().size(), 1);
		assertEquals(s1.getGrade(c1), 90);
		assertEquals(s1.getGPA(), 3.6);
		
		
	}
	
	@Test
	void gradeNeeded() {
		Student s1 = new Student("Rees", "Hart");
		Student s2 = new Student("Crispin", "Carter");
		
		ArrayList<Student> list = new ArrayList<Student>();
		list.add(s1);
		list.add(s2);
		
		Collections.sort(list, Student.fisrtNameFirstComparator());
		Collections.sort(list, Student.lastNameFirstComparator());
		Collections.sort(list, Student.userNameFirstComparator());
		
	}
	
	
	
	

}
