package model;

/*
 * File: Person.java
 * Author: Rees Hart
 * Purpose: This class represents a person object
 */
public class Person {
	
	/*INSTANCE VARIABLES*/
    private final String FIRST;
    private final String LAST;
    private String username;
	private String role;

	/*CONSTRUCTOR */
    /*
     * @pre first != null && last != null;
     */
    public Person(String first, String last, String username, String role){
        this.FIRST = first;
        this.LAST = last;
        this.username = username;
		this.role = role;
    }

    /*SETTERS AND GETTERS */
    
    public String getFirstName(){
        return FIRST;
    }
    public String getLastName(){
        return this.LAST;
    }
    /*
     * @pre username != null
     */
    public void setUser(String username){
        this.username = username;
    }

    public String getUserName(){
        return this.username;
    }
    
    @Override
    public String toString() {
    	return this.FIRST + " " + this.LAST;
    }
}