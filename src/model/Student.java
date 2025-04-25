package model;

import java.util.ArrayList;
import java.util.Comparator;
/*File: Student.java
Author: Rees Hart
Purpose: This class represents a student object
*/
import java.util.HashMap;

public class Student extends Person {

	/*INSTANCE VARIABLES*/
	private HashMap<Course, HashMap<Assignment, Double>> courseList;

	/*CONSTRUCTOR */
	public Student(String first, String last, String user) {
		super(first, last, user, "student");
		courseList = new HashMap<Course, HashMap<Assignment, Double>>();
	}
	
	/*COPY CONSTRUCTOR */
	public Student(Student s){
		super(s.getFirstName(), s.getLastName(), s.getUserName(), s.getFirstName());
		this.courseList = s.courseList;
	}

	/*SETTERS AND GETTERS */
	
	/*
	 * getGPA() - calculates gpa for student based on completed courses
	 * Returns: double representing students GPA
	 */
	public double getGPA() {
		if (this.getCompletedCourses().size() == 0) {
			return 0.0;
		} else {
			double sum = 0;
			for (Course c : this.getCompletedCourses()) {
				if (c.isCompleted()) {
					sum += getGrade(c) / 25;
				}
			}
			return (sum / this.getCompletedCourses().size());
		}
	}

	/*
	 * addCourse(Course) - adds course for student to course list
	 * Returns - none
	 */
	public void addCourse(Course c) {
		courseList.put(c, new HashMap<Assignment, Double>());
	}

