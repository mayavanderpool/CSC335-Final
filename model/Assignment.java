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

	/*CONSTRUCTOR */
	public Assignment(String name, Double points){
		this.name = name;
		this.points = points;
	}

	public Assignment(Assignment assg){
		this.name = assg.getName();
		this.points = assg.getTotalPoints();
	}

	/*SETTERS AND GETTERS */

	public Double getTotalPoints(){
		return points;
	}

	public String getName(){
		return name;
	}
 }