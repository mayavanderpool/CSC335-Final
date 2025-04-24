package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.AssgType;
import model.Assignment;
import model.Course;
import model.Student;
import model.StudentList;
import model.Teacher;

class TeacherTest {

	@Test
	void testAddCourse() {
		Teacher first = new Teacher("jane", "doe", "janed");
		Course science = new Course("science");
		
		assertEquals(first.getCourses().size(), 0);
		first.addCourse(science);
		assertEquals(first.getCourses().size(), 1);
	}
	
	@Test
	void testGetCurrentOrCompletedCourses() {
		Teacher first = new Teacher("jane", "doe", "janed");
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
		Teacher first = new Teacher("jane", "doe", "janed");
		Course science = new Course("science");
		
		assertTrue(first.getCourse("science") == null);

		first.addCourse(science);
		
		assertTrue(first.getCourse("science") == science);
		
	}
	
	@Test
	void testGetStudentList() {
		Teacher first = new Teacher("jane", "doe", "janed");
		Course science = new Course("science");
		first.addCourse(science);
		Student stu = new Student("john", "smith", "jsm");
		
		StudentList list = first.getStudentList(science);
		assertEquals(list.getStudents().size(), 0);
		first.addStudent(science, stu);
		
		list = first.getStudentList(science);
		assertEquals(list.getStudents().size(), 1);
	}
	
	@Test
	void testMakeXGroups() {
		Teacher teach = new Teacher("jane", "doe", "janed");
		Student s = new Student("mary", "mess", "mm");
		Student s1 = new Student("abby", "zczepkowski", "az");
		Student s2 = new Student("zeke", "almond", "za");
		Student s3 = new Student("zeke1", "almond1", "zaa");
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
		Teacher teach = new Teacher("jane", "doe", "janed");
		Student s = new Student("mary", "mess", "mm");
		Student s1 = new Student("abby", "zczepkowski", "az");
		Student s2 = new Student("zeke", "almond", "za");
		Student s3 = new Student("zeke1", "almond1", "zaa");
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
		Teacher teach = new Teacher("name1", "name2", "uname");
		teach.addCourse(course);
		Assignment a1 = new Assignment("a1", 10.0, AssgType.HOMEWORK);
		Student stu = new Student("john", "smith", "js");
		teach.addStudent(course, stu);
		assertEquals(teach.getUngradedAssignments(), "science:\njohn smith:\nAll Assignments Graded\n");
		teach.addAssignmentToCourse("science", a1);
		assertEquals(teach.getUngradedAssignments(), "science:\njohn smith:\na1, \n");
	}
	
	@Test
	void testAddAssignmentGrade() {
		Teacher teach = new Teacher("jane", "doe", "mdcd");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Student s = new Student("Stu", "Dent", "st");
		Student s2 = new Student("St", "Udent", "stt");
		teach.addStudent(c, s2);
		teach.addStudent(c, s);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentGrade(s, "science", a, 10.0);
		assertEquals(teach.getStudentGrade(c, s), 100.0);
	}
	
