package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.Assignment;
import model.Course;
import model.Student;
import model.Teacher;

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
		Student stu1 = new Student("Rees", "Hart", "rhart");
		
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
	
	@Test
	void testGetUngradedAssignments() {
		Teacher t = new Teacher("Alice", "Anderson", "aa");
		Course course = new Course("math");
		
		t.addCourse(course);
		Student s1 = new Student("Miss", "Piggy", "mp");
		Student s2 = new Student("Kermit", "the Frog", "kf");
		t.addStudent(course, s1);
		t.addStudent(course, s2);
		
		Assignment a1 = new Assignment("hw1", 100.0);
		Assignment a2 = new Assignment("hw2", 100.0);
		t.addAssignmentToCourse("math", a1);
		t.addAssignmentToCourse("math", a2);
		
		t.addAssignmentGrade(s1, "math", a1, 95);
		t.addAssignmentGrade(s1, "math", a2, 90);
		assertEquals(course.getUngradedAssignments().length(), 63);
		
	}

	@Test
	void testCopyConstructor() {
		Course original = new Course("CS101");
		Student student = new Student("John", "Doe", "jd123");
		Assignment assg = new Assignment("Midterm", 100.0);
		
		original.addStudents(student);
		original.addAssg(assg);
		original.setCompleted();
		
		Course copy = new Course(original);
		
		assertEquals(original.getName(), copy.getName());
		assertEquals(original.isCompleted(), copy.isCompleted());
		assertEquals(1, copy.getStudents().getStudents().size());
		assertEquals("jd123", copy.getStudents().getStudents().get(0).getUserName());
		assertEquals(1, copy.getAssignments().size());
		assertEquals("Midterm", copy.getAssignments().get(0).getName());
	}
	

}
