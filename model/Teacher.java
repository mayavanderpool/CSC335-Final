
package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Teacher extends Person {
	private ArrayList<Course> courseList;

	public Teacher(String fName, String lName) {
		super(fName, lName);
		this.courseList = new ArrayList<Course>();
	}
	
	// COURSE METHODS
	// can add others like get course by course number
	
	// add course and studentlist
	public void addCourse(Course course) {
		courseList.add(course);
	}
	
	// get all courses for a teacher
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Course c : courseList) {
			courses.add(c); // add a copy course? or make method private
		}
		return courses;
	}
	
	// get either completed or current courses. gives completed if true.
	public ArrayList<Course> getCompletedOrCurrentCourses(boolean isComplete) {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Course c : courseList) {
			if (c.isCompleted() == isComplete) courses.add(c);  // c needs method isComplete and getCopy
		}
		return courses;
	}
	
	//NOTE: DO WE NEED TO HAVE THOSE TWO METHODS?? WHY NOT KEEP BY STRING AND THEN IF WE NEED CALL C.GETNAME()?
	
	
	
	// get a course from hashmap by course name
	public Course getCourse(String courseName) {
		for(Course c: courseList) {
			if(c.getName().equals(courseName)){
				return c;
			}
		}
		return null;
	}
	
	// get a course from hashmap by course name
	public Course getCourse(Course courseName) {
		for(Course c: courseList) {
			if(c == courseName){
				return c;
			}
		}
		return null;
	}

	
	// STUDENTLIST METHODS
	
	// get student list by course. may change to string courseName or something else
	public StudentList getStudentList(Course c) {
		return c.getStudents();  // slist needs a get copy method
	}
	
	// returns student mean of a student list for a course
	public double getStudentAverage(Course c) {
		StudentList slist = getCourse(c).getStudents();
		return slist.getCourseAverage(c);
	}
	
	// returns student median of a student list for a course
	public double getStudentMedian(Course c) {
		StudentList slist = getCourse(c).getStudents();
		return slist.getCourseMedian(c); 
	}
	
	// group students
	public String makeXGroups(String courseName, int num) {
		StudentList slist = getCourse(courseName).getStudents();
		return slist.makeXGroups(num);
	}
	
	public String makeGroupsOfXStudents(String courseName, int num) {
		StudentList slist = getCourse(courseName).getStudents();
		return slist.makeGroupsOfXStudents(num);
	}
	
	// gets ungraded assignments
	public ArrayList<Assignment> getUngradedAssignments(String courseName) {
		for();  // course needs a getUngradedAssignment method
	}
	
	// SORTING
	public ArrayList<Student> getStudentByFirstName(String courseName) {
		return getCourse(courseName).getStudents().getStudentsByFirstName();
	}
	
	public ArrayList<Student> getStudentByLastName(String courseName) {
		StudentList list = getCourse(courseName).getStudents();
		return list.getStudentsByLastName();
	}
	
	public ArrayList<Student> getStudentByUsername(String courseName) {
		StudentList list = getCourse(courseName).getStudents();
		return list.getStudentsByUsername();
	}
	
	public ArrayList<Student> getStudentByCourseGrade(String courseName) {
		return getCourse(courseName).getStudents().getStudentsByFirstName();
	}
	
	
	
	// INDIVIDUAL STUDENT METHODS
	
	public void addAssignmentToCourse(String course, Assignment a) {
		Course c = getCourse(course);
		c.addAssg(a);
	}
	
	// add grade for student on an assignment
	public void addAssignmentGrade(Student s, String courseName, Assignment a, double grade) {
		StudentList slist = getCourse(courseName).getStudents();
		for (Student stu : slist) {
			if (stu == s) stu.setAssignmentGrade(courseName, a, grade);  // student needs method setAssignmentGrade
		}
	}
	
	// adds a student to a class
	public void addStudent(Course c, Student s) {
		c.addStudents(s);
	}
	
	// removes a student from a class
	public void removeStudent(Course c, Student s) {
		c.removeStudents(s);
	}
	
	// returns a single student's grade
	public double getStudentGrade(Course c, Student s) {
		StudentList slist = getCourse(c).getStudents();
		for (Student stu : slist) {
			if (stu == s) 
				return s.getGrade(c);  // student needs method setAssignmentGrade
		}
		return 0.0;
	}

}
