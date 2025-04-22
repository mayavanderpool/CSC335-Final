package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Teacher extends Person {
	private ArrayList<Course> courseList;

	public Teacher(String fName, String lName, String user) {
		super(fName, lName, user, "teacher");
		this.courseList = new ArrayList<Course>();
	}
	
	// COURSE METHODS
	
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
	


	
	// STUDENTLIST METHODS
	
	// get student list by course. may change to string courseName or something else
	public StudentList getStudentList(Course c) {
		return c.getStudents();  // slist needs a get copy method
	}
	
	// returns student mean of a student list for a course
	public double getClassAverage(Course c) {
		StudentList slist = c.getStudents();
		return slist.getCourseAverage(c);
	}

	/*
	 * getAssgClassAverage(Course c, Assignment a) - returns the average grade on an assignment
	 */
	public double getAssgClassAverage(Course c, Assignment a){
		StudentList slist = c.getStudents();
		return slist.getAssgAverage(a, c);
	}

	/*
	 * getAssgMedian(Course c, Assignment a) - returns the median grade on an assignment
	 */
	public String getAssgMedian(Course c, Assignment a){
		StudentList slist = c.getStudents();
		if(slist.getAssgMedian(a, c) == -1){
			return "";
		}
		else{
			return "" + slist.getAssgMedian(a, c);
		}
	}

	public String getCompletedData(Course c, Assignment a){
		int total = 0;
		int graded = 0;

		StudentList list = c.getStudents();
		for(Student s: list){
			if(s.getGraded().contains(a)){
				total++;
				graded++;
			}
			else{
				total++;
			}
		}
		return graded + "/" + total;
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
	public String getUngradedAssignments() {
		String str = "";
		for(Course c : courseList) {
			str += c.getName() + ":\n";
			str += c.getUngradedAssignments();
		}
		return str;
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
	
	public ArrayList<Student> getStudentByAssgGrade(String courseName, String assgName) {
		StudentList list = getCourse(courseName).getStudents();
		return list.getStudentsByAssgGrade(courseName, assgName);
	}
	
	
	// INDIVIDUAL STUDENT METHODS
	
	public void addAssignmentToCourse(String course, Assignment a) {
		Course c = getCourse(course);
		c.addAssg(a);
	}
	
	public void removeAssignmentFromCourse(String course, Assignment a) {
		getCourse(course).removeAssg(a);
	}
	
	// add grade for student on an assignment
	public void addAssignmentGrade(Student s, String courseName, Assignment a, double grade) {
		s.setAssignmentGrade(courseName, a, grade);
	}
	
	// adds a student to a class
	public void addStudent(Course c, Student s) {
		c.addStudents(s);
		s.addCourse(c);
	}
	
	// removes a student from a class
	public void removeStudent(Course c, Student s) {
		c.removeStudents(s);
	}
	
	// returns a single student's grade in decimal form
	public double getStudentGrade(Course c, Student s) {
		StudentList slist = c.getStudents();
		for (Student stu : slist) {
			if (stu == s)
				return s.getGrade(c);
				
		}
		return 0.0;
	}

}