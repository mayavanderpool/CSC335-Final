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
	private AssgType type;
	private boolean dropped;

	/*CONSTRUCTOR */
	public Assignment(String name, Double points, AssgType t){
		this.name = name;
		this.points = points;
		this.type = t;
		dropped = false;
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
	
	public AssgType getType() {
		return type;
	}
	
	public void dropAssg() {
		dropped = true;
	}
	
	public boolean getDropped() {
		return dropped;
	}
	
	
 }