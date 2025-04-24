package controller;

import java.util.ArrayList;

import model.AccountManager;
import model.Course;
import model.Student;
import view.StudentView;

public class StudentController {
    private Student student;
    private StudentView studentView;
	private AccountManager accountManager;
    
    public StudentController(Student student, AccountManager accountManager) {
    this.student = student;
    this.accountManager = accountManager;
    this.studentView = new StudentView(this, student);
    accountManager.addObserver(studentView);
}
    
    public void start() {
        studentView.display();
    }
    
    public void logout() {
		studentView.close();
		MainController mainController = new MainController(accountManager);
		mainController.start();
	}
    
    public ArrayList<Course> getCurrentCourses() {
        return student.getCurrentCourses();
    }
    
    public ArrayList<Course> getCompletedCourses() {
        return student.getCompletedCourses();
    }

	public ArrayList<Course> getAllCourses() {
        return student.getAllCourses();
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

	public Double courseGradeNeeded(Double target, Course c) {
		return student.courseGradeNeeded(target, c);
	}

	public Double gradeNeeded(Double target, Course c, double nextAssgPoints) {
		return student.gradeNeeded(target, c, nextAssgPoints);
	}

	
    // Add additional methods to interact with student data as needed
}