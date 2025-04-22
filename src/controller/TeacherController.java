package controller;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.*;
import view.CourseView;
import view.TeacherView;

public class TeacherController {
	private Teacher teacher;
	private TeacherView teacherView;
	private AccountManager aManager;

	public TeacherController(Teacher teacher, AccountManager aManager) {
		this.teacher = teacher;
		this.aManager = aManager;
		this.teacherView = new TeacherView(this, teacher);
	}

	public void start() {
		teacherView.display();
	}

	public void logout() {
		teacherView.close();
		MainController mainController = new MainController(aManager); // assumes MainController accepts this
		mainController.start();
	}

	public ArrayList<Course> getCurrentCourses() {
		return teacher.getCompletedOrCurrentCourses(false);
	}

	public ArrayList<Course> getCompletedCourses() {
		return teacher.getCompletedOrCurrentCourses(true);
	}

	public void addCourse(String courseName) {
		Course newCourse = new Course(courseName);
		teacher.addCourse(newCourse);
	}

	public void markCourseAsCompleted(Course course) {
		course.setCompleted();
	}

	public void addStudentToCourse(Course course, String fName, String lName) {
		Student student = aManager.getStudentByName(fName, lName);
		if (student != null) {
			teacher.addStudent(course, student);
		}else{
			JOptionPane.showMessageDialog(null, 
            "Student " + fName + " " + lName + " does not exist in the database.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
		}
		

	}

	public void importStudents(File file, Course course) {
		if (file != null) {
			aManager.importStudents(file.getAbsolutePath(), course);
		}
	}

	public void removeStudentFromCourse(Course course, String fName, String lName) {
		Student student = aManager.getStudentByName(fName, lName);
		if (student != null) {
			teacher.removeStudent(course, student);
		}
	}

	public void addAssignment(Course course, String name, double points) {
		Assignment newAssignment = new Assignment(name, points);
		course.addAssg(newAssignment);
	}

	public void removeAssignment(Course course, Assignment assignment) {
		course.removeAssg(assignment);
	}

	public void addAssignmentGrade(String fName, String lName, String courseName, Assignment assignment, double grade) {
		Student student = aManager.getStudentByName(fName, lName);
		if (student != null) {
			teacher.addAssignmentGrade(student, courseName, assignment, grade);
		}

	}

	public String generateGroups(Course course, int number, boolean byCount) {
		if (byCount) {
			return teacher.makeXGroups(course.getName(), number);
		} else {
			return teacher.makeGroupsOfXStudents(course.getName(), number);
		}
	}

	public double getAssignmentClassAverage(Course course, Assignment assignment) {
		return teacher.getAssgClassAverage(course, assignment);
	}

	public String getAssignmentMedian(Course course, Assignment assignment) {
		return teacher.getAssgMedian(course, assignment);
	}

	public String getCompletedData(Course course, Assignment assignment) {
		return teacher.getCompletedData(course, assignment);
	}

	public String makeXGroups(String course, int number) {
		return teacher.makeXGroups(course, number);
	}

	public String makeGroupsOfXStudents(String course, int number) {
		return teacher.makeGroupsOfXStudents(course, number);
	}

	public void sortByFirst(String courseName) {
		Course c = teacher.getCourse(courseName);
		if (c != null) {
			StudentList sList = c.getStudents();
			ArrayList<Student> sorted = teacher.getStudentByFirstName(courseName);
			sList.setStudents(sorted); // this must exist in StudentList
		}
	}
	
	public void sortByLast(String courseName) {
		Course c = teacher.getCourse(courseName);
		if (c != null) {
			StudentList sList = c.getStudents();
			ArrayList<Student> sorted = teacher.getStudentByLastName(courseName);
			sList.setStudents(sorted);
		}
	}
	
	public void sortByUser(String courseName) {
		Course c = teacher.getCourse(courseName);
		if (c != null) {
			StudentList sList = c.getStudents();
			ArrayList<Student> sorted = teacher.getStudentByUsername(courseName);
			sList.setStudents(sorted);
		}
	}
	


}
