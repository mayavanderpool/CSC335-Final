/*
 * File: Course.java
 * Author: Maya Vanderpool
 * Purpose: 
 */

import java.util.ArrayList;

public class Course{

	/*INSTANCE VARIABLES*/
	private String name;
	private ArrayList<Assignment> assignments;
	private Boolean completed;

	/*CONSTRUCTOR*/
	public Course(String name){
		this.name = name;
		this.assignments = new ArrayList<Assignment>();
		this.completed = false;
	}

	/*SETTERS AND GETTERS */
	public void setCompleted(){
		this.completed = true;
	}

	public Boolean isCompleted(){
		return completed;
	}

	public String getName(){
		return name;
	}

	/* */
	public void addAssg(Assignment assg){
		assignments.add(assg);
	}

	/* */
	public void removeAssg(Assignment assg){
		assignments.remove(assg);
	}

	
 }