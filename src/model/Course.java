
package model;

/*
 * File: Course.java
 * Author: Maya Vanderpool
 * Purpose: This class represents a course object
 */

import java.util.ArrayList;


public class Course {

	/* INSTANCE VARIABLES */
	private String name;
	private ArrayList<Assignment> assignments;
	private Boolean completed;
	private StudentList students;

	/* CONSTRUCTOR */
	public Course(String name) {
		this.name = name;
		this.assignments = new ArrayList<Assignment>();
		this.completed = false;
		this.students = new StudentList();
	}
	
	/* COPY CONSTRUCTOR */
	public Course(Course c){
		this.name = c.name;
		this.assignments = c.getAssignments();
		this.completed = c.completed;
		this.students = c.getStudents();
	}

	/* SETTERS AND GETTERS */
	public void setCompleted() {
		this.completed = true;
	}

	public Boolean isCompleted() {
		return completed;
	}

	public String getName() {
		return name;
	}

	
	public StudentList getStudents() {
		return students;
	}
	
	public void addStudents(Student stu) {
		students.addStudent(stu);
		stu.addCourse(this);
	}
	
	public void removeStudents(Student stu) {
		students.removeStudent(stu.getUserName());
	}


	/*
	 * getAssignments() - gets all assignments from course
	 * Returns: ArrayList of all Assignments in course
	 */
	public ArrayList<Assignment> getAssignments(){
		ArrayList<Assignment> all = new ArrayList<Assignment>();
		for(Assignment assg : assignments){
			all.add(new Assignment(assg));
		}
		return all;
	}
	

	
	/* addAssg(Assignment assg) - Adds an assignment to the assignment arraylist.
	 * Returns: nothing
	 */
	
	public void addAssg(Assignment assg) {
		assignments.add(assg);
		for(Student s: students.getStudents()) {
			s.addAssignment(this, assg);
		}
	}

	/* removeAssg(Assignment assg) - Removes an assignment from the assignment arraylist.
	 * Returns: nothing
	 */
	public void removeAssg(Assignment assg) {
		for (Assignment a: assignments) {
			if (a.getName().equals(assg.getName())) {
				assignments.remove(a);
				break;
			}
		}
		
		for (Student s : students.getStudents()) {
			s.removeAssignment(this, assg);
		}
	}
	
	/*
	 * getUngradedAssignments() - Gets all assignments that have not been graded
	 * Returns: string of all ungraded assignments
	 */
	public String getUngradedAssignments() {
		String str = "";
		for(Student s: students.getStudents()) {
			str += s.toString() + ":\n";
			ArrayList<Assignment> ungraded = s.getUngraded();
			if(ungraded.size() == 0) str += "All Assignments Graded";
			else {
				for (Assignment a : ungraded) {
					str += a.getName() + ", ";
				}
			}
			str += "\n";
		}
		return str;
	}
	

	
}