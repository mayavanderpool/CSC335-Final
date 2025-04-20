package model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class View {
    private static AccountManager aManager;

    public static void main(String[] args) {
        aManager = new AccountManager();
        createRolePrompt();
    }

    public static void createRolePrompt() {
        SwingUtilities.invokeLater(() -> {
            JFrame roleFrame = new JFrame("Select Role");
            roleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            roleFrame.setSize(300, 150);
            roleFrame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel prompt = new JLabel("Are you a student or teacher?", SwingConstants.CENTER);
            JButton studentButton = new JButton("Student");
            JButton teacherButton = new JButton("Teacher");

            panel.add(prompt);
            panel.add(studentButton);
            panel.add(teacherButton);

            roleFrame.add(panel);
            roleFrame.setVisible(true);

            studentButton.addActionListener(e -> {
                roleFrame.dispose();
                createLoginOrSignupPrompt(true);
            });

            teacherButton.addActionListener(e -> {
                roleFrame.dispose();
                createLoginOrSignupPrompt(false);
            });
        });
    }

    private static void createLoginOrSignupPrompt(boolean isStudent) {
        JFrame frame = new JFrame(isStudent ? "Student Access" : "Teacher Access");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel prompt = new JLabel("What would you like to do?", SwingConstants.CENTER);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Create Account");

        panel.add(prompt);
        panel.add(loginButton);
        panel.add(signupButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            frame.dispose();
            if (isStudent) {
                createStudentLoginPrompt();
            } else {
                createTeacherLoginPrompt();
            }
        });

        signupButton.addActionListener(e -> {
            frame.dispose();
            if (isStudent) {
                createStudentSignupPrompt();
            } else {
                createTeacherSignupPrompt();
            }
        });
    }

    private static void createStudentLoginPrompt() {
        JFrame frame = new JFrame("Student Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel usernamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);

        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(new JLabel());
        panel.add(buttonPanel);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (aManager.checkCredentials(username, password)) {
                // Find the student object
                Student student = null;
                for (Person p : aManager.getUsers()) {
                    if (p instanceof Student && p.getUserName().equals(username)) {
                        student = (Student) p;
                        break;
                    }
                }

                if (student != null) {
                    frame.dispose();
                    showStudentHomePage(student);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            createLoginOrSignupPrompt(true);
        });
    }

    private static void createTeacherLoginPrompt() {
        JFrame frame = new JFrame("Teacher Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel usernamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);

        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(new JLabel());
        panel.add(buttonPanel);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (aManager.checkCredentials(username, password)) {
                // Find the teacher object
                Teacher teacher = null;
                for (Person p : aManager.getUsers()) {
                    if (p instanceof Teacher && p.getUserName().equals(username)) {
                        teacher = (Teacher) p;
                        break;
                    }
                }

                if (teacher != null) {
                    frame.dispose();
                    showTeacherHomePage(teacher);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            createLoginOrSignupPrompt(false);
        });
    }

    private static void createStudentSignupPrompt() {
        JFrame frame = new JFrame("Student Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel fnamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel fnameLabel = new JLabel("First Name:");
        JTextField fnameField = new JTextField();
        fnamePanel.add(fnameLabel, BorderLayout.WEST);
        fnamePanel.add(fnameField, BorderLayout.CENTER);

        JPanel lnamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel lnameLabel = new JLabel("Last Name:");
        JTextField lnameField = new JTextField();
        lnamePanel.add(lnameLabel, BorderLayout.WEST);
        lnamePanel.add(lnameField, BorderLayout.CENTER);

        JPanel usernamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton signupButton = new JButton("Create Account");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(signupButton);

        panel.add(fnamePanel);
        panel.add(lnamePanel);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(new JLabel());
        panel.add(buttonPanel);

        frame.add(panel);
        frame.setVisible(true);

        signupButton.addActionListener(e -> {
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (fname.isEmpty() || lname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            if (!aManager.personExists(fname, lname)) {
                JOptionPane.showMessageDialog(frame, "Person not found in the system.");
                return;
            }

            if (aManager.accountExists(fname, lname)) {
                JOptionPane.showMessageDialog(frame, "Account already exists for this person.");
                return;
            }

            aManager.addPassword(fname, lname, username, password);
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            createStudentLoginPrompt();
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            createLoginOrSignupPrompt(true);
        });
    }

    private static void createTeacherSignupPrompt() {
        JFrame frame = new JFrame("Teacher Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel fnamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel fnameLabel = new JLabel("First Name:");
        JTextField fnameField = new JTextField();
        fnamePanel.add(fnameLabel, BorderLayout.WEST);
        fnamePanel.add(fnameField, BorderLayout.CENTER);

        JPanel lnamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel lnameLabel = new JLabel("Last Name:");
        JTextField lnameField = new JTextField();
        lnamePanel.add(lnameLabel, BorderLayout.WEST);
        lnamePanel.add(lnameField, BorderLayout.CENTER);

        JPanel usernamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton signupButton = new JButton("Create Account");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(signupButton);

        panel.add(fnamePanel);
        panel.add(lnamePanel);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(new JLabel());
        panel.add(buttonPanel);

        frame.add(panel);
        frame.setVisible(true);

        signupButton.addActionListener(e -> {
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (fname.isEmpty() || lname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            if (!aManager.personExists(fname, lname)) {
                // Create teacher if they don't exist
                Teacher newTeacher = new Teacher(fname, lname);
                aManager.addTeacher(newTeacher);
            }

            if (aManager.accountExists(fname, lname)) {
                JOptionPane.showMessageDialog(frame, "Account already exists for this person.");
                return;
            }

            aManager.addPassword(fname, lname, username, password);
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            createTeacherLoginPrompt();
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            createLoginOrSignupPrompt(false);
        });
    }

    private static void showStudentHomePage(Student student) {
        JFrame frame = new JFrame("Student Dashboard - " + student.getFirstName() + " " + student.getLastName());
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

        // Add action listeners for current and completed courses
        viewCurrentCoursesButton.addActionListener(e -> {
            ArrayList<Course> courses = student.getCurrentCourses();
            showCoursesDialog(frame, courses, "Current Courses");
        });
        
        viewCompletedCoursesButton.addActionListener(e -> {
            ArrayList<Course> courses = student.getCompletedCourses();
            showCoursesDialog(frame, courses, "Completed Courses");
        });

        viewAssignmentsButton.addActionListener(e -> {
            showCourseSelectionForAssignments(frame, student);
        });

        calculateClassAverageButton.addActionListener(e -> {
            showCourseSelectionForAverage(frame, student);
        });

        calculateGPAButton.addActionListener(e -> {
            double gpa = student.getGPA();
            JOptionPane.showMessageDialog(frame, "Your current GPA is: " + String.format("%.2f", gpa));
        });

        logoutButton.addActionListener(e -> {
            frame.dispose();
            createRolePrompt();
        });

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
        frame.setVisible(true);
    }

    private static void showCourseSelectionForAssignments(JFrame parentFrame, Student student) {
        ArrayList<Course> courses = student.getCurrentCourses();
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "You are not enrolled in any courses.");
            return;
        }

        String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseNames[i] = courses.get(i).getName();
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(
                parentFrame,
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
                showAssignmentsDialog(parentFrame, student, course);
            }
        }
    }

    private static void showAssignmentsDialog(JFrame parentFrame, Student student, Course course) {
        JDialog dialog = new JDialog(parentFrame, "Assignments for " + course.getName(), true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create tabbed pane for graded and ungraded assignments
        JTabbedPane tabbedPane = new JTabbedPane();

        // Graded assignments
        ArrayList<Assignment> gradedAssignments = student.getGraded();
        JPanel gradedPanel = new JPanel(new BorderLayout());
        JTextArea gradedText = new JTextArea();
        gradedText.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Assignment assignment : gradedAssignments) {
            sb.append(assignment.getName())
                    .append(" (Points: ").append(assignment.getTotalPoints()).append(")\n");
        }
        gradedText.setText(sb.toString());

        gradedPanel.add(new JScrollPane(gradedText), BorderLayout.CENTER);
        tabbedPane.addTab("Graded Assignments", gradedPanel);

        // Ungraded assignments
        ArrayList<Assignment> ungradedAssignments = student.getUngraded();
        JPanel ungradedPanel = new JPanel(new BorderLayout());
        JTextArea ungradedText = new JTextArea();
        ungradedText.setEditable(false);

        sb = new StringBuilder();
        for (Assignment assignment : ungradedAssignments) {
            sb.append(assignment.getName())
                    .append(" (Points: ").append(assignment.getTotalPoints()).append(")\n");
        }
        ungradedText.setText(sb.toString());

        ungradedPanel.add(new JScrollPane(ungradedText), BorderLayout.CENTER);
        tabbedPane.addTab("Ungraded Assignments", ungradedPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private static void showCourseSelectionForAverage(JFrame parentFrame, Student student) {
        ArrayList<Course> courses = student.getCurrentCourses();
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "You are not enrolled in any courses.");
            return;
        }

        String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseNames[i] = courses.get(i).getName();
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(
                parentFrame,
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
                double grade = student.getGrade(course) * 100; // Convert to percentage
                String letterGrade = student.getLetterGrade(course);
                JOptionPane.showMessageDialog(parentFrame,
                        "Your current grade in " + course.getName() + " is: " +
                                String.format("%.2f%%", grade) + " (" + letterGrade + ")");
            }
        }
    }

    private static void showTeacherHomePage(Teacher teacher) {
        JFrame frame = new JFrame("Teacher Dashboard - " + teacher.getFirstName() + " " + teacher.getLastName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + teacher.getFirstName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create buttons for teacher functions
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton viewCurrentCoursesButton = new JButton("View Current Courses");
        JButton viewCompletedCoursesButton = new JButton("View Completed Courses");
        JButton addCourseButton = new JButton("Add New Course");
        JButton logoutButton = new JButton("Logout");

        // Add action listeners
        viewCurrentCoursesButton.addActionListener(e -> {
            ArrayList<Course> courses = teacher.getCompletedOrCurrentCourses(false);
            showCourseSelectionDialog(frame, courses, "Current Courses", teacher);
        });
        
        viewCompletedCoursesButton.addActionListener(e -> {
            ArrayList<Course> courses = teacher.getCompletedOrCurrentCourses(true);
            showCourseSelectionDialog(frame, courses, "Completed Courses", teacher);
        });

        addCourseButton.addActionListener(e -> {
            String courseName = JOptionPane.showInputDialog(frame, "Enter the name of the new course:");
            if (courseName != null && !courseName.trim().isEmpty()) {
                Course newCourse = new Course(courseName);
                teacher.addCourse(newCourse);
                JOptionPane.showMessageDialog(frame, "Course '" + courseName + "' added successfully!");
            }
        });

        logoutButton.addActionListener(e -> {
            frame.dispose();
            createRolePrompt();
        });

        // Add buttons to panel
        buttonPanel.add(viewCurrentCoursesButton);
        buttonPanel.add(viewCompletedCoursesButton);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(logoutButton);

        // Add components to main panel
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void showCourseSelectionDialog(JFrame parentFrame, ArrayList<Course> courses, String title, Teacher teacher) {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No courses to display.");
            return;
        }
        
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create a list model for courses
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Course c : courses) {
            listModel.addElement(c.getName());
        }
        
        JList<String> courseList = new JList<>(listModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        panel.add(new JScrollPane(courseList), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton selectButton = new JButton("Select Course");
        JButton closeButton = new JButton("Close");
        
        selectButton.addActionListener(e -> {
            int selectedIndex = courseList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Course selectedCourse = courses.get(selectedIndex);
                dialog.dispose();
                showCourseHomePage(parentFrame, teacher, selectedCourse);
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a course.");
            }
        });
        
        closeButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(selectButton);
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private static void showCourseHomePage(JFrame parentFrame, Teacher teacher, Course course) {
        JFrame courseFrame = new JFrame("Course: " + course.getName());
        courseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        courseFrame.setSize(800, 600);
        courseFrame.setLocationRelativeTo(null);
        
        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Course title at the top
        JLabel courseTitle = new JLabel(course.getName(), SwingConstants.CENTER);
        courseTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        courseTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Header bar with navigation buttons
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        JButton gradesButton = new JButton("Grades");
        JButton classlistButton = new JButton("Classlist");
        JButton assignmentsButton = new JButton("Assignments");
        JButton groupsButton = new JButton("Groups");
        
        headerPanel.add(gradesButton);
        headerPanel.add(classlistButton);
        headerPanel.add(assignmentsButton);
        headerPanel.add(groupsButton);
        
        // Content panel (will be populated based on selection)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add default welcome content
        JLabel welcomeLabel = new JLabel("Select an option from the header bar above.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Add action listeners for header buttons
        gradesButton.addActionListener(e -> {
            contentPanel.removeAll();
            showGradesPanel(contentPanel, teacher, course);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        
        classlistButton.addActionListener(e -> {
            contentPanel.removeAll();
            showClasslistPanel(contentPanel, teacher, course);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        
        assignmentsButton.addActionListener(e -> {
            contentPanel.removeAll();
            showAssignmentsPanel(contentPanel, teacher, course);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        
        groupsButton.addActionListener(e -> {
            contentPanel.removeAll();
            showGroupsPanel(contentPanel, teacher, course);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        
        // Add components to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(courseTitle, BorderLayout.NORTH);
        topPanel.add(headerPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add a back button at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            courseFrame.dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        courseFrame.add(mainPanel);
        courseFrame.setVisible(true);
    }
    
    private static void showGradesPanel(JPanel contentPanel, Teacher teacher, Course course) {
        // Create a panel with BorderLayout
        JPanel gradesPanel = new JPanel(new BorderLayout(10, 10));
        
        // Create a label for instructions
        JLabel instructionLabel = new JLabel("Assignment Grades Overview", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create a table model for assignments with statistics
        String[] columnNames = {"Assignment", "Average Grade", "Median Grade", "Completed/Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        // Get all assignments
        ArrayList<Assignment> assignments = course.getAssignments();
        
        // Populate table with assignment data
        for (Assignment assignment : assignments) {
            // For now, leave statistics empty as mentioned in the requirements
            Object[] rowData = {
                assignment.getName(),
                "", // Average grade (placeholder)
                "", // Median grade (placeholder)
                "" // Completion rate (placeholder)
            };
            tableModel.addRow(rowData);
        }
        
        // Create the table and add it to a scroll pane
        JTable assignmentsTable = new JTable(tableModel);
        assignmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentsTable.setRowHeight(25);
        assignmentsTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane tableScrollPane = new JScrollPane(assignmentsTable);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton viewGradesButton = new JButton("View Student Grades");
        
        viewGradesButton.addActionListener(e -> {
            int selectedRow = assignmentsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String assignmentName = (String) tableModel.getValueAt(selectedRow, 0);
                
                // Find the selected assignment
                final Assignment selectedAssignment = findAssignment(assignments, assignmentName);
                
                if (selectedAssignment != null) {
                    // Create and show the student grades dialog directly
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Student Grades for " + selectedAssignment.getName());
                    dialog.setModal(true);
                    dialog.setSize(500, 400);
                    dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(contentPanel));
                    
                    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    // Create table for student grades
                    String[] studentColumnNames = {"Student", "Grade", "Status"};
                    DefaultTableModel studentTableModel = new DefaultTableModel(studentColumnNames, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return column == 1; // Only make the grade column editable
                        }
                    };
                    
                    // Get students enrolled in the course
                    StudentList students = course.getStudents();
                    ArrayList<Student> studentList = students.getStudents();
                    
                    // Populate table with student data
                    for (Student student : studentList) {
                        // Check if assignment is graded for this student
                        boolean isGraded = student.getGraded().contains(selectedAssignment);
                        double grade = 0;
                        String status = isGraded ? "Graded" : "Not Graded";
                        
                        // If graded, get the actual grade (this part depends on your model's implementation)
                        if (isGraded) {
                            // We would need to implement a method to get a specific assignment's grade
                            // For now, just use a placeholder
                            grade = 0.0; // Placeholder
                        }
                        
                        Object[] rowData = {
                            student.getFirstName() + " " + student.getLastName(),
                            isGraded ? grade : "",
                            status
                        };
                        studentTableModel.addRow(rowData);
                    }
                    
                    JTable studentTable = new JTable(studentTableModel);
                    studentTable.setRowHeight(25);
                    studentTable.getTableHeader().setReorderingAllowed(false);
                    
                    JScrollPane studentTableScrollPane = new JScrollPane(studentTable);
                    
                    // Create button panel
                    JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    JButton saveButton = new JButton("Save Grades");
                    JButton closeButton = new JButton("Close");
                    
                    saveButton.addActionListener(event -> {
                        // Save all grades that have been entered
                        for (int i = 0; i < studentTableModel.getRowCount(); i++) {
                            String studentName = (String) studentTableModel.getValueAt(i, 0);
                            Object gradeObj = studentTableModel.getValueAt(i, 1);
                            
                            if (gradeObj != null && !gradeObj.toString().isEmpty()) {
                                try {
                                    double grade = Double.parseDouble(gradeObj.toString());
                                    
                                    // Find the student
                                    String[] nameParts = studentName.split(" ");
                                    String firstName = nameParts[0];
                                    String lastName = nameParts[1];
                                    
                                    for (Student s : studentList) {
                                        if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                                            // Add the grade
                                            teacher.addAssignmentGrade(s, course.getName(), selectedAssignment, grade);
                                            studentTableModel.setValueAt("Graded", i, 2); // Update status
                                            break;
                                        }
                                    }
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(dialog, 
                                            "Invalid grade format for " + studentName + ". Please enter a valid number.");
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(dialog, "Grades saved successfully!");
                    });
                    
                    closeButton.addActionListener(event -> dialog.dispose());
                    
                    dialogButtonPanel.add(saveButton);
                    dialogButtonPanel.add(closeButton);
                    
                    // Add components to the main panel
                    mainPanel.add(studentTableScrollPane, BorderLayout.CENTER);
                    mainPanel.add(dialogButtonPanel, BorderLayout.SOUTH);
                    
                    dialog.add(mainPanel);
                    dialog.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(contentPanel, "Please select an assignment.");
            }
        });
        
        buttonPanel.add(viewGradesButton);
        
        // Add components to the grades panel
        gradesPanel.add(instructionLabel, BorderLayout.NORTH);
        gradesPanel.add(tableScrollPane, BorderLayout.CENTER);
        gradesPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the grades panel to the content panel
        contentPanel.add(gradesPanel);
    }
    
    private static void showClasslistPanel(JPanel contentPanel, Teacher teacher, Course course) {
        // Create a panel with BorderLayout
        JPanel classlistPanel = new JPanel(new BorderLayout(10, 10));
        
        // Create a label for instructions
        JLabel instructionLabel = new JLabel("Class List for " + course.getName(), SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create a table model for students
        String[] columnNames = {"Student", "Course Average"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        // Get students enrolled in the course
        StudentList students = course.getStudents();
        ArrayList<Student> studentList = students.getStudents();
        
        // Populate table with student data
        for (Student student : studentList) {
            double grade = student.getGrade(course) * 100; // Convert to percentage
            
            Object[] rowData = {
                student.getFirstName() + " " + student.getLastName(),
                String.format("%.2f%%", grade)
            };
            tableModel.addRow(rowData);
        }
        
        // Create the table and add it to a scroll pane
        JTable studentsTable = new JTable(tableModel);
        studentsTable.setRowHeight(25);
        studentsTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane tableScrollPane = new JScrollPane(studentsTable);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addStudentButton = new JButton("Add Student");
        JButton removeStudentButton = new JButton("Remove Student");
        JButton importStudentsButton = new JButton("Import Students");
        
        addStudentButton.addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog(contentPanel, "Enter student's first name:");
            if (firstName != null && !firstName.trim().isEmpty()) {
                String lastName = JOptionPane.showInputDialog(contentPanel, "Enter student's last name:");
                if (lastName != null && !lastName.trim().isEmpty()) {
                    // Check if student exists
                    Student student = null;
                    for (Person p : aManager.getUsers()) {
                        if (p instanceof Student && 
                            p.getFirstName().equalsIgnoreCase(firstName) && 
                            p.getLastName().equalsIgnoreCase(lastName)) {
                            student = (Student) p;
                            break;
                        }
                    }
                    
                    if (student == null) {
                        // Create new student
                        student = new Student(firstName, lastName);
                    }
                    
                    teacher.addStudent(course, student);
                    JOptionPane.showMessageDialog(contentPanel, "Student added successfully!");
                    
                    // Refresh the classlist panel
                    contentPanel.removeAll();
                    showClasslistPanel(contentPanel, teacher, course);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
            }
        });
        
        removeStudentButton.addActionListener(e -> {
            int selectedRow = studentsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String studentName = (String) tableModel.getValueAt(selectedRow, 0);
                String[] nameParts = studentName.split(" ");
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                
                for (Student s : studentList) {
                    if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                        int confirm = JOptionPane.showConfirmDialog(contentPanel, 
                                "Are you sure you want to remove " + studentName + " from this course?",
                                "Confirm Removal", JOptionPane.YES_NO_OPTION);
                        
                        if (confirm == JOptionPane.YES_OPTION) {
                            teacher.removeStudent(course, s);
                            JOptionPane.showMessageDialog(contentPanel, "Student removed successfully!");
                            
                            // Refresh the classlist panel
                            contentPanel.removeAll();
                            showClasslistPanel(contentPanel, teacher, course);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        }
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(contentPanel, "Please select a student to remove.");
            }
        });
        
        importStudentsButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Student List File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text/CSV files", "txt", "csv"));
            
            int result = fileChooser.showOpenDialog(contentPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                importStudentsFromFile(selectedFile, teacher, course);
                
                // Refresh the classlist panel
                contentPanel.removeAll();
                showClasslistPanel(contentPanel, teacher, course);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        
        buttonPanel.add(addStudentButton);
        buttonPanel.add(removeStudentButton);
        buttonPanel.add(importStudentsButton);
        
        // Add components to the classlist panel
        classlistPanel.add(instructionLabel, BorderLayout.NORTH);
        classlistPanel.add(tableScrollPane, BorderLayout.CENTER);
        classlistPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the classlist panel to the content panel
        contentPanel.add(classlistPanel);
    }
    
    private static void showAssignmentsPanel(JPanel contentPanel, Teacher teacher, Course course) {
        // Create a panel with BorderLayout
        JPanel assignmentsPanel = new JPanel(new BorderLayout(10, 10));
        
        // Create a label for instructions
        JLabel instructionLabel = new JLabel("Assignments for " + course.getName(), SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create a table model for assignments with statistics
        String[] columnNames = {"Assignment", "Total Points", "Average", "Median", "Completed/Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        // Get all assignments - make sure we get a fresh copy
        ArrayList<Assignment> assignments = course.getAssignments();
        
        // Populate table with assignment data
        for (Assignment assignment : assignments) {
            Object[] rowData = {
                assignment.getName(),
                assignment.getTotalPoints(),
                "", // Average (placeholder)
                "", // Median (placeholder)
                "" // Completion rate (placeholder)
            };
            tableModel.addRow(rowData);
        }
        
        // Create the table and add it to a scroll pane
        JTable assignmentsTable = new JTable(tableModel);
        assignmentsTable.setRowHeight(25);
        assignmentsTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane tableScrollPane = new JScrollPane(assignmentsTable);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addAssignmentButton = new JButton("Add Assignment");
        JButton removeAssignmentButton = new JButton("Remove Assignment");
        
        addAssignmentButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(contentPanel, "Enter assignment name:");
            if (name != null && !name.trim().isEmpty()) {
                String pointsStr = JOptionPane.showInputDialog(contentPanel, "Enter total points:");
                try {
                    double points = Double.parseDouble(pointsStr);
                    Assignment newAssignment = new Assignment(name, points);
                    
                    // Add the assignment to the course
                    course.addAssg(newAssignment);
                    
                    // Add row to the table
                    Object[] rowData = {
                        name,
                        points,
                        "", // Average (placeholder)
                        "", // Median (placeholder)
                        "" // Completion rate (placeholder)
                    };
                    tableModel.addRow(rowData);
                    
                    // Add to local assignments list
                    assignments.add(newAssignment);
                    
                    JOptionPane.showMessageDialog(contentPanel, "Assignment added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contentPanel, "Invalid points value.");
                }
            }
        });
        
        removeAssignmentButton.addActionListener(e -> {
            int selectedRow = assignmentsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String assignmentName = (String) tableModel.getValueAt(selectedRow, 0);
                
                // Find the selected assignment
                final Assignment selectedAssignment = findAssignment(assignments, assignmentName);
                
                if (selectedAssignment != null) {
                    int confirm = JOptionPane.showConfirmDialog(contentPanel, 
                            "Are you sure you want to remove assignment '" + assignmentName + "'?",
                            "Confirm Removal", JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        // First, remove from the course - this will update the model
                        course.removeAssg(selectedAssignment);
                        
                        // Remove the row from the table model - this updates the UI
                        tableModel.removeRow(selectedRow);
                        
                        // Explicitly remove the assignment from our local assignments list
                        // This ensures our in-memory copy stays in sync
                        assignments.remove(selectedAssignment);
                        
                        JOptionPane.showMessageDialog(contentPanel, "Assignment removed successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(contentPanel, "Please select an assignment to remove.");
            }
        });
        
        buttonPanel.add(addAssignmentButton);
        buttonPanel.add(removeAssignmentButton);
        
        // Add components to the assignments panel
        assignmentsPanel.add(instructionLabel, BorderLayout.NORTH);
        assignmentsPanel.add(tableScrollPane, BorderLayout.CENTER);
        assignmentsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the assignments panel to the content panel
        contentPanel.add(assignmentsPanel);
    }
    
    private static void showGroupsPanel(JPanel contentPanel, Teacher teacher, Course course) {
        // Create a panel with BorderLayout
        JPanel groupsPanel = new JPanel(new BorderLayout(10, 10));
        
        // Create a label for instructions
        JLabel instructionLabel = new JLabel("Create Student Groups for " + course.getName(), SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create options panel
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Create radio buttons for grouping options
        JRadioButton byNumberOfGroupsRadio = new JRadioButton("Group by Number of Groups");
        JRadioButton byStudentsPerGroupRadio = new JRadioButton("Group by Students Per Group");
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(byNumberOfGroupsRadio);
        buttonGroup.add(byStudentsPerGroupRadio);
        byNumberOfGroupsRadio.setSelected(true);
        
        // Create number input panel
        JPanel numberPanel = new JPanel(new BorderLayout(5, 5));
        JLabel numberLabel = new JLabel("Enter number:");
        JTextField numberField = new JTextField();
        numberPanel.add(numberLabel, BorderLayout.WEST);
        numberPanel.add(numberField, BorderLayout.CENTER);
        
        // Create button to generate groups
        JButton generateButton = new JButton("Generate Groups");
        
        // Create text area to display results
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane resultsScrollPane = new JScrollPane(resultsArea);
        
        // Add action listener for generate button
        generateButton.addActionListener(e -> {
            String input = numberField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Please enter a number.");
                return;
            }
            
            try {
                int number = Integer.parseInt(input);
                String result;
                
                if (byNumberOfGroupsRadio.isSelected()) {
                    result = teacher.makeXGroups(course.getName(), number);
                } else {
                    result = teacher.makeGroupsOfXStudents(course.getName(), number);
                }
                
                resultsArea.setText(result);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(contentPanel, "Please enter a valid number.");
            }
        });
        
        // Add components to options panel
        optionsPanel.add(byNumberOfGroupsRadio);
        optionsPanel.add(byStudentsPerGroupRadio);
        optionsPanel.add(numberPanel);
        optionsPanel.add(generateButton);
        
        // Add components to groups panel
        groupsPanel.add(instructionLabel, BorderLayout.NORTH);
        groupsPanel.add(optionsPanel, BorderLayout.CENTER);
        groupsPanel.add(resultsScrollPane, BorderLayout.SOUTH);
        
        // Add the groups panel to the content panel
        contentPanel.add(groupsPanel);
    }
    
    private static void importStudentsFromFile(File file, Teacher teacher, Course course) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String lastName = parts[0].trim();
                    String firstName = parts[1].trim();
                    
                    // Check if student exists
                    Student student = null;
                    for (Person p : aManager.getUsers()) {
                        if (p instanceof Student && 
                            p.getFirstName().equalsIgnoreCase(firstName) && 
                            p.getLastName().equalsIgnoreCase(lastName)) {
                            student = (Student) p;
                            break;
                        }
                    }
                    
                    if (student == null) {
                        // Create new student
                        student = new Student(firstName, lastName);
                    }
                    
                    teacher.addStudent(course, student);
                    count++;
                }
            }
            
            JOptionPane.showMessageDialog(null, count + " students imported successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Helper method to find an assignment by name
     */
    private static Assignment findAssignment(ArrayList<Assignment> assignments, String name) {
        for (Assignment a : assignments) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }
    
    private static void showCoursesDialog(JFrame parentFrame, ArrayList<Course> courses, String title) {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No courses to display.");
            return;
        }
        
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        
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
}