
package model;

/*
 * File: Course.java
 * Author: Maya Vanderpool
 * Purpose: 
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
	
	//
	public void addStudents(Student stu) {
		students.addStudent(stu);
	}
	
	public void removeStudents(Student stu) {
		students.removeStudent(stu.getUserName());
	}


	public ArrayList<Assignment> getAssignments(){
		ArrayList<Assignment> all = new ArrayList<Assignment>();
		for(Assignment assg : assignments){
			all.add(new Assignment(assg));
		}
		return all;
	}
	/*

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
		assignments.remove(assg);
		
		for (Student s : students.getStudents()) {
			s.removeAssignment(this, assg);
		}
	}
	
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