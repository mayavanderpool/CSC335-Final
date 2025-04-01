/*
 * File: Assignment.java
 * Author: Maya Vanderpool
 * Purpose: 
 */

 public class Assignment{

	/*INSTANCE VARIABLES*/
	private String name;
	private Double points;
	private Boolean graded;
	private Double studentGrade;

	/*CONSTRUCTOR */
	public Assignment(String name, Double points){
		this.name = name;
		this.points = points;
		this.graded = false;
	}

	/*SETTERS AND GETTERS */
	public void setGraded(){
		this.graded = true;
	}

	public Boolean isGraded(){
		return graded;
	}

	public Double getTotalPoints(){
		return points;
	}

	public String getName(){
		return name;
	}

	public void setStudentGrade(Double grade){
		studentGrade = grade;
	}

	public Double getStudentGrade(){
		return studentGrade;
	}


 }