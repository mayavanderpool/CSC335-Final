package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import controller.StudentController;
import model.Student;
import model.Course;

public class StudentView {
    private StudentController controller;
    private Student student;
    private JFrame frame;
    
    public StudentView(StudentController controller, Student student) {
        this.controller = controller;
        this.student = student;
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Student Dashboard - " + student.getFirstName() + " " + student.getLastName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + student.getFirstName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create buttons for student functions
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton viewCurrentCoursesButton = new JButton("View Current Courses");
        JButton viewCompletedCoursesButton = new JButton("View Completed Courses");
        JButton viewAssignmentsButton = new JButton("View Assignments");
        JButton calculateClassAverageButton = new JButton("Calculate Class Average");
        JButton calculateGPAButton = new JButton("Calculate GPA");
        JButton logoutButton = new JButton("Logout");

        // Add action listeners
        viewCurrentCoursesButton.addActionListener(e -> {
            ArrayList<Course> courses = controller.getCurrentCourses();
            showCoursesDialog(courses, "Current Courses");
        });
        
        viewCompletedCoursesButton.addActionListener(e -> {
            ArrayList<Course> courses = controller.getCompletedCourses();
            showCoursesDialog(courses, "Completed Courses");
        });

        viewAssignmentsButton.addActionListener(e -> {
            showCourseSelectionForAssignments();
        });

        calculateClassAverageButton.addActionListener(e -> {
            showCourseSelectionForAverage();
        });

        calculateGPAButton.addActionListener(e -> {
            double gpa = controller.getGPA();
            JOptionPane.showMessageDialog(frame, "Your current GPA is: " + String.format("%.2f", gpa));
        });

        logoutButton.addActionListener(e -> controller.logout());

        // Add buttons to panel
        buttonPanel.add(viewCurrentCoursesButton);
        buttonPanel.add(viewCompletedCoursesButton);
        buttonPanel.add(viewAssignmentsButton);
        buttonPanel.add(calculateClassAverageButton);
        buttonPanel.add(calculateGPAButton);
        buttonPanel.add(logoutButton);

        // Add components to main panel
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
    }
    
    private void showCoursesDialog(ArrayList<Course> courses, String title) {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No courses to display.");
            return;
        }
        
        JDialog dialog = new JDialog(frame, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        
        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.getName()).append("\n");
        }
        
        textArea.setText(sb.toString());
        
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showCourseSelectionForAssignments() {
        ArrayList<Course> courses = controller.getCurrentCourses();
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "You are not enrolled in any courses.");
            return;
        }

        String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseNames[i] = courses.get(i).getName();
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                "Course Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseNames,
                courseNames[0]);

        if (selectedCourse != null) {
            // Find the selected course object
            Course course = null;
            for (Course c : courses) {
                if (c.getName().equals(selectedCourse)) {
                    course = c;
                    break;
                }
            }

            if (course != null) {
                // Here you would show the assignments dialog
                // This is simplified to show you the pattern
                JOptionPane.showMessageDialog(frame, "Assignments for " + course.getName());
            }
        }
    }
    
    private void showCourseSelectionForAverage() {
        ArrayList<Course> courses = controller.getCurrentCourses();
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "You are not enrolled in any courses.");
            return;
        }

        String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseNames[i] = courses.get(i).getName();
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                "Course Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseNames,
                courseNames[0]);

        if (selectedCourse != null) {
            // Find the selected course object
            Course course = null;
            for (Course c : courses) {
                if (c.getName().equals(selectedCourse)) {
                    course = c;
                    break;
                }
            }

            if (course != null) {
                double grade = controller.getGradeForCourse(course); 
                String letterGrade = controller.getLetterGradeForCourse(course);
                JOptionPane.showMessageDialog(frame,
                        "Your current grade in " + course.getName() + " is: " +
                                String.format("%.2f%%", grade) + " (" + letterGrade + ")");
            }
        }
    }

    public void display() {
        frame.setVisible(true);
    }
    
    public void close() {
        frame.dispose();
    }
}