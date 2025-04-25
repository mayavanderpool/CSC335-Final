/*File: StudentList.java
Author: Lauren Greene
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
	
	/*COPY CONSTRUCTOR */
	public StudentList(StudentList sList){
		this.students = sList.getStudents();
	}

	/*SETTERS AND GETTERS */
	
	public void	addStudent(Student s) {
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
	
	/*
	 * getStudents() - returns ArrayList of all students
	 * Returns: none
	 */
	public ArrayList<Student> getStudents() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		return listCopy;
	}
	
	/*
	 * getStudentsByFirstName()- use strategy design pattern to sort by first name
	 * Returns: ArrayList of students, sorted by first name
	 */
	public ArrayList<Student> getStudentsByFirstName() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.fisrtNameFirstComparator());
		return listCopy;
	}
	
	/*
	 * getStudentsByFirstName()- use strategy design pattern to sort by last name
	 * Returns: ArrayList of students, sorted by last name
	 */
	public ArrayList<Student> getStudentsByLastName() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.lastNameFirstComparator());
		return listCopy;
	}
	
	/*
	 * getStudentsByFirstName()- use strategy design pattern to sort by username
	 * Returns: ArrayList of students, sorted by username
	 */
	public ArrayList<Student> getStudentsByUsername() {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.userNameFirstComparator());
		return listCopy;
	}
	
	
	/*
	 * getStudentsByFirstName(Course, Sting)- use strategy design pattern to sort by assignment grade
	 * Returns: ArrayList of students, sorted by assignment grade
	 */
	public ArrayList<Student> getStudentsByAssgGrade(String course, String assg) {
		ArrayList<Student> listCopy = new ArrayList<Student>(students);
		Collections.sort(listCopy, Student.assgFirstComparator(course, assg));
		return listCopy;
	}
	
	
	public void setStudents(ArrayList<Student> sortedList) {
		this.students = sortedList;
	}

	
	/*
	 * makeXGroups(int)- returns students split into x amount of groups 
	 * Return: returns a string with each group
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
	 * makeGroupsOfXStudents(int) splits students into groups where each group will
	 * have about x members
	 * Return: returns a string with each group
	 */
	public String makeGroupsOfXStudents(int x) {
		int n = students.size() / x;
		if (n == 0) n = 1;
		return makeXGroups(n);
	}
	
	/*
	 * getCourseAverage(Course) - calculates average for a course
	 * Return: double of average
	 */
	public double getCourseAverage(Course c) {
		int count = 0;
		double total = 0; 
		for (Student s : students) {
			count += 1;
			total += s.getGrade(c);
		}
		return total / count;
	}

	/*
	 * getAssgAverage(Assignment, Course) - calculates the average of the students' grades on an assignment
	 * Returns: double of average
	 */
	public double getAssgAverage(Assignment a, Course c){
		double total = 0;
		int count = 0;
	
		for(Student s : c.getStudents().getStudents()){
			if(s.getGraded(a, c.getName())){
				count += 1;
				total += s.getAssgGrade(a, c.getName());
			}
		}
		return count > 0 ? total/count : 0.0;
	}

	/*
	 * getAssgMedian(Assignment, Course) - returns the median of the students' grades on an assignment
	 * Returns: double of median
	 */
	public double getAssgMedian(Assignment a, Course c){
		ArrayList<Double> grades = new ArrayList<Double>();

		for(Student s : c.getStudents().getStudents()){
			if(s.getGraded(a, c.getName())){
				grades.add(s.getAssgGrade(a, c.getName()));
			}
		}

		if(grades.isEmpty()){
			return -1.0;
		}

		Collections.sort(grades);
		int num = grades.size();
		int mid = num/2;

		if(num % 2 == 1){
			return grades.get(mid);
		}
		else if(num % 2 == 0){
			return (grades.get(mid -1) + grades.get(mid)) / 2.0;
		}

		return -1.0;
	
		
	}


	/*
	 * shuffleStudents() shuffles students
	 * Return: shuffled ArrayList of students
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