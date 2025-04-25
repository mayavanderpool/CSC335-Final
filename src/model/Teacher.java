package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

/*
 * File: Teacher.java
 * Author: Abby
 * Purpose: This class represents a teacher
 */
public class Teacher extends Person {
	
	/*INSTANCE VARIABLES*/
	private ArrayList<Course> courseList;

	/*CONSTRUCTOR */
	public Teacher(String fName, String lName, String user) {
		super(fName, lName, user, "teacher");
		this.courseList = new ArrayList<Course>();
	}
	
	/*COURSE METHODS*/
	
	/*
	 * addCourse(Course) - adds course to teachers courselist
	 * Returns: none
	 */
	public void addCourse(Course course) {
		courseList.add(course);
	}
	
	/*
	 * getCOurses()- get all courses for a teacher
	 * Returns: ArrayList of Courses
	 */
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Course c : courseList) {
			courses.add(c); // add a copy course? or make method private
		}
		return courses;
	}
	
	/* 
	 *getCompletedOrCurrentCourses(boolean)get either completed or current courses,
	 * gives completed if true.
	 * Return: ArrayList of Courses
	 */
	public ArrayList<Course> getCompletedOrCurrentCourses(boolean isComplete) {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Course c : courseList) {
			if (c.isCompleted() == isComplete) courses.add(c);  // c needs method isComplete and getCopy
		}
		return courses;
	}	
	
	
	/*
	 * getCoursr(String) - get a course from hashmap by course name
	 * Return: Course
	 */
	public Course getCourse(String courseName) {
		for(Course c: courseList) {
			if(c.getName().equals(courseName)){
				return c;
			}
		}
		return null;
	}
	
	
	/*STUDENTLIST METHODS*/
	
	/* 
	 * getStudentList(Course)get student list by course
	 * Return: StudentList of Course
	 */
	public StudentList getStudentList(Course c) {
		return c.getStudents(); 
	}
	
	/*
	 * getClassAverage(Course)- returns student mean of a student list for a course
	 * Return: double of average
	 */
	public double getClassAverage(Course c) {
		StudentList slist = c.getStudents();
		return slist.getCourseAverage(c);
	}

	/*
	 * getAssgClassAverage(Course, Assignment) - calculates the average grade on an assignment
	 * Returns: average for assignment
	 */
	public double getAssgClassAverage(Course c, Assignment a){
		StudentList slist = c.getStudents();
		return slist.getAssgAverage(a, c);
	}

	/*
	 * getAssgMedian(Course, Assignment) - calculates the median grade on an assignment
	 * Returns: String of median of assignment
	 */
	public String getAssgMedian(Course c, Assignment a){
		StudentList slist = c.getStudents();
		if(slist.getAssgMedian(a, c) == -1){
			return "0.0";
		}
		else{
			return "" + slist.getAssgMedian(a, c);
		}
	}

	/*
	 * getCompletedData(Course, Assignment) - gets amount of assignments that are graded
	 * Return: String representing what has been completed
	 */
	public String getCompletedData(Course c, Assignment a){
		int total = 0;
		int graded = 0;

		StudentList list = c.getStudents();
		for(Student s: list){
			if(s.getGraded(a, c.getName())){
				total++;
				graded++;
			}
			else{
				total++;
			}
		}
		return graded + "/" + total;
	}
	
	public String makeGroups(Course course, int number, boolean byCount) {
		if (byCount) {
			return makeXGroups(course.getName(), number);
		} else {
			return makeGroupsOfXStudents(course.getName(), number);
		}
	}
	
	
	/*
	 * makeXGroups(String, int)- returns students from course split into x amount of groups 
	 * Return: returns a string with each group
	 */
	public String makeXGroups(String courseName, int num) {
		StudentList slist = getCourse(courseName).getStudents();
		return slist.makeXGroups(num);
	}
	
	/*
	 * makeGroupsOfXStudents(String, int) splits students from course into 
	 * groups where each group will have about x members
	 * Return: returns a string with each group
	 */
	public String makeGroupsOfXStudents(String courseName, int num) {
		StudentList slist = getCourse(courseName).getStudents();
		return slist.makeGroupsOfXStudents(num);
	}
	
	/*
	 * getUngradedAssignments() - gets all assignments that havent been graded
	 * Return String of ungraded assignments
	 */
	public String getUngradedAssignments() {
		String str = "";
		for(Course c : courseList) {
			str += c.getName() + ":\n";
			str += c.getUngradedAssignments();
		}
		return str;
	}
	
	// SORTING
	
	/*
	 * sortStudents(String, String) uses helper methods to sort by specific way and update list
	 * Return: none
	 */
	public void sortStudents(String courseName, String x) {
		Course c = getCourse(courseName);
		if (c != null) {
			StudentList sList = c.getStudents();
			ArrayList<Student> sorted;
			switch(x) {
			case "f" :
				sorted = getStudentByFirstName(courseName);
				break;
			case "l" :
				sorted = getStudentByLastName(courseName);
				break;
			case "u" :
				sorted = getStudentByUsername(courseName);
				break;
			default :
				sorted = sList.getStudents();
			}
			sList.setStudents(sorted);
		}
	}
	
	/*
	 * getStudentsByFirstName(String) - gets list of students in course sorted by firstname
	 * Return: ArrayList of sorted Students
	 */
	public ArrayList<Student> getStudentByFirstName(String courseName) {
		return getCourse(courseName).getStudents().getStudentsByFirstName();
	}
	
	/*
	 * getStudentsByLasttName(String) - gets list of students in course sorted by lastname
	 * Return: ArrayList of sorted Students
	 */
	public ArrayList<Student> getStudentByLastName(String courseName) {
		StudentList list = getCourse(courseName).getStudents();
		return list.getStudentsByLastName();
	}
	
	/*
	 * getStudentsByUsername(String) - gets list of students in course sorted by username
	 * Return: ArrayList of sorted Students
	 */
	public ArrayList<Student> getStudentByUsername(String courseName) {
		StudentList list = getCourse(courseName).getStudents();
		return list.getStudentsByUsername();
	}
	
	/*
	 * getStudentsByFirstName(String) - gets list of students in course sorted by assgGrade
	 * Return: ArrayList of sorted Students
	 */
	public void getStudentByAssgGrade(String courseName, String assgName) {
		Course c = getCourse(courseName);
		if (c!= null) {
			StudentList sList = c.getStudents();
			ArrayList<Student> sorted = sList.getStudentsByAssgGrade(courseName, assgName);
			sList.setStudents(sorted);
		}
	}
	
	
	// INDIVIDUAL STUDENT METHODS
	
	/*
	 * addAssignmentToCourse(String, Assignment) - adds assignment to course
	 * Return: none
	 */
	public void addAssignmentToCourse(String course, Assignment a) {
		Course c = getCourse(course);
		c.addAssg(a);
	}
	
	/*
	 * removeAssignmentFromCoure(String, Assignment) - removes assignment from course
	 * Return : none
	 */
	public void removeAssignmentFromCourse(String course, Assignment a) {
		getCourse(course).removeAssg(a);
	}
	
	/*
	 * addAssignmentGrade(Student, String, Assignment, double) - add grade for student on an assignment
	 * Return: none
	 */
	public void addAssignmentGrade(Student s, String courseName, Assignment a, double grade) {
		if (s!= null) s.setAssignmentGrade(courseName, a, grade);
	}
	
	/*
	 * addStudent(Course Student) - adds a student to a class
	 * Return: none
	 */
	public void addStudent(Course c, Student s) {
		c.addStudents(s);
		s.addCourse(c);

		for(Assignment a : c.getAssignments()){
			s.addAssignment(c, a);
		}
	}
	
	/*
	 * removeStudent(Course, Student)- removes a student from a class
	 * Return: none
	 */
	public void removeStudent(Course c, Student s) {
		if(s != null) c.removeStudents(s);
		
	}
	
	/*
	 * getStudentGrade(Course, Student) - returns a single student's grade from course
	 * Return: double of student grade
	 */
	public double getStudentGrade(Course c, Student s) {
		StudentList slist = c.getStudents();
		for (Student stu : slist) {
			if (stu == s)
				return s.getGrade(c);
				
		}
		return 0.0;
	}

	public Assignment findAssignment(ArrayList<Assignment> a, String name){
		for(Assignment assg: a){
			if(assg.getName().equals(name)){
				return assg;
			}
		}
		return null;
	}

}