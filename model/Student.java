package model;
import java.util.ArrayList;
/*File: Student.java
Author: Rees Hart
Purpose: This class represents a student object
*/

// NEED TO ADD COMPERATOR
public class Student extends Person{
    private ArrayList<Course> courseList;
    private double gpa;

    public Student(String first, String last){
        super(first,last);
        this.courseList = new ArrayList<Course>();
        this.gpa = 0.0;
    }

    public void setGPA(double gpa){
        this.gpa = gpa;
    }

    public double getGPA(){
        return this.gpa;
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
            if(a.graded()){
                graded.add(a);
            }
        }
        return graded;
    }
}
