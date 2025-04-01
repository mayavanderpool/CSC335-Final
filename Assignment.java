/*
 * File: Assignment.java
 * Author: Maya Vanderpool
 * Purpose: 
 */

 public class Assignment{

	/*INSTANCE VARIABLES*/
	private String name;
	private Float points;
	private Boolean graded;

	/*CONSTRUCTOR */
	public Assignment(String name, Float points, Boolean graded){
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

	public Float getTotalPoints(){
		return points;
	}

	public String getName(){
		return name;
	}

 }