	@Test
	void testGetStudentsByFirstName() {
		Teacher teach = new Teacher("jane", "doe", "vfe");
		Student s = new Student("mary", "mess", "cwe");
		Student s1 = new Student("abby", "zczepkowski", "cwv");
		Student s2 = new Student("zeke", "almond", "uyt");
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
		Teacher teach = new Teacher("jane", "doe", "rew");
		Student s = new Student("mary", "mess", "yhg");
		Student s1 = new Student("abby", "zczepkowski", "asd");
		Student s2 = new Student("zeke", "almond", "lmk");
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
		Teacher teach = new Teacher("jane", "doe", "janed");
		Student s = new Student("mary", "mess", "a");
		Student s1 = new Student("abby", "zczepkowski", "b");
		Student s2 = new Student("zeke", "almond", "c");
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
	void testGetStudentsByAssgGrade() {
		Teacher teach = new Teacher("jane", "doe", "janed");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Student s1 = new Student("Stu", "Dent", "dskj");
		Student s2 = new Student("St", "Udent", "ds");
		Student s3 = new Student("S", "Tudent", "mor");
		
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
	
	
	@Test
	void testRemoveStudent() {
		Teacher teach = new Teacher("jane", "doe", "janed");
		Student s = new Student("mary", "mess", "marym");
		Course course = new Course("math");
		course.addStudents(s);
		teach.addCourse(course);
		assertEquals(teach.getStudentList(course).getStudents().size(), 1);
		teach.removeStudent(course, s);
		assertEquals(teach.getStudentList(course).getStudents().size(), 0);
	}
	
	@Test
	void testGetClassAverage() {
		Teacher teach = new Teacher("jane", "doe", "mcfk");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Student s = new Student("Stu", "Dent", "cew");
		Student s2 = new Student("St", "Udent", "plk");
		teach.addStudent(c, s2);
		teach.addStudent(c, s);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentGrade(s, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		assertEquals(teach.getClassAverage(c), 90.0);
	}
	
	@Test
	void testgetAssgClassAverage(){
		Teacher teach = new Teacher("jane", "doe", "asd");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Student s = new Student("Stu", "Dent", "lkn");
		Student s2 = new Student("St", "Udent", "edf");
		teach.addStudent(c, s2);
		teach.addStudent(c, s);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentGrade(s, "science", a, 10.0);
		teach.addAssignmentGrade(s2, "science", a, 8.0);
		assertEquals(teach.getAssgClassAverage(c, a), 9.0);
	}
	
	@Test
	void testGetStudentMedian() {
		Teacher teach = new Teacher("jane", "doe", "cfew");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Student s1 = new Student("Stu", "Dent", "few");
		Student s2 = new Student("St", "Udent", "red");
		Student s3 = new Student("Stud", "Ent", "plm");
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
	void testGetStudentgrade() {
		Teacher teach = new Teacher("jane", "doe", "qwe");
		Course course = new Course("math");
		teach.addCourse(course);
		
		Student s = new Student("mary", "mess", "pmf");
		Student s1 = new Student("minnie", "mouse", "sad");
		teach.addStudent(course, s);
		teach.addStudent(course, s1);
		
		Assignment assg1 = new Assignment("assg1", 100.0, AssgType.HOMEWORK);
		course.addAssg(assg1);
		teach.addAssignmentGrade(s, "math", assg1, 55);
		assertTrue(.55 - teach.getStudentGrade(course, s) < .000001);
		Assignment assg2 = new Assignment("assg2", 80.0, AssgType.HOMEWORK);
		course.addAssg(assg2);
		teach.addAssignmentGrade(s, "math", assg2, 89);
		assertEquals(teach.getStudentGrade(course, s), 80.0);
	}
	
	@Test
	void testRemoveAssignment() {
		Teacher teach = new Teacher("jane", "doe", "qwf");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Assignment b = new Assignment("b", 10.0, AssgType.HOMEWORK);
		Student s = new Student("Stu", "Dent", "pkc");
		Student s2 = new Student("Stu", "Pid", "spo");
		teach.addStudent(c, s);
		teach.addStudent(c, s2);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentToCourse("science", b);
		assertEquals(teach.getCourse("science").getAssignments().size(), 2);
		assertEquals(teach.getStudentList(c).getStudents().get(0).getAssignments().size(), 2);
		teach.removeAssignmentFromCourse("science", a);
		assertEquals(teach.getCourse("science").getAssignments().size(), 1);
		assertEquals(teach.getStudentList(c).getStudents().get(0).getAssignments().size(), 1);
	}
	
	@Test
	void testGetCompletedData() {
		Teacher teach = new Teacher("jane", "doe", "janed");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Student s = new Student("Stu", "Dent", "stu");
		Student s2 = new Student("Stu", "Pid", "pid");
		teach.addStudent(c, s);
		teach.addStudent(c, s2);
		assertEquals(teach.getCompletedData(c, a), "0/2");
	}
	
	@Test
	void testDropAssignment() {
		Teacher teach = new Teacher("jane", "doe", "qwf");
		Course c = new Course("science");
		teach.addCourse(c);
		Assignment a = new Assignment("a", 10.0, AssgType.HOMEWORK);
		Assignment b = new Assignment("b", 10.0, AssgType.HOMEWORK);
		Student s = new Student("Stu", "Dent", "pkc");
		Student s2 = new Student("Stu", "Pid", "spo");
		teach.addStudent(c, s);
		teach.addStudent(c, s2);
		teach.addAssignmentToCourse("science", a);
		teach.addAssignmentToCourse("science", b);
		teach.addAssignmentGrade(s, "science", a, 2);
		teach.addAssignmentGrade(s2, "science", a, 3);
		teach.addAssignmentGrade(s, "science", b, 9);
		teach.addAssignmentGrade(s2, "science", b, 7);
		teach.dropAssg("science", "homework", 1);
		assertEquals(teach.getClassAverage(c), 80.0);
	}
	
	
}
