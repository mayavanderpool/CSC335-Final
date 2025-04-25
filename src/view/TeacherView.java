package view;

import controller.TeacherController;
import model.Course;
import model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.Observer;

public class TeacherView implements Observer {
    private TeacherController controller;
    private Teacher teacher;
    private JFrame frame;

    public TeacherView(TeacherController controller, Teacher teacher) {
        this.controller = controller;
        this.teacher = teacher;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Teacher Dashboard - " + teacher.getFirstName() + " " + teacher.getLastName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + teacher.getFirstName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton viewCurrentCoursesButton = new JButton("View Current Courses");
        JButton viewCompletedCoursesButton = new JButton("View Completed Courses");
        JButton addCourseButton = new JButton("Add New Course");
        JButton logoutButton = new JButton("Logout");

        viewCurrentCoursesButton.addActionListener(e -> showCourseSelection(false));
        viewCompletedCoursesButton.addActionListener(e -> showCourseSelection(true));
        addCourseButton.addActionListener(e -> promptAddCourse());
        logoutButton.addActionListener(e -> controller.logout());

        buttonPanel.add(viewCurrentCoursesButton);
        buttonPanel.add(viewCompletedCoursesButton);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    private void promptAddCourse() {
        String courseName = JOptionPane.showInputDialog(frame, "Enter the name of the new course:");
        if (courseName != null && !courseName.trim().isEmpty()) {
            controller.addCourse(courseName);
        }
    }

    private void showCourseSelection(boolean completed) {
        ArrayList<Course> courses = completed ? controller.getCompletedCourses() : controller.getCurrentCourses();
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No courses to display.");
            return;
        }

        String[] options = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            options[i] = courses.get(i).getName();
        }

        String selected = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                completed ? "Completed Courses" : "Current Courses",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selected != null) {
            for (Course course : courses) {
                if (course.getName().equals(selected)) {
                    try {
                        // Pass the "completed" flag to CourseView to indicate read-only mode
                        CourseView courseView = new CourseView(controller, teacher, course, completed);
                        controller.getAccountManager().addObserver(courseView); 
                        courseView.display();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Failed to open course: " + ex.getMessage());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void update() {
        showCourseSelection(false);
    }
}