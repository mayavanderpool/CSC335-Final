package model;

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

	public Assignment(Assignment assg){
		this.name = assg.getName();
		this.points = assg.getTotalPoints();
		this.graded = assg.isGraded();
		this.studentGrade = assg.getStudentGrade();
	}

	/*SETTERS AND GETTERS */

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
		this.graded = true;
	}

	public Double getStudentGrade(){
		return studentGrade;
	}


 }