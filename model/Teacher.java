package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Teacher extends Person {
	
	public HashMap<Course, StudentList> courseList;

	public Teacher(String fName, String lName) {
		super(fName, lName);
		courseList = new HashMap<Course, StudentList>();
	}
	
	
	
	// COURSE METHODS
	// can add others like get course by course number
	
	// add course and studentlist
	public void addCourse(Course c, StudentList slist) {
		courseList.put(c, slist);
	}
	
	// get all courses for a teacher
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Course c : courseList.keySet()) {
			courses.add(c); // add a copy course? or make method private
		}
		return courses;
	}
	
	// get either completed or current courses. gives completed if true.
	public ArrayList<Course> getCompletedOrCurrentCourses(boolean isComplete) {
		ArrayList<Course> courses = getCourses();
		for (Course c : courseList.keySet()) {
			if (c.isCompleted() == isComplete) courses.add(c);  // c needs method isComplete and getCopy
		}
		return getCourses();
	}
	
	// get a course from hashmap by course name
	public Course getCourse(String courseName) {
		for (Map.Entry<Course, StudentList> entry : this.courseList.entrySet()) {
			// find course, add student
			if (entry.getKey().getName().equals(courseName))  // courseName needs a getName method
				return entry.getKey();
		}
		return null;  // fix this
	}
	
	// get a course from hashmap by course name
	public Course getCourse(Course c) {
		for (Map.Entry<Course, StudentList> entry : this.courseList.entrySet()) {
			// find course, add student
			if (entry.getKey() == c)  // courseName needs a equals method
				return entry.getKey();
		}
		return null;  // fix this
	}

	
	
	
	// STUDENTLIST METHODS
	
	// get student list by course. may change to string courseName or something else
	public StudentList getStudentList(String courseName) {
		StudentList slist = courseList.get(courseName);
		return slist;  // slist needs a get copy method
	}
	
//	// returns student mean of a student list for a course
//	public double getStudentAverage(Course c) {
//		StudentList slist = courseList.get(getCourse(c));
//		return slist.getAverage(); // slist needs a get average method
//	}
//	
//	// returns student median of a student list for a course
//	public double getStudentMedian(String courseName) {
//		StudentList slist = courseList.get(getCourse(courseName));
//		return slist.getMedian(); // slist needs a get median method
//	}
	
	// group students
	public String makeXGroups(String courseName, int num) {
		StudentList slist = courseList.get(getCourse(courseName));
		return slist.makeXGroups(num);
	}
	
	public String makeGroupsOfXStudents(String courseName, int num) {
		StudentList slist = courseList.get(getCourse(courseName));
		return slist.makeGroupsOfXStudents(num);
	}
	
	// gets ungraded assignments
	public ArrayList<Assignment> getUngradedAssignments(String courseName) {
		return getCourse(courseName).getUngradedAssignments();  // course needs a getUngradedAssignment method
	}
	
	
	// edit this for sorting!!
//	// sort students in course
//	public StudentList sortStudentsBy(Course c, String sortBy) {
//		StudentList slist = courseList.get(getCourse(c));
//		return slist.sortBy(sortBy);  // slist needs sorting methods
//	}
	
	
	
	
	// INDIVIDUAL STUDENT METHODS
	
	// add grade for student on an assignment
	public void addAssignmentGrade(Student s, String courseName, Assignment a, double grade) {
		StudentList slist = courseList.get(getCourse(courseName));
		for (Student stu : slist) {
			if (stu == s) stu.setAssignmentGrade(courseName, a, grade);  // student needs method setAssignmentGrade
		}
	}
	
	// adds a student to a class
	public void addStudent(Course c, Student s) {
		courseList.get(getCourse(c)).addStudent(s);
	}
	
	// removes a student from a class
	public void removeStudent(Course c, Student s) {
		courseList.get(getCourse(c)).removeStudent(s.getFirstName());
	}
	
	// returns a single student's grade
	public double getStudentGrade(Course c, Student s) {
		double grade = -1.0;
		StudentList slist = courseList.get(getCourse(c));
		for (Student stu : slist) {  // slist needs to be iterable. if not, we can add .getStudents()
			if (stu == s) grade = s.getGrade(c);  // s needs a get grade method
		}
		return grade;  // need to check for negative number somewhere
	}

}