	/*
	 * getCurrentCourses() - gets courses that are not completed
	 * Returns - ArrayList of Courses that student is currently in
	 */
	public ArrayList<Course> getCurrentCourses() {
		ArrayList<Course> current = new ArrayList<Course>();
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (!entry.getKey().isCompleted())
				current.add(entry.getKey());
		}
		return current;
	}

	/*
	 * getCompletedCourses() - gets courses that are marked as completed
	 * Returns - ArrayList of Courses that student has completed
	 */
	public ArrayList<Course> getCompletedCourses() {
		ArrayList<Course> complete = new ArrayList<Course>();
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (entry.getKey().isCompleted())
				complete.add(entry.getKey());
		}
		return complete;
	}
	
	/*
	 * getAllCourses() - gets all courses for student
	 * Return: ArrayList of Courses
	 */
	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> all = new ArrayList<Course>();
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			all.add(entry.getKey());
		}
		return all;
	}

	
	/*
	 * getAssignments() - gets all assignments for all courses
	 * Returns - ArrayList of students Assignments 
	 */
	public ArrayList<Assignment> getAssignments() {
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			HashMap<Assignment, Double> assignentry = entry.getValue();
			for (HashMap.Entry<Assignment, Double> entry2 : assignentry.entrySet()) {
				assignments.add(entry2.getKey());
			}
		}
		return assignments;
	}

	/*
	 * getGraded(Assignment, String) - returns if Assignment from course is graded
	 * Returns - boolean representing if assignment has been graded
	 */
	public boolean getGraded(Assignment a, String c) {
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (entry.getKey().getName().equals(c)) {
				HashMap<Assignment, Double> assgnentry = entry.getValue();
				for (Assignment key : assgnentry.keySet()) {
					if (key.getName().equals(a.getName()) && assgnentry.get(key) > 0.0) {
						return true;
					}
				}
			}

		}
		return false;
	}

	
	/*
	 * getUngraded() - gets all assignments that are not graded
	 * Returns - ArrayList of Assignments that have not been graded
	 */
	public ArrayList<Assignment> getUngraded() {
		ArrayList<Assignment> ungraded = new ArrayList<Assignment>();

		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			HashMap<Assignment, Double> assgnentry = entry.getValue();
			for (HashMap.Entry<Assignment, Double> assg : assgnentry.entrySet()) {
				if (assg.getValue().equals(0.0)) {
					ungraded.add(assg.getKey());
				}
			}
		}
		return ungraded;
	}

	/*
	 * firstNameComparator() - compares student based on first name first
	 * Returns: Comparator<Student>
	 */
	public static Comparator<Student> fisrtNameFirstComparator() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int comp = s1.getFirstName().compareTo(s2.getFirstName());
				if (comp == 0) {
					comp = s1.getLastName().compareTo(s2.getLastName());
				}
				return comp;
			}
		};
	}

	/*
	 * lastNameComparator() - compares student based on first name first
	 * Returns: Comparator<Student>
	 */
	public static Comparator<Student> lastNameFirstComparator() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int comp = s1.getLastName().compareTo(s2.getLastName());
				if (comp == 0) {
					comp = s1.getFirstName().compareTo(s2.getFirstName());
				}
				return comp;
			}
		};
	}

	/*
	 * addAssignment(Course, Assignment) - adds assignment to assignmentlist
	 * Returns - none
	 */
	public void addAssignment(Course c, Assignment a) {
		if (courseList.containsKey(c)) {
			courseList.get(c).put(a, 0.0);
		}
	}

	/*
	 * removeAssignment(Course, Assignment) - removes assignment from
	 * assignmentlist of course
	 * Returns - none
	 */
	public void removeAssignment(Course c, Assignment a) {
		if (courseList.containsKey(c)) {
			HashMap<Assignment, Double> assignments = courseList.get(c);
			assignments.remove(a);
		}
	}
	

	/*
	 * userNameFirstComparator() - compares student objects with username
	 * Returns: Comparator<Student>
	 */
	public static Comparator<Student> userNameFirstComparator() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int comp = s1.getUserName().compareTo(s2.getUserName());

				return comp;
			}
		};
	}

	/*
	 * gradeFirstComparator() - compares student objects with username
	 * Returns: Comparator<Student>
	 */
	public static Comparator<Student> gradeFirstComparator(Course c) {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int comp = Double.compare(s1.getGrade(c), s2.getGrade(c));
				return comp;
			}
		};
	}

	/*
	 * assgFirstComparator() - compares student objects with assignment grade
	 * Returns: Comparator<Student>
	 */
	public static Comparator<Student> assgFirstComparator(String course, String a) {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int comp = Double.compare(s2.getAssgGrade(a, course), s1.getAssgGrade(a, course));
				return comp;
			}
		};
	}

	/*
	 * setAssignmentGrade(String, Assignment, double) - sets assignment grade
	 * Returns: None
	 */
	public void setAssignmentGrade(String course, Assignment a, double grade) {
		boolean set = false;
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (entry.getKey().getName().equals(course)) {
				HashMap<Assignment, Double> assignentry = entry.getValue();
				if (assignentry.containsKey(a)) {
					assignentry.put(a, grade);
					set = true;
					break;
				}
				for (Assignment key : assignentry.keySet()) {
					if (key.getName().equals(a.getName())) {
						assignentry.put(key, grade);
						set = true;
						break;
					}
				}
				if (set) {
					break;
				}
			}

		}
	}

	/*
	 * getGrade() - gets overall grade for a course
	 */
	public double getGrade(Course course) {
		double grade = 0.0;
		double total = 0;
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (entry.getKey().equals(course)) {
				HashMap<Assignment, Double> assignentry = entry.getValue();
				for (HashMap.Entry<Assignment, Double> entry2 : assignentry.entrySet()) {
					total += entry2.getKey().getTotalPoints();
					grade += entry2.getValue();

				}
			}
		}
		if (total == 0)
			return 0;
		return grade / total * 100;
	}

	/*
	 * getAssgGrade(Assignment a, String course) - gets assignment grade of Assignment from course
	 */
	public double getAssgGrade(Assignment a, String course) {
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (entry.getKey().getName().equals(course)) {
				HashMap<Assignment, Double> assignentry = entry.getValue();
				for (Assignment key : assignentry.keySet()) {
					if (key.getName().equals(a.getName())) {
						return assignentry.get(key);
					}
				}
			}
		}
		return -1;
	}

	// overloaded getAssgGrade()- used String, String instead
	public double getAssgGrade(String a, String course) {
		for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
			if (entry.getKey().getName().equals(course)) {
				HashMap<Assignment, Double> assignentry = entry.getValue();
				for (Assignment key : assignentry.keySet()) {
					if (key.getName().equals(a)) {
						return assignentry.get(key);
					}
				}
			}
		}
		return -1;
	}

	/*
	 * getLetterGrade(Course c) - returns letter grade of course based on percent grade
	 */
	public String getLetterGrade(Course c) {
		Double grade = getGrade(c);

		if (grade >= 90.0) {
			return "A";
		} else if (grade >= 80.0 && grade < 90) {
			return "B";
		} else if (grade >= 70.0 && grade < 80.0) {
			return "C";
		} else if (grade >= 60.0 && grade < 70.0) {
			return "D";
		} else {
			return "F";
		}
	}

	/*
	 * courseGradeNeeded(Double) - What is the minimum grade needed in this course
	 * to get target GPA.
	 * Returns: Double
	 */
	public Double courseGradeNeeded(Double target, Course c) {
		ArrayList<Course> current = this.getCurrentCourses();
		ArrayList<Course> completed = this.getCompletedCourses();
		Double sum = 0.0;

		for (Course course : current) {
			if (course != c) {
				sum += getGrade(course);
			}
		}
		for (Course course : completed) {
			sum += getGrade(course);
		}

		int courses = current.size() + completed.size();
		Double avg = (target) * courses;
		Double result = avg - sum;

		return result;

	}

	/*
	 * gradeNeeded(Double) - What additional grade assignment grade is needed to get
	 * desired grade in course
	 * Returns: Double
	 */
	public Double gradeNeeded(Double target, Course c, double nextAssgPoints) {
		target = target / 100; // target is a percent
		double earned = 0.0;
		double total = 0.0;

		// get current grade and current total for assignments
		for (Assignment a : getAssignments()) {
			earned += this.courseList.get(c).get(a);
			total += a.getTotalPoints();
		}

		double requiredTotal = target * (total + nextAssgPoints);
		double needed = requiredTotal - earned;

		if (needed < 0)
			return 0.0;
		// can't score more than this. this means you won't reach the target
		if (needed > nextAssgPoints)
			return nextAssgPoints;

		return needed;
	}
}