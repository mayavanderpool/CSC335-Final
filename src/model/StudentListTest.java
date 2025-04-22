package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Assignment;
import model.Course;
import model.Student;
import model.StudentList;
import model.Teacher;

class StudentListTest {

	StudentList students = new StudentList();
	Student s1 = new Student("Alyssa", "Abe");
	Student s2 = new Student("Bob", "Brown");
	Student s3 = new Student("Carl", "Clyde");
	Course c1 = new Course("335");
	Assignment a1 = new Assignment("assg1", 100.0);
	Assignment a2 = new Assignment("assg2", 100.0);
			
	
	@Test
	void testAddStudent() {
		students.addStudent(s1);
		ArrayList<Student> s = students.getStudents();
		// for encapsulation, just check username if needed
		assertEquals(s1, s.get(0));
	}
	
	@Test
	void testRemoveStudent() {
		students.addStudent(s1);
		s1.setUser("aabe");
		students.addStudent(s2);
		s2.setUser("bobbyb");
		assertEquals(students.removeStudent("bobbyb"), "Student bobbyb removed");
		ArrayList<Student> s = students.getStudents();
		assertFalse(s.contains(s2));
		assertEquals(students.removeStudent("notastudent"), "Student notastudent not found");
	}
	
	@Test
	void testGetStudentsByFirstName() {
		students.addStudent(s2);
		students.addStudent(s3);
		students.addStudent(s1);
		ArrayList<Student> s = students.getStudentsByFirstName();
		assertEquals(s.get(0).getFirstName(), "Alyssa");
		assertEquals(s.get(1).getFirstName(), "Bob");
		assertEquals(s.get(2).getFirstName(), "Carl");
	}
	
	@Test
	void testGetStudentsByLastName() {
		students.addStudent(s2);
		students.addStudent(s3);
		students.addStudent(s1);
		ArrayList<Student> s = students.getStudentsByLastName();
		assertEquals(s.get(0).getLastName(), "Abe");
		assertEquals(s.get(1).getLastName(), "Brown");
		assertEquals(s.get(2).getLastName(), "Clyde");
	}
	
	@Test
	void testGetStudentsByUsername() {
		students.addStudent(s2);
		s2.setUser("bobbyb");
		students.addStudent(s3);
		s3.setUser("carlc");
		students.addStudent(s1);
		s1.setUser("aabe");
		ArrayList<Student> s = students.getStudentsByUsername();
		assertEquals(s.get(0).getUserName(), "aabe");
		assertEquals(s.get(1).getUserName(), "bobbyb");
		assertEquals(s.get(2).getUserName(), "carlc");
	}
	
	@Test
	void testMakeXGroups() {
		assertEquals(students.makeXGroups(16), "Only 15 Groups Allowed");
		students.addStudent(s1);
		students.addStudent(s2);
		students.addStudent(s3);
		assertEquals(students.makeXGroups(4), "Not enough students for groups");
		String groups = students.makeXGroups(2);
		assertTrue(groups.contains("Group 2")); // second group name
	}
	
	@Test
	void testMakeGroupsOfXStudents() {
		assertEquals(students.makeGroupsOfXStudents(5), "Not enough students for groups");
		students.addStudent(s1);
		students.addStudent(s2);
		students.addStudent(s3);
		String groups = students.makeXGroups(2);
		assertFalse(groups.contains("Group 3")); // should only be two groups
	}
	
	@Test
	void testToString() {
		students.addStudent(s1);
		students.addStudent(s2);
		String str = s1.getFirstName() + " " + s1.getLastName()  + "\n" + s2.getFirstName() + " " + s2.getLastName() + "\n";
		assertEquals(students.toString(), str);
	}
	
	@Test
	void testGetClassAverage() {
		Teacher teach = new Teacher("jane", "doe");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0);
		Student s = new Student("Stu", "Dent");
		Student s2 = new Student("St", "Udent");
		teach.addStudent(c, s2);
		teach.addStudent(c, s);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentGrade(s, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		assertEquals(teach.getClassAverage(c), 90.0);
	}
	
	@Test
	void testgetAssgClassAverage(){
		Teacher teach = new Teacher("jane", "doe");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0);
		Student s = new Student("Stu", "Dent");
		Student s2 = new Student("St", "Udent");
		teach.addStudent(c, s2);
		teach.addStudent(c, s);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentGrade(s, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		assertEquals(teach.getAssgClassAverage(c, a), 9.0);
	}
	
	@Test
	void testGetStudentMedian() {
		Teacher teach = new Teacher("jane", "doe");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0);
		Student s1 = new Student("Stu", "Dent");
		Student s2 = new Student("St", "Udent");
		Student s3 = new Student("Stud", "Ent");
		teach.addStudent(c, s3);
		teach.addStudent(c, s2);
		teach.addStudent(c, s1);
		teach.addAssignmentToCourse("science", a);

		teach.addAssignmentGrade(s1, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		teach.addAssignmentGrade(s3, "science", a, 9.0);
		assertEquals(teach.getAssgMedian(c, a), "9.0");
	}
	
	@Test
	void testGetStudentsByAssgGrade() {
		Teacher teach = new Teacher("jane", "doe");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0);
		Student s1 = new Student("Stu", "Dent");
		Student s2 = new Student("St", "Udent");
		Student s3 = new Student("S", "Tudent");
		teach.addStudent(c, s1);
		teach.addStudent(c, s2);
		teach.addStudent(c, s3);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentGrade(s3, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		teach.addAssignmentGrade(s1, "science", a, 5.0);
		ArrayList<Student> list = teach.getStudentByAssgGrade("science", "a");
		assertEquals(list.get(0), s1);
		assertEquals(list.get(1), s2);
		assertEquals(list.get(2), s3);
	}

}