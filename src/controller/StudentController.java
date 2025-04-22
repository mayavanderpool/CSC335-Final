package controller;

import java.util.ArrayList;
import model.Course;
import model.Student;
import view.StudentView;

public class StudentController {
    private Student student;
    private StudentView studentView;
    
    public StudentController(Student student) {
        this.student = student;
        this.studentView = new StudentView(this, student);
    }
    
    public void start() {
        studentView.display();
    }
    
    public void logout() {
        studentView.close();
        MainController mainController = new MainController(null); // You'll need to adjust this
        mainController.start();
    }
    
    public ArrayList<Course> getCurrentCourses() {
        return student.getCurrentCourses();
    }
    
    public ArrayList<Course> getCompletedCourses() {
        return student.getCompletedCourses();
    }
    
    public double getGPA() {
        return student.getGPA();
    }
    
    public double getGradeForCourse(Course course) {
        return student.getGrade(course);
    }
    
    public String getLetterGradeForCourse(Course course) {
        return student.getLetterGrade(course);
    }
    
    // Add additional methods to interact with student data as needed
}