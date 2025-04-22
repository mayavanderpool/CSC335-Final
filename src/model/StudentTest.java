package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;


import model.Assignment;
import model.Course;
import model.Student;
import model.Teacher;

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
	void testCourseGradeNeeded() {
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
	
	@Test
	void testGetUngraded() {
		Student s = new Student("Snuffy", "Snuffles");
		Course course = new Course("math");
		Assignment a1 = new Assignment("hw1", 100.0);
		Assignment a2 = new Assignment("hw2", 100.0);
		Assignment a3 = new Assignment("hw3", 100.0);
		
		s.addCourse(course);
		s.addAssignment(course, a1);
		s.addAssignment(course, a2);
		s.addAssignment(course, a3);
		
		s.setAssignmentGrade("math", a1, 94);
		s.setAssignmentGrade("math", a2, 96);

		assertEquals(1, s.getUngraded().size());
	}
	
	@Test
	void testFisrtNameFirstComparator() {
		Student s1 = new Student("Alice", "Adams");
		Student s2 = new Student("Bob", "Benson");
		Student s3 = new Student("Alice", "Smith");
		
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(s1);
		students.add(s2);
		students.add(s3);
		Collections.sort(students, Student.fisrtNameFirstComparator());
		assertEquals("Alice", students.get(0).getFirstName());
		assertEquals("Adams", students.get(0).getLastName());
		assertEquals("Bob", students.get(2).getFirstName());
	}
	
	@Test
	void testLastNameFirstComparator() {
		Student s1 = new Student("Alice", "Adams");
		Student s2 = new Student("Bob", "Adams");
		Student s3 = new Student("Alice", "Smith");
		
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(s1);
		students.add(s2);
		students.add(s3);
		Collections.sort(students, Student.lastNameFirstComparator());
		assertEquals("Alice", students.get(0).getFirstName());
		assertEquals("Bob", students.get(1).getFirstName());
		assertEquals("Smith", students.get(2).getLastName());
	}
	
	@Test
	void testUserNameFirstComparator() {
		Student s1 = new Student("Alice", "Adams");
		s1.setUser("c");
		Student s2 = new Student("Bob", "Benson");
		s2.setUser("b");
		Student s3 = new Student("Charles", "Smith");
		s3.setUser("a");
		
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(s1);
		students.add(s2);
		students.add(s3);
		Collections.sort(students, Student.userNameFirstComparator());
		assertEquals("Alice", students.get(2).getFirstName());
		assertEquals("Bob", students.get(1).getFirstName());
	}
	
	@Test
	void testGradeFirstComparator() {
		Teacher t = new Teacher("fname", "lname");
		Course course = new Course("math");
		Student s1 = new Student("Bob", "Johnson");
		Student s2 = new Student("Alice", "Brown");
		Student s3 = new Student("Alice", "Smith");
		t.addCourse(course);
		t.addStudent(course, s1);
		t.addStudent(course, s2);
		t.addStudent(course, s3);
		Assignment a1 = new Assignment("hw1", 100.0);
		t.addAssignmentToCourse("math", a1);
		t.addAssignmentGrade(s1, "math", a1, 0);
		t.addAssignmentGrade(s2, "math", a1, 100);
		t.addAssignmentGrade(s3, "math", a1, 20);
		
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(s1);
		students.add(s2);
		students.add(s3);
		Collections.sort(students, Student.gradeFirstComparator(course));
		assertEquals(0.0, students.get(0).getGrade(course));
		assertEquals("Brown", students.get(2).getLastName());
		
	}
	
	@Test
	void testGetLetterGrade() {
		Teacher t = new Teacher("Snoopy", "Lastname");
		Course course = new Course("math");
		t.addCourse(course);
		Student sA = new Student("Jamie", "Lee");
		Student sB = new Student("Lindsay", "Lohan");
		Student sC = new Student("Lindsay", "Lohan");
		Student sD = new Student("Lindsay", "Lohan");
		Student sF = new Student("Lindsay", "Lohan");
        t.addStudent(course, sA);
        t.addStudent(course, sB);
        t.addStudent(course, sC);
        t.addStudent(course, sD);
        t.addStudent(course, sF);
        
        Assignment a1 = new Assignment("hw1", 100.0);
        Assignment a2 = new Assignment("hw2", 100.0);
        t.addAssignmentToCourse("math", a1);
        t.addAssignmentToCourse("math", a2);
        
        t.addAssignmentGrade(sA, "math", a1, 100.0);
        t.addAssignmentGrade(sA, "math", a2, 87.0);
        t.addAssignmentGrade(sB, "math", a1, 90.0);
        t.addAssignmentGrade(sB, "math", a2, 84.0);
        t.addAssignmentGrade(sC, "math", a1, 77.0);
        t.addAssignmentGrade(sC, "math", a2, 74.0);
        t.addAssignmentGrade(sD, "math", a1, 60.0);
        t.addAssignmentGrade(sD, "math", a2, 66.5);
        t.addAssignmentGrade(sF, "math", a1, 20.0);
        t.addAssignmentGrade(sF, "math", a2, 70.0);
        
        assertEquals(sA.getLetterGrade(course), "A");
        System.out.println(sB.getGrade(course));
        assertEquals(sB.getLetterGrade(course), "B");
        assertEquals(sC.getLetterGrade(course), "C");
        assertEquals(sD.getLetterGrade(course), "D");
        assertEquals(sF.getLetterGrade(course), "F");
	}

	@Test
	void testGradeNeeded() {
		Teacher t = new Teacher("Snoopy", "Lastname");
		Course course = new Course("math");
		t.addCourse(course);
		Student sA = new Student("Jamie", "Lee");
        t.addStudent(course, sA);
        Assignment a1 = new Assignment("hw1", 100.0);     
        t.addAssignmentToCourse("math", a1);    
        t.addAssignmentGrade(sA, "math", a1, 80);;
        assertEquals(100, sA.gradeNeeded(90.0, course, 100.0));
        assertEquals(80, sA.gradeNeeded(80.0, course, 100.0));
	}
	
	@Test
	void testRemoveAssignment() {
		Student s = new Student("Snuffy", "Snuffles");
		Course course = new Course("math");
		Assignment a1 = new Assignment("hw1", 100.0);
		Assignment a2 = new Assignment("hw2", 100.0);
		Assignment a3 = new Assignment("hw3", 100.0);
		
		s.addCourse(course);
		s.addAssignment(course, a1);
		s.addAssignment(course, a2);
		s.addAssignment(course, a3);
		
		s.setAssignmentGrade("math", a1, 94);
		s.setAssignmentGrade("math", a2, 96);

		assertEquals(3, s.getAssignments().size());
		s.removeAssignment(course, a3);
		assertEquals(2, s.getAssignments().size());
	}
	
	@Test
	void testGetAssgGrade() {
		Teacher teach = new Teacher("jane", "doe");
		Course course = new Course("math");
		teach.addCourse(course);
		Student s = new Student("mary", "mess");
		teach.addStudent(course, s);
		Assignment a = new Assignment("a", 10.0);
		course.addAssg(a);
		teach.addAssignmentGrade(s, "math", a, 5);
		assertEquals(s.getAssgGrade(a, "math"), 5.0);
	}
	
	@Test
	void testGetAssgGradeStr() {
		Teacher teach = new Teacher("jane", "doe");
		Course course = new Course("math");
		teach.addCourse(course);
		Student s = new Student("mary", "mess");
		teach.addStudent(course, s);
		Assignment a = new Assignment("a", 10.0);
		course.addAssg(a);
		teach.addAssignmentGrade(s, "math", a, 5);
		assertEquals(s.getAssgGrade("a", "math"), 5.0);
	}

}