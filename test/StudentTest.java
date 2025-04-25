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
		Student s1 = new Student("Rees", "Hart", "rh");
		assertEquals(s1.getFirstName(), "Rees");
		assertEquals(s1.getLastName(), "Hart");
		assertEquals(s1.getGPA(), 0.0);
		assertEquals(s1.getCurrentCourses().size(), 0);
		assertEquals(s1.getCompletedCourses().size(), 0);
		assertEquals(s1.getAssignments().size(), 0);
	}
	
	@Test
	void testSet() {
		Student s1 = new Student("Rees", "Hart", "rh");
		Course c1 = new Course("Math");
		Teacher t1 = new Teacher("Jane", "Smith", "js");
		
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
		
		assertFalse(s1.getGraded(a1, "Art"));
		t1.addAssignmentGrade(s1, "Art", a1, 100);
		
		assertEquals(s1.getAssignments().size(), 1);
		assertTrue(s1.getGraded(a1, "Art"));
	}
	
	@Test
	void GPA() {
		Student s1 = new Student("Rees", "Hart", "rh");
		Teacher t1 = new Teacher("Jane", "Smith", "js");
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
		Student s1 = new Student("Rees", "Hart", "rh");
		Course c1 = new Course("Math");
		Course c2 = new Course("Art");
		Teacher t1 = new Teacher("Jane", "Smith", "js");
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
		Student s = new Student("Snuffy", "Snuffles", "ss");
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
		Student s1 = new Student("Alice", "Adams", "aa");
		Student s2 = new Student("Bob", "Benson", "bb");
		Student s3 = new Student("Alice", "Smith", "as");
		
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
		Student s1 = new Student("Alice", "Adams", "aa");
		Student s2 = new Student("Bob", "Adams", "ba");
		Student s3 = new Student("Alice", "Smith", "as");
		
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
		Student s1 = new Student("Alice", "Adams", "aa");
		s1.setUser("c");
		Student s2 = new Student("Bob", "Benson", "bb");
		s2.setUser("b");
		Student s3 = new Student("Charles", "Smith", "cs");
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
		Teacher t = new Teacher("fname", "lname", "t");
		Course course = new Course("math");
		Student s1 = new Student("Bob", "Johnson", "b");
		Student s2 = new Student("Alice", "Brown", "a");
		Student s3 = new Student("Alice", "Smith", "c");
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
	void testAssgFirstComparator() {
		Teacher t = new Teacher("fname", "lname", "t");
		Course course = new Course("math");
		Student s1 = new Student("Bob", "Johnson", "b");
		Student s2 = new Student("Alice", "Brown", "a");
		Student s3 = new Student("Alice", "Smith", "c");
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
		Collections.sort(students, Student.assgFirstComparator("math", "hw1"));
		assertEquals(100.0, students.get(0).getGrade(course));
		assertEquals("Johnson", students.get(2).getLastName());
		
	}
	
	@Test
	void testGetLetterGrade() {
		Teacher t = new Teacher("Snoopy", "Lastname", "sn");
		Course course = new Course("math");
		t.addCourse(course);
		Student sA = new Student("Jamie", "Lee", "jl");
		Student sB = new Student("Lindsay", "Lohan", "ll");
		Student sC = new Student("Lindsay", "Lohan", "sc");
		Student sD = new Student("Lindsay", "Lohan", "sd");
		Student sF = new Student("Lindsay", "Lohan", "ll");
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
        assertEquals(sB.getLetterGrade(course), "B");
        assertEquals(sC.getLetterGrade(course), "C");
        assertEquals(sD.getLetterGrade(course), "D");
        assertEquals(sF.getLetterGrade(course), "F");
	}

	@Test
	void testGradeNeeded() {
		Teacher t = new Teacher("Snoopy", "Lastname", "snoop");
		Course course = new Course("math");
		t.addCourse(course);
		Student sA = new Student("Jamie", "Lee", "jl");
        t.addStudent(course, sA);
        Assignment a1 = new Assignment("hw1", 100.0);     
        t.addAssignmentToCourse("math", a1);    
        t.addAssignmentGrade(sA, "math", a1, 80);;
        assertEquals(100, sA.gradeNeeded(90.0, course, 100.0));
        assertEquals(80, sA.gradeNeeded(80.0, course, 100.0));
	}
	
	@Test
	void testRemoveAssignment() {
		Student s = new Student("Snuffy", "Snuffles", "ss");
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
		Teacher teach = new Teacher("jane", "doe", "js");
		Course course = new Course("math");
		teach.addCourse(course);
		Student s = new Student("mary", "mess", "mm");
		teach.addStudent(course, s);
		Assignment a = new Assignment("a", 10.0);
		course.addAssg(a);
		teach.addAssignmentGrade(s, "math", a, 5);
		assertEquals(s.getAssgGrade(a, "math"), 5.0);
	}
	
	@Test
	void testGetAssgGradeStr() {
		Teacher teach = new Teacher("jane", "doe", "jd");
		Course course = new Course("math");
		teach.addCourse(course);
		Student s = new Student("mary", "mess", "mm");
		teach.addStudent(course, s);
		Assignment a = new Assignment("a", 10.0);
		course.addAssg(a);
		teach.addAssignmentGrade(s, "math", a, 5);
		assertEquals(s.getAssgGrade("a", "math"), 5.0);
	}

}
