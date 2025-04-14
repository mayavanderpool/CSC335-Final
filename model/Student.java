package model;

import java.util.ArrayList;
import java.util.Comparator;
/*File: Student.java
Author: Rees Hart
Purpose: This class represents a student object
*/
import java.util.HashMap;


public class Student extends Person{

    private HashMap<Course, HashMap<Assignment, Double>> courseList;

    public Student(String first, String last){
        super(first,last);
        courseList = new HashMap<Course, HashMap<Assignment, Double>>();
    }


    public double getGPA(){
    	if(this.getCompletedCourses().size()== 0) {
    		return 0.0;
    	} else {
        double sum = 0;
        for (Course c: this.getCompletedCourses()){
            if (c.isCompleted()){
                sum += getGrade(c) * 4.0;
            }
        }
        return (sum / this.getCompletedCourses().size());
    	}
    }

    public void addCourse(Course c){
        courseList.put(c, new HashMap<Assignment, Double>());
    }

    public ArrayList<Course> getCurrentCourses(){
        ArrayList<Course> current = new ArrayList<Course>();
        for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
        	if (!entry.getKey().isCompleted()) current.add(entry.getKey());
        }
        return current;
    }

    public ArrayList<Course> getCompletedCourses(){
        ArrayList<Course> complete = new ArrayList<Course>();
        for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
        	if (entry.getKey().isCompleted()) complete.add(entry.getKey());
        }
        return complete;
    }

    public ArrayList<Assignment> getAssignments(){
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
        	HashMap<Assignment, Double> assignentry = entry.getValue();
        	for(HashMap.Entry<Assignment, Double> entry2 : assignentry.entrySet()) {
        		assignments.add(entry2.getKey());
        	}
        }
        return assignments;
    }

    public ArrayList<Assignment> getGraded(){
        ArrayList<Assignment> graded = new ArrayList<Assignment>();

        for(HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()){
        	HashMap<Assignment, Double> assgnentry = entry.getValue();
        	for(HashMap.Entry<Assignment, Double> assg : assgnentry.entrySet()) {
        		if(assg.getValue() > 0.0) {
        			graded.add(assg.getKey());
        		}
        	}
        }
        return graded;
    }
    
    public ArrayList<Assignment> getUngraded(){
        ArrayList<Assignment> ungraded = new ArrayList<Assignment>();

        for(HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()){
        	HashMap<Assignment, Double> assgnentry = entry.getValue();
        	for(HashMap.Entry<Assignment, Double> assg : assgnentry.entrySet()) {
        		if(assg.getValue().equals(0.0)) {
        			ungraded.add(assg.getKey());
        		}
        	}
        }
        return ungraded;
    }

    public static Comparator<Student> fisrtNameFirstComparator(){
        return new Comparator<Student>(){
            public int compare(Student s1, Student s2){
                int comp = s1.getFirstName().compareTo(s2.getFirstName());
                if(comp == 0){
                    comp = s1.getLastName().compareTo(s2.getLastName());
                }
                return comp;
            }
        };
    }

    public static Comparator<Student> lastNameFirstComparator(){
        return new Comparator<Student>(){
            public int compare(Student s1, Student s2){
                int comp = s1.getLastName().compareTo(s2.getLastName());
                if(comp == 0){
                    comp = s1.getFirstName().compareTo(s2.getFirstName());
                }
                return comp;
            }
        };
    }
    
    public void addAssignment(Course c, Assignment a) {
    	if(courseList.containsKey(c)) {
    		courseList.get(c).put(a, 0.0);
    	}
    }

    public static Comparator<Student> userNameFirstComparator(){
        return new Comparator<Student>(){
            public int compare(Student s1, Student s2){
                int comp = s1.getUserName().compareTo(s2.getUserName());
                
                return comp;
            }
        };
    }
    
    public static Comparator<Student> gradeFirstComparator(Course c){
        return new Comparator<Student>(){
            public int compare(Student s1, Student s2){
                int comp = Double.compare(s1.getGrade(c), s2.getGrade(c));
                return comp;
            }
        };
    }


    public void setAssignmentGrade(String course, Assignment a, double grade){
    	
        for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
        	if (entry.getKey().getName().equals(course)) {
            	HashMap<Assignment, Double> assignentry = entry.getValue();
            	for (Assignment key : assignentry.keySet()) {
            		if(key.getName().equals(a.getName())) {
            			assignentry.put(key, grade);
            		}
            	}
        	}
        }
    }

    public double getGrade(Course course){
        double grade = 0.0;
        double total = 0;
        for (HashMap.Entry<Course, HashMap<Assignment, Double>> entry : this.courseList.entrySet()) {
        	if (entry.getKey().equals(course)) {
            	HashMap<Assignment, Double> assignentry = entry.getValue();
            	for(HashMap.Entry<Assignment, Double> entry2 : assignentry.entrySet()) {
            		total+= entry2.getKey().getTotalPoints();
            		grade += entry2.getValue();
            		}
            	}
        	}
        return grade/total;
        }
    
	public String getLetterGrade(Course c){
		Double grade = getGrade(c)*100;

		if(grade >= 90.0){
			return "A";
		}
		else if(grade >= 80.0 && grade < 90){
			return "B";
		}
		else if(grade >= 70.0 && grade < 80.0){
			return "C";
		}
		else if(grade >= 60.0 && grade < 70.0){
			return "D";
		}
		else{
			return "F";
		}
	}

	/* courseGradeNeeded(Double) - What is the minimum grade needed in this course to get target GPA.
	 * Returns: Double
	*/
	public Double courseGradeNeeded(Double target, Course c){
		ArrayList<Course> current = this.getCurrentCourses();
		ArrayList<Course> completed = this.getCompletedCourses();
		Double sum = 0.0;

		for(Course course: current){
			if(course != c){
				sum += getGrade(course);
			}
		}
		for(Course course: completed){
			sum += getGrade(course);
		}
		
		int courses = current.size() + completed.size();
		Double avg = (target) * courses;
		Double result = avg - sum;


		return result;

	}
	
	/* gradeNeeded(Double) - What additional grade assignment grade is needed to get desired grade in course
	 * Returns: Double
	*/
	public Double gradeNeeded(Double target, Course c){
		Double curr = getGrade(c);

		if(curr >= target){
			return 0.0;
		}
		else{
			Double temp = target - curr;
			Double result = (temp * c.getAssignments().size()) + target;
			return result;
		}
	}
}