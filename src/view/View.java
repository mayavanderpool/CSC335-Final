package view;

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
                Student student = aManager.getStudentByUsername(username);

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
                Teacher teacher = aManager.getTeacherByUsername(username);

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
                double grade = student.getGrade(course); 
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
        JPanel headerPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        JButton gradesButton = new JButton("Grades");
        JButton classlistButton = new JButton("Classlist");
        JButton assignmentsButton = new JButton("Assignments");
        JButton groupsButton = new JButton("Groups");
        
        // Add mark as completed button with a different color
        JButton completeButton = new JButton("Mark as Completed");
        if (course.isCompleted()) {
            completeButton.setText("Course Completed");
            completeButton.setEnabled(false);
            completeButton.setBackground(new Color(200, 255, 200)); // Light green
        } else {
            completeButton.setBackground(new Color(255, 230, 230)); // Light red
        }
        
        headerPanel.add(gradesButton);
        headerPanel.add(classlistButton);
        headerPanel.add(assignmentsButton);
        headerPanel.add(groupsButton);
        headerPanel.add(completeButton);
        
        // Content panel (will be populated based on selection)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add action listener for complete button
        completeButton.addActionListener(e -> {
            course.setCompleted();
            completeButton.setText("Course Completed");
            completeButton.setEnabled(false);
            completeButton.setBackground(new Color(200, 255, 200)); // Light green
        });
        
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
        
        // Default to classlist view instead of showing the welcome message
        showClasslistPanel(contentPanel, teacher, course);
        
        courseFrame.setVisible(true);
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
            double grade = student.getGrade(course); // Convert to percentage
            
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
                   if(aManager.personExists(lastName, firstName)){
						student = aManager.getStudentByName(lastName, firstName);
				   }
                    
                    if (student == null) {
                        // Create new student
                        student = new Student(firstName, lastName);
                    }
                    
                    teacher.addStudent(course, student);
                    
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
                        // Removed the confirmation dialog here
                        teacher.removeStudent(course, s);
                        
                        // Refresh the classlist panel
                        contentPanel.removeAll();
                        showClasslistPanel(contentPanel, teacher, course);
                        contentPanel.revalidate();
                        contentPanel.repaint();
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
    
    private static void showGradesPanel(JPanel contentPanel, Teacher teacher, Course course) {
        // Create a panel with BorderLayout for the main grades panel
        JPanel gradesPanel = new JPanel(new BorderLayout(10, 10));
        
        // Create a label for instructions
        JLabel instructionLabel = new JLabel("Assignment Grades Overview", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create a split pane with assignments on top and student grades below
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.4); // Assignments get 40% of the space
        
        // TOP PANEL - Assignments list
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        
        // Create a table model for assignments with statistics
        String[] columnNames = {"Assignment", "Total Points", "Average", "Median", "Completed/Total"};
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
                assignment.getTotalPoints(),
                teacher.getAssgClassAverage(course, assignment),
                teacher.getAssgMedian(course, assignment),
                teacher.getCompletedData(course, assignment)
            };
            tableModel.addRow(rowData);
        }
        
        // Create the table and add it to a scroll pane
        JTable assignmentsTable = new JTable(tableModel);
        assignmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentsTable.setRowHeight(25);
        assignmentsTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane tableScrollPane = new JScrollPane(assignmentsTable);
        topPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // BOTTOM PANEL - Student grades for selected assignment
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        
        // Create a label for student grades section
        JLabel studentGradesLabel = new JLabel("Select an assignment to view student grades", SwingConstants.CENTER);
        studentGradesLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        
        // Create table model for student grades
        String[] studentColumnNames = {"Student", "Grade", "Status"};
        DefaultTableModel studentTableModel = new DefaultTableModel(studentColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Only make the grade column editable
            }
        };
        
        JTable studentTable = new JTable(studentTableModel);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane studentTableScrollPane = new JScrollPane(studentTable);
        bottomPanel.add(studentGradesLabel, BorderLayout.NORTH);
        bottomPanel.add(studentTableScrollPane, BorderLayout.CENTER);
        
        // Add listener to assignments table to update student grades when an assignment is selected
        assignmentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Only respond to the final event in a series
                int selectedRow = assignmentsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Update student grades label
                    String assignmentName = (String) tableModel.getValueAt(selectedRow, 0);
                    studentGradesLabel.setText("Student Grades for: " + assignmentName);
                    
                    // Find the selected assignment
                    final Assignment selectedAssignment = findAssignment(assignments, assignmentName);
                    
                    if (selectedAssignment != null) {
                        // Clear the student grades table
                        studentTableModel.setRowCount(0);
                        
                        // Get students enrolled in the course
                        StudentList students = course.getStudents();
                        ArrayList<Student> studentList = students.getStudents();
                        
                        // Populate table with student data
                        for (Student student : studentList) {
                            // First get the actual grade from the student for this assignment
                            double grade = student.getAssgGrade(selectedAssignment, course.getName());
                            
                            // Check if assignment is graded - if grade is > 0, it's graded
                            boolean isGraded = grade > 0;
                            String status = isGraded ? "Graded" : "Not Graded";
                            
                            Object[] rowData = {
                                student.getFirstName() + " " + student.getLastName(),
                                isGraded ? grade : "",  // Show grade value if graded
                                status
                            };
                            studentTableModel.addRow(rowData);
                        }
                        
                        // Add TableModelListener to handle grade entries in real-time
                        // First remove any existing listeners to prevent duplicates
                        for (javax.swing.event.TableModelListener l : studentTableModel.getTableModelListeners()) {
                            if (l instanceof GradeEntryListener) {
                                studentTableModel.removeTableModelListener(l);
                            }
                        }
                        
                        // Add new listener for auto-saving grades
                        studentTableModel.addTableModelListener(new GradeEntryListener(teacher, course.getName(), selectedAssignment, studentTableModel, studentList));
                    }
                } else {
                    // No assignment selected, clear student grades
                    studentTableModel.setRowCount(0);
                    studentGradesLabel.setText("Select an assignment to view student grades");
                }
            }
        });
        
        // Add panels to split pane
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);
        
        // Add components to the grades panel
        gradesPanel.add(instructionLabel, BorderLayout.NORTH);
        gradesPanel.add(splitPane, BorderLayout.CENTER);
        
        // Add the grades panel to the content panel
        contentPanel.add(gradesPanel);
    }
    
    // Custom TableModelListener for handling grade entry and saving
    // Custom TableModelListener for handling grade entry and saving
