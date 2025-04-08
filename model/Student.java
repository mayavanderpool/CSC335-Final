package model;

import java.util.ArrayList;
import java.util.Comparator;
/*File: Student.java
Author: Rees Hart
Purpose: This class represents a student object
*/

public class Student extends Person{
    private ArrayList<Course> courseList;
    private double gpa;

    public Student(String first, String last){
        super(first,last);
        this.courseList = new ArrayList<Course>();
        this.gpa = 0.0;
    }


    public double getGPA(){
    	if(this.getCompletedCourses().size()== 0) {
    		return 0.0;
    	} else {
        double sum = 0;
        for (Course c: this.getCompletedCourses()){
            if (c.isCompleted()){
                sum += c.getOverallGrade();
            }
        }
        double x = getCompletedCourses().size() * 100;
        return (sum / x) * 4;
    	}
    }

    public void addCourse(Course c){
        courseList.add(c);
    }

    public ArrayList<Course> getCurrentCourses(){
        ArrayList<Course> current = new ArrayList<Course>();
        for(Course c : courseList){
            if(!c.isCompleted()){
                current.add(c);
            }
        }
        return current;
    }

    public ArrayList<Course> getCompletedCourses(){
        ArrayList<Course> complete = new ArrayList<Course>();
        for(Course c : courseList){
            if(c.isCompleted()){
                complete.add(c);
            }
        }
        return complete;
    }

    public ArrayList<Assignment> getAssignments(){
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        for(Course c : courseList){
            for(Assignment a : c.getAssignments()){
                assignments.add(a);
            }
        }
        return assignments;
    }

    public ArrayList<Assignment> getGraded(){
        ArrayList<Assignment> assignments = this.getAssignments();
        ArrayList<Assignment> graded = new ArrayList<Assignment>();

        for(Assignment a : assignments){
            if(a.isGraded()){
                graded.add(a);
            }
        }
        return graded;
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
        for(Course c: courseList){
            if(c.getName().compareTo(course) == 0){
                for(Assignment assg: c.getAssignments()){
                    if(assg.getName().compareTo(a.getName()) == 0){
                        assg.setStudentGrade(grade);
                    }
                }
            }
        }
    }

    public double getGrade(Course course){
        double grade = 0.0;
        for(Course c : courseList){
            if(c.equals(course)){
                grade = course.getOverallGrade();
            }
        }
        return grade;
    }

	/* courseGradeNeeded(Double) - What is the minimum grade needed in this course to get target GPA.
	 * Returns: Double
	*/
    /*
	public Double courseGradeNeeded(Double target, Course c){
		ArrayList<Course> current = this.getCurrentCourses();
		ArrayList<Course> completed = this.getCompletedCourses();
		Double sum = 0.0;

		for(Course course: current){
			if(course != c){
				sum += course.getOverallGrade();
			}
		}
		for(Course course: completed){
			sum += course.getOverallGrade();
		}
		int courses = current.size() + completed.size();
		Double avg = sum/courses;
		Double temp = (target * 25) - avg;

		Double result = (temp * (courses + 1)) + avg;


		return result;

	}
	*/
}