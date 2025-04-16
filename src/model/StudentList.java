/*File: StudentList.java
Author: 
Purpose: StudentList stores a list of students and defines functions for
different ways to sort or split the students
*/

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import java.lang.Iterable;

public class StudentList implements Iterable<Student>{

	/*INSTANCE VARIABLES*/
	private ArrayList<Student> students;

	/*CONSTRUCTOR */
	public StudentList() {
		this.students = new ArrayList<Student>();
	}

	/*SETTERS AND GETTERS */
	
	public void	addStudent(Student s) {
		// can add check to make sure username is not used
		// not sure if there is somewhere else that would be better to check
		students.add(s);
	}
	
	public String removeStudent(String username) {
		// assumes usernames are unique 
		for (Student s : students) {
			if (s.getUserName().equals(username)) {
				students.remove(s);
				return "Student " + username + " removed";
			}
		}
		return "Student " + username + " not found";
	}
	
	public ArrayList<Student> getStudents() {
		// can make deep copy if needed
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		return listCopy;
	}
	
	// use strategy design pattern to sort by first name
	public ArrayList<Student> getStudentsByFirstName() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.fisrtNameFirstComparator());
		return listCopy;
	}
	
	// sort by last name
	public ArrayList<Student> getStudentsByLastName() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.lastNameFirstComparator());
		return listCopy;
	}
	
	// sort by username
	public ArrayList<Student> getStudentsByUsername() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.userNameFirstComparator());
		return listCopy;
	}
	
	// sort by grade in course
	public ArrayList<Student> getStudentsByCourseGrade(Course c) {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.gradeFirstComparator(c));
		return listCopy;
	}
	
	
	/*
	 * This method returns students split into x amount of groups 
	 * parameters: x is an integer representing the amount of groups to
	 * split students into 
	 * return: returns a string with each group
	 */
	public String makeXGroups(int x) {
		String groupsString = "";
		if (x > 15) {return "Only 15 Groups Allowed";}
		if (x > students.size()) {return "Not enough students for groups";}
		
		ArrayList<Student> shuffled = shuffleStudents();

		ArrayList<ArrayList<Student>> groups = new ArrayList<ArrayList<Student>>();

		for (int i = 0; i < x; i++) {
			// add arraylist for each group
			groups.add(new ArrayList<Student>());
		}

		// distribute students into groups
		for (int i = 0; i < shuffled.size(); i++) {
			int j = i % x;
			groups.get(j).add(shuffled.get(i));
		}
		
		// return string of groups
		for (int i = 0; i < x; i++) {
			groupsString += ("Group " + (i + 1) + ":\n");
			for(Student s : groups.get(i)) {
				groupsString += s.getFirstName() + " " + s.getLastName() + "\n";
			}
			groupsString += "\n";
		}
		return groupsString;
	}
	
	/*
	 * This method splits students into groups based where each group will
	 * have about x members
	 * parameters: x is an integer representing the number of students per group 
	 * return: returns a string with each group
	 */
	public String makeGroupsOfXStudents(int x) {
		int n = students.size() / x;
		if (n == 0) n = 1;
		return makeXGroups(n);
	}
	
	public double getCourseAverage(Course c) {
		int count = 0;
		double total = 0; 
		for (Student s : students) {
			count += 1;
			total += s.getGrade(c);
		}
		return total / count;
	}
	
	public double getCourseMedian(Course c) {
		ArrayList<Student> slist = getStudentsByCourseGrade(c);
		int len = slist.size();
		
		if (len % 2 == 1) return slist.get(len/2).getGrade(c);
		double m1 = slist.get(len/ 2 - 1).getGrade(c);
		double m2 = slist.get(len / 2).getGrade(c);
		return (m1 + m2) / 2.0;
	}

	/*
	 * This method returns a shuffled list of students 
	 * parameters: none 
	 * return: returns a shuffled ArrayList of students
	 */
	private ArrayList<Student> shuffleStudents() {
		ArrayList<Student> shuffled = new ArrayList<>(students);
		Collections.shuffle(shuffled);
		return shuffled;
	}
	
	
	@Override
	public Iterator<Student> iterator() {
		return getStudentsByFirstName().iterator();
	}
	
	public String toString() {
		String str = "";
		for (Student s : students) {
			str += s.getFirstName() + " " + s.getLastName() + "\n";
		}
		return str;
	}

}