private static class GradeEntryListener implements javax.swing.event.TableModelListener {
    private Teacher teacher;
    private String courseName;
    private Assignment assignment;
    private DefaultTableModel tableModel;
    private ArrayList<Student> studentList;
    
    public GradeEntryListener(Teacher teacher, String courseName, Assignment assignment, 
                              DefaultTableModel tableModel, ArrayList<Student> studentList) {
        this.teacher = teacher;
        this.courseName = courseName;
        this.assignment = assignment;
        this.tableModel = tableModel;
        this.studentList = studentList;
    }
    
    @Override
    public void tableChanged(javax.swing.event.TableModelEvent e) {
        if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 1) {
            int row = e.getFirstRow();
            Object gradeObj = tableModel.getValueAt(row, 1);
            String studentName = (String) tableModel.getValueAt(row, 0);
            
            if (gradeObj != null && !gradeObj.toString().isEmpty()) {
                try {
                    double grade = Double.parseDouble(gradeObj.toString());
                    
                    // Update status immediately
                    tableModel.setValueAt("Graded", row, 2);
                    
                    // Save the grade to the model
                    String[] nameParts = studentName.split(" ");
                    if (nameParts.length >= 2) {
                        String firstName = nameParts[0];
                        String lastName = nameParts[1];
                        
                        // Find student and save grade
                        for (Student s : studentList) {
                            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                                // The critical fix: use courseName instead of lastName
                                // This properly calls the teacher's method with the correct parameters
                                teacher.addAssignmentGrade(s, courseName, assignment, grade);
                                break;
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Invalid number format - do nothing
                }
            }
        }
    }
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

                    if(aManager.personExists(lastName, firstName)){
						student = aManager.getStudentByName(firstName, lastName);
					}
                    
                    if (student == null) {
                        // Create new student
                        student = new Student(firstName, lastName);
                    }
                    
                    teacher.addStudent(course, student);
                    count++;
                }
            }
            
            JOptionPane.showMessageDialog(null, count + " students imported.");
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
        // Populate table with assignment data
for (Assignment assignment : assignments) {
    // Calculate statistics using teacher methods
    double avgGrade = 0;
    String medianGrade = "0.0";
    String avgDisplay = "0.0";
    String medianDisplay = "0.0";
    String completionRate = "0/0";
    
    try {
        // Get average grade using teacher method
        avgGrade = teacher.getAssgClassAverage(course, assignment);
        if (!Double.isNaN(avgGrade)) {
            avgDisplay = String.format("%.1f", avgGrade);
        }
        
        // Get median grade using teacher method
        medianGrade = teacher.getAssgMedian(course, assignment);
        if (!medianGrade.equals("-1")) {
            medianDisplay = String.format("%.1f", medianGrade);
        }
		else{
			medianDisplay = "";
		}
        
        // Calculate completion rate (count of graded vs total students)
        int gradedCount = 0;
        int totalCount = 0;
        StudentList students = course.getStudents();
        for (Student s : students.getStudents()) {
            totalCount++;
            if (s.getGraded().contains(assignment)) {
                gradedCount++;
            }
        }
        
        if (totalCount > 0) {
            completionRate = gradedCount + "/" + totalCount;
        }
    } catch (Exception e) {
        // Ignore exceptions during statistics calculation
    }
    
    Object[] rowData = {
        assignment.getName(),
        assignment.getTotalPoints(),
        avgDisplay,
        medianDisplay,
        completionRate
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
                    // Removed the confirmation dialog here
                    // First, remove from the course - this will update the model
                    course.removeAssg(selectedAssignment);
                    
                    // Remove the row from the table model - this updates the UI
                    tableModel.removeRow(selectedRow);
                    
                    // Explicitly remove the assignment from our local assignments list
                    // This ensures our in-memory copy stays in sync
                    assignments.remove(selectedAssignment);
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

	
	
}