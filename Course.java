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

	/* CONSTRUCTOR */
	public Course(String name) {
		this.name = name;
		this.assignments = new ArrayList<Assignment>();
		this.completed = false;
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

	public Double getOverallGrade() {
		Double sum = 0.0;
		int assgCount = 0;
		for (Assignment assg : assignments) {
			sum += assg.getStudentGrade();
			assgCount++;
		}
		
		double overallGrade = (sum / assgCount);
		return overallGrade;
	}

	public String getLetterGrade(){
		Double grade = getOverallGrade();

		if(grade >= 90.0){
			return "A";
		}
		else if(grade >= 80.0 && grade < 90){
			return "B";
		}
		else if(grade >= 70.0 && grade < 80.0){
			return "C";
		}
		else if(grade >= 60.0 && grade < 70.0){
			return "D";
		}
		else{
			return "F";
		}
	}

	//needs encapsulation
	public ArrayList<Assignment> getUngradedAssignments(){
		ArrayList<Assignment> ungraded = new ArrayList<Assignment>();
		for(Assignment assg : assignments){
			if(assg.isGraded() == false){
				ungraded.add(assg);
			}
		}
		return ungraded;
	}

	/* */
	public void addAssg(Assignment assg) {
		assignments.add(assg);
	}

	/* */
	public void removeAssg(Assignment assg) {
		assignments.remove(assg);
	}

}