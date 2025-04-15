package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
	
//	@Test
//	void testGetStudentAverage() {
//		Teacher teach = new Teacher("jane", "doe");
//		Student s = new Student("mary", "mess");
//		Student s1 = new Student("abby", "zczepkowski");
//		Student s2 = new Student("zeke", "almond");
//		Course course = new Course("math");
//		course.addStudents(s);
//		course.addStudents(s1);
//		course.addStudents(s2);
//		teach.addCourse(course);
//		assertEquals(teach.getStudentAverage(course), 0.0);
//		Assignment a1 = new Assignment("a1", 10.0);
//		course.addAssg(a1);
//		s.setAssignmentGrade("math", a1, 0.0);
//		s1.setAssignmentGrade("math", a1, 10.0);
//		s2.setAssignmentGrade("math", a1, 10);
//		System.out.println(s1.getGrade(course));
//		// set overall grade
//		assertEquals(teach.getStudentAverage(course), 7.0);
//	}
	
	@Test
	void testMakeXGroups() {
		Teacher teach = new Teacher("jane", "doe");
		Student s = new Student("mary", "mess");
		Student s1 = new Student("abby", "zczepkowski");
		Student s2 = new Student("zeke", "almond");
		Student s3 = new Student("zeke1", "almond1");
		Course course = new Course("math");
		course.addStudents(s);
		course.addStudents(s1);
		course.addStudents(s2);
		course.addStudents(s3);
		teach.addCourse(course);
		assertEquals(teach.makeXGroups("math", 2).split("\n").length, 7);
		assertEquals(teach.makeXGroups("math", 1).split("\n").length, 5);
	}
	
	@Test
	void testMakeGroupsOfXStudents() {
		Teacher teach = new Teacher("jane", "doe");
		Student s = new Student("mary", "mess");
		Student s1 = new Student("abby", "zczepkowski");
		Student s2 = new Student("zeke", "almond");
		Student s3 = new Student("zeke1", "almond1");
		Course course = new Course("math");
		course.addStudents(s);
		course.addStudents(s1);
		course.addStudents(s2);
		course.addStudents(s3);
		teach.addCourse(course);
		assertEquals(teach.makeGroupsOfXStudents("math", 2).split("\n").length, 7);
		assertEquals(teach.makeGroupsOfXStudents("math", 4).split("\n").length, 5);
	}
	
	@Test
	void testGetUngradedAssignments() {
		Course course = new Course("science");
		Teacher teach = new Teacher("name1", "name2");
		teach.addCourse(course);
		Assignment a1 = new Assignment("a1", 10.0);
		Student stu = new Student("john", "smith");
		teach.addStudent(course, stu);
		assertEquals(teach.getUngradedAssignments(), "science:\njohn smith:\nAll Assignments Graded\n");
		teach.addAssignmentToCourse("science", a1);
		assertEquals(teach.getUngradedAssignments(), "science:\njohn smith:\na1, \n");
	}
	
	@Test
	void testAddAssignmentGrade() {
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
		assertEquals(teach.getStudentGrade(c, s), 1.0);
	}
	
	@Test
	void testGetStudentsByFirstName() {
		Teacher teach = new Teacher("jane", "doe");
		Student s = new Student("mary", "mess");
		Student s1 = new Student("abby", "zczepkowski");
		Student s2 = new Student("zeke", "almond");
		Course course = new Course("math");
		course.addStudents(s);
		course.addStudents(s1);
		course.addStudents(s2);
		teach.addCourse(course);
		assertEquals(teach.getStudentByFirstName("math").get(0).getFirstName(), "abby");
		assertEquals(teach.getStudentByFirstName("math").get(2).getFirstName(), "zeke");
	}
	
	@Test
	void testGetStudentsByLastName() {
		Teacher teach = new Teacher("jane", "doe");
		Student s = new Student("mary", "mess");
		Student s1 = new Student("abby", "zczepkowski");
		Student s2 = new Student("zeke", "almond");
		Course course = new Course("math");
		course.addStudents(s);
		course.addStudents(s1);
		course.addStudents(s2);
		teach.addCourse(course);
		assertEquals(teach.getStudentByLastName("math").get(0).getFirstName(), "zeke");
		assertEquals(teach.getStudentByLastName("math").get(2).getFirstName(), "abby");
	}
	
	@Test
	void testGetStudentsByUsername() {
		Teacher teach = new Teacher("jane", "doe");
		Student s = new Student("mary", "mess");
		s.setUser("a");
		Student s1 = new Student("abby", "zczepkowski");
		s1.setUser("b");
		Student s2 = new Student("zeke", "almond");
		s2.setUser("c");
		Course course = new Course("math");
		course.addStudents(s);
		course.addStudents(s1);
		course.addStudents(s2);
		teach.addCourse(course);
		ArrayList<Student> list = teach.getStudentByUsername("math");
		assertEquals(list.get(0).getUserName(), "a");
		assertEquals(list.get(2).getUserName(), "c");
	}
	
	
	@Test
	void testRemoveStudent() {
		Teacher teach = new Teacher("jane", "doe");
		Student s = new Student("mary", "mess");
		Course course = new Course("math");
		course.addStudents(s);
		teach.addCourse(course);
		assertEquals(teach.getStudentList(course).getStudents().size(), 1);
		teach.removeStudent(course, s);
		assertEquals(teach.getStudentList(course).getStudents().size(), 0);
	}
	
	@Test
	void testGetStudentAverage() {
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
		assertEquals(teach.getStudentAverage(c), 0.9);
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
		teach.addStudent(c, s2);
		teach.addStudent(c, s1);
		teach.addAssignmentToCourse("science", a);

		teach.addAssignmentGrade(s1, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		teach.addAssignmentGrade(s3, "science", a, 9.0);
		assertEquals(teach.getStudentMedian(c), 0.9);
	}
	
	@Test
	void testGetStudentByCourseGrade() {
		Teacher t = new Teacher("Tea", "Cher");
		Course course = new Course("math");
		t.addCourse(course);
		Assignment a1 = new Assignment("hw1", 100.0);
		Assignment a2 = new Assignment("hw2", 80.0);
		t.addAssignmentToCourse("math", a1);
		t.addAssignmentToCourse("math", a2);
		Student s1 = new Student("Stu", "Dent");
		Student s2 = new Student("Stu", "Pid");
		Student s3 = new Student("Stu", "ck");
		t.addStudent(course, s1);
		t.addStudent(course, s2);
		t.addStudent(course, s3);
		t.addAssignmentGrade(s1, "math", a1, 0);
		t.addAssignmentGrade(s1, "math", a2, 100);
		t.addAssignmentGrade(s2, "math", a1, 75);
		t.addAssignmentGrade(s2, "math", a2, 50);
		t.addAssignmentGrade(s3, "math", a1, 100);
		t.addAssignmentGrade(s3, "math", a2, 100);
		
		ArrayList<Student> students = t.getStudentByCourseGrade("math");
		assertEquals(students.get(0).getLastName(),"Dent");
		
	}
	

	
	@Test
	void testGetStudentgrade() {
		Teacher teach = new Teacher("jane", "doe");
		Course course = new Course("math");
		teach.addCourse(course);
		
		Student s = new Student("mary", "mess");
		Student s1 = new Student("minnie", "mouse");
		teach.addStudent(course, s);
		teach.addStudent(course, s1);
		
		Assignment assg1 = new Assignment("assg1", 100.0);
		course.addAssg(assg1);
		teach.addAssignmentGrade(s, "math", assg1, 55);
		assertEquals(teach.getStudentGrade(course, s), .55);
		
		Assignment assg2 = new Assignment("assg2", 80.0);
		course.addAssg(assg2);
		teach.addAssignmentGrade(s, "math", assg2, 89);
		assertEquals(teach.getStudentGrade(course, s), .8);
	}
	
	@Test
	void testRemoveAssignment() {
		Teacher teach = new Teacher("jane", "doe");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0);
		Assignment b = new Assignment("b", 10.0);
		Student s = new Student("Stu", "Dent");
		Student s2 = new Student("Stu", "Pid");
		teach.addStudent(c, s);
		teach.addStudent(c, s2);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentToCourse("science", b);
		assertEquals(teach.getCourse(c).getAssignments().size(), 2);
		assertEquals(teach.getStudentList(c).getStudents().get(0).getAssignments().size(), 2);
		teach.removeAssignmentFromCourse("science", a);
		assertEquals(teach.getCourse(c).getAssignments().size(), 1);
		assertEquals(teach.getStudentList(c).getStudents().get(0).getAssignments().size(), 1);
	}
}