package model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
		frame.setSize(800, 700);
		frame.setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());

		// Welcome message
		JLabel welcomeLabel = new JLabel("Welcome, " + teacher.getFirstName() + "!", SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		// Create scrollable panel for buttons
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

		// Create buttons for teacher functions
		JButton viewCurrentCoursesButton = new JButton("View Current Courses");
		JButton viewCompletedCoursesButton = new JButton("View Completed Courses");
		JButton addCourseButton = new JButton("Add New Course");
		JButton manageCourseButton = new JButton("Manage Course");
		JButton viewUngradedButton = new JButton("View Ungraded Assignments");
		JButton logoutButton = new JButton("Logout");

		// Add action listeners for current and completed courses
		viewCurrentCoursesButton.addActionListener(e -> {
			ArrayList<Course> courses = teacher.getCompletedOrCurrentCourses(false);
			showCoursesDialog(frame, courses, "Current Courses");
		});
		
		viewCompletedCoursesButton.addActionListener(e -> {
			ArrayList<Course> courses = teacher.getCompletedOrCurrentCourses(true);
			showCoursesDialog(frame, courses, "Completed Courses");
		});

		addCourseButton.addActionListener(e -> {
			String courseName = JOptionPane.showInputDialog(frame, "Enter the name of the new course:");
			if (courseName != null && !courseName.trim().isEmpty()) {
				Course newCourse = new Course(courseName);
				teacher.addCourse(newCourse);
				JOptionPane.showMessageDialog(frame, "Course '" + courseName + "' added successfully!");
			}
		});

		manageCourseButton.addActionListener(e -> {
			ArrayList<Course> courses = teacher.getCourses();
			if (courses.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "You don't have any courses to manage.");
				return;
			}

			String[] courseNames = new String[courses.size()];
			for (int i = 0; i < courses.size(); i++) {
				courseNames[i] = courses.get(i).getName();
			}

			String selectedCourse = (String) JOptionPane.showInputDialog(
					frame,
					"Select a course to manage:",
					"Course Selection",
					JOptionPane.QUESTION_MESSAGE,
					null,
					courseNames,
					courseNames[0]);

			if (selectedCourse != null) {
				Course course = teacher.getCourse(selectedCourse);
				showCourseManagementDialog(frame, teacher, course);
			}
		});

		viewUngradedButton.addActionListener(e -> {
			String ungradedInfo = teacher.getUngradedAssignments();
			if (ungradedInfo.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "There are no ungraded assignments.");
			} else {
				JTextArea textArea = new JTextArea(20, 50);
				textArea.setText(ungradedInfo);
				textArea.setEditable(false);
				JScrollPane scrollPane = new JScrollPane(textArea);
				JOptionPane.showMessageDialog(frame, scrollPane, "Ungraded Assignments",
						JOptionPane.INFORMATION_MESSAGE);
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
		buttonPanel.add(manageCourseButton);
		buttonPanel.add(viewUngradedButton);
		buttonPanel.add(logoutButton);

		// Add components to main panel
		JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
		buttonScrollPane.setBorder(null);

		mainPanel.add(welcomeLabel, BorderLayout.NORTH);
		mainPanel.add(buttonScrollPane, BorderLayout.CENTER);

		frame.add(mainPanel);
		frame.setVisible(true);
	}

	private static void showCourseManagementDialog(JFrame parentFrame, Teacher teacher, Course course) {
        JDialog dialog = new JDialog(parentFrame, "Managing Course: " + course.getName(), true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        
        JButton addAssignmentButton = new JButton("Add Assignment");
        JButton removeAssignmentButton = new JButton("Remove Assignment");
        JButton addStudentButton = new JButton("Add Student");
        JButton removeStudentButton = new JButton("Remove Student");
        JButton importStudentsButton = new JButton("Import Students");
        JButton viewStudentsButton = new JButton("View Students");
        JButton addGradesButton = new JButton("Add Grades");
        JButton calculateAveragesButton = new JButton("Calculate Class Averages & Medians");
        JButton calculateStudentAverageButton = new JButton("Calculate Student Average");
        JButton sortStudentsButton = new JButton("Sort Students");
        JButton createGroupsButton = new JButton("Create Student Groups");
        JButton assignFinalGradesButton = new JButton("Assign Final Grades");
        JButton setCompletedButton = new JButton("Mark Course as Completed");
        JButton closeButton = new JButton("Close");
        
        // Add action listeners
        addAssignmentButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(dialog, "Enter assignment name:");
            if (name != null && !name.trim().isEmpty()) {
                String pointsStr = JOptionPane.showInputDialog(dialog, "Enter total points:");
                try {
                    double points = Double.parseDouble(pointsStr);
                    Assignment newAssignment = new Assignment(name, points);
                    teacher.addAssignmentToCourse(course.getName(), newAssignment);
                    JOptionPane.showMessageDialog(dialog, "Assignment added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid points value.");
                }
            }
        });
        
        removeAssignmentButton.addActionListener(e -> {
            ArrayList<Assignment> assignments = course.getAssignments();
            if (assignments.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No assignments to remove.");
                return;
            }
            
            String[] assignmentNames = new String[assignments.size()];
            for (int i = 0; i < assignments.size(); i++) {
                assignmentNames[i] = assignments.get(i).getName();
            }
            
            String selectedAssignment = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Select an assignment to remove:",
                    "Remove Assignment",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    assignmentNames,
                    assignmentNames[0]);
            
            if (selectedAssignment != null) {
                // Find the selected assignment object
                Assignment assignment = null;
                for (Assignment a : assignments) {
                    if (a.getName().equals(selectedAssignment)) {
                        assignment = a;
                        break;
                    }
                }
                
                if (assignment != null) {
                    teacher.removeAssignmentFromCourse(course.getName(), assignment);
                    JOptionPane.showMessageDialog(dialog, "Assignment removed successfully!");
                }
            }
        });
        
        addStudentButton.addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog(dialog, "Enter student's first name:");
            if (firstName != null && !firstName.trim().isEmpty()) {
                String lastName = JOptionPane.showInputDialog(dialog, "Enter student's last name:");
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
                    JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                }
            }
        });
        
        removeStudentButton.addActionListener(e -> {
            StudentList students = course.getStudents();
            ArrayList<Student> studentList = students.getStudents();
            
            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No students to remove.");
                return;
            }
            
            String[] studentNames = new String[studentList.size()];
            for (int i = 0; i < studentList.size(); i++) {
                Student s = studentList.get(i);
                studentNames[i] = s.getFirstName() + " " + s.getLastName();
            }
            
            String selectedStudent = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Select a student to remove:",
                    "Remove Student",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    studentNames,
                    studentNames[0]);
            
            if (selectedStudent != null) {
                // Find the selected student
                String[] nameParts = selectedStudent.split(" ");
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                
                for (Student s : studentList) {
                    if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                        teacher.removeStudent(course, s);
                        JOptionPane.showMessageDialog(dialog, "Student removed successfully!");
                        break;
                    }
                }
            }
        });
        
        importStudentsButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Student List File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text/CSV files", "txt", "csv"));
            
            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                importStudentsFromFile(selectedFile, teacher, course);
            }
        });
        
        viewStudentsButton.addActionListener(e -> {
            StudentList students = course.getStudents();
            ArrayList<Student> studentList = students.getStudents();
            
            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No students enrolled in this course.");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Students enrolled in ").append(course.getName()).append(":\n\n");
            
            for (Student s : studentList) {
                sb.append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
            }
            
            JTextArea textArea = new JTextArea(20, 40);
            textArea.setText(sb.toString());
            textArea.setEditable(false);
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(dialog, scrollPane, "Student List", JOptionPane.INFORMATION_MESSAGE);
        });
        
        addGradesButton.addActionListener(e -> {
            StudentList students = course.getStudents();
            ArrayList<Student> studentList = students.getStudents();
            ArrayList<Assignment> assignments = course.getAssignments();
            
            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No students enrolled in this course.");
                return;
            }
            
            if (assignments.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No assignments in this course.");
                return;
            }
            
            // Select student
            String[] studentNames = new String[studentList.size()];
            for (int i = 0; i < studentList.size(); i++) {
                Student s = studentList.get(i);
                studentNames[i] = s.getFirstName() + " " + s.getLastName();
            }
            
            String selectedStudent = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Select a student:",
                    "Add Grades",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    studentNames,
                    studentNames[0]);
            
            if (selectedStudent != null) {
                // Select assignment
                String[] assignmentNames = new String[assignments.size()];
                for (int i = 0; i < assignments.size(); i++) {
                    assignmentNames[i] = assignments.get(i).getName();
                }
                
                String selectedAssignment = (String) JOptionPane.showInputDialog(
                        dialog,
                        "Select an assignment:",
                        "Add Grades",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        assignmentNames,
                        assignmentNames[0]);
                
                if (selectedAssignment != null) {
                    // Find student and assignment objects
                    Student student = null;
                    Assignment assignment = null;
                    
                    String[] nameParts = selectedStudent.split(" ");
                    String firstName = nameParts[0];
                    String lastName = nameParts[1];
                    
                    for (Student s : studentList) {
                        if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                            student = s;
                            break;
                        }
                    }
                    
                    for (Assignment a : assignments) {
                        if (a.getName().equals(selectedAssignment)) {
                            assignment = a;
                            break;
                        }
                    }
                    
                    if (student != null && assignment != null) {
                        String gradeStr = JOptionPane.showInputDialog(dialog, 
                                "Enter grade for " + student.getFirstName() + " on " + assignment.getName() + 
                                " (max: " + assignment.getTotalPoints() + "):");
                        
                        try {
                            double grade = Double.parseDouble(gradeStr);
                            if (grade >= 0 && grade <= assignment.getTotalPoints()) {
                                teacher.addAssignmentGrade(student, course.getName(), assignment, grade);
                                JOptionPane.showMessageDialog(dialog, "Grade added successfully!");
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Grade must be between 0 and " + 
                                        assignment.getTotalPoints() + ".");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialog, "Invalid grade value.");
                        }
                    }
                }
            }
        });
        
        calculateAveragesButton.addActionListener(e -> {
            ArrayList<Assignment> assignments = course.getAssignments();
            
            if (assignments.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No assignments in this course.");
                return;
            }
            
            String[] assignmentNames = new String[assignments.size() + 1];
            assignmentNames[0] = "Course Average";
            for (int i = 0; i < assignments.size(); i++) {
                assignmentNames[i + 1] = assignments.get(i).getName();
            }
            
            String selectedItem = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Calculate average/median for:",
                    "Class Statistics",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    assignmentNames,
                    assignmentNames[0]);
            
            if (selectedItem != null) {
                double average, median;
                
                if (selectedItem.equals("Course Average")) {
                    average = teacher.getStudentAverage(course);
                    median = teacher.getStudentMedian(course);
                    
                    String message = "Course Statistics:\n\n" +
                            "Class Average: " + String.format("%.2f", average * 100) + "%\n" +
                            "Class Median: " + String.format("%.2f", median * 100) + "%";
                    
                    JOptionPane.showMessageDialog(dialog, message);
                } else {
                    // For specific assignment (not implemented in the model classes)
                    JOptionPane.showMessageDialog(dialog, 
                            "Individual assignment statistics are not implemented yet.");
                }
            }
        });
        
        calculateStudentAverageButton.addActionListener(e -> {
            StudentList students = course.getStudents();
            ArrayList<Student> studentList = students.getStudents();
            
            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No students enrolled in this course.");
                return;
            }
            
            String[] studentNames = new String[studentList.size()];
            for (int i = 0; i < studentList.size(); i++) {
                Student s = studentList.get(i);
                studentNames[i] = s.getFirstName() + " " + s.getLastName();
            }
            
            String selectedStudent = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Select a student:",
                    "Student Average",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    studentNames,
                    studentNames[0]);
            
            if (selectedStudent != null) {
                String[] nameParts = selectedStudent.split(" ");
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                
                for (Student s : studentList) {
                    if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                        double grade = teacher.getStudentGrade(course, s);
                        String letterGrade = s.getLetterGrade(course);
                        
                        String message = "Student: " + s.getFirstName() + " " + s.getLastName() + "\n" +
                                "Course: " + course.getName() + "\n" +
                                "Grade: " + String.format("%.2f", grade * 100) + "% (" + letterGrade + ")";
                        
                        JOptionPane.showMessageDialog(dialog, message);
                        break;
                    }
                }
            }
        });
        
        sortStudentsButton.addActionListener(e -> {
            StudentList students = course.getStudents();
            ArrayList<Student> studentList = students.getStudents();
            
            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No students enrolled in this course.");
                return;
            }
            
            String[] sortOptions = {"First Name", "Last Name", "Username", "Course Grade"};
            
            String selectedOption = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Sort students by:",
                    "Sort Students",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    sortOptions,
                    sortOptions[0]);
            
            if (selectedOption != null) {
                ArrayList<Student> sortedList;
                
                switch (selectedOption) {
                    case "First Name":
                        sortedList = students.getStudentsByFirstName();
                        break;
                    case "Last Name":
                        sortedList = students.getStudentsByLastName();
                        break;
                    case "Username":
                        sortedList = students.getStudentsByUsername();
                        break;
                    case "Course Grade":
                        sortedList = students.getStudentsByCourseGrade(course);
                        break;
                    default:
                        sortedList = studentList;
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("Students sorted by ").append(selectedOption).append(":\n\n");
                
                for (Student s : sortedList) {
                    sb.append(s.getFirstName()).append(" ").append(s.getLastName());
                    
                    if (selectedOption.equals("Course Grade")) {
                        double grade = s.getGrade(course) * 100;
                        sb.append(" - Grade: ").append(String.format("%.2f", grade)).append("%");
                    } else if (selectedOption.equals("Username")) {
                        sb.append(" - Username: ").append(s.getUserName());
                    }
                    
                    sb.append("\n");
                }
                
                JTextArea textArea = new JTextArea(20, 40);
                textArea.setText(sb.toString());
                textArea.setEditable(false);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(dialog, scrollPane, "Sorted Student List", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        createGroupsButton.addActionListener(e -> {
            StudentList students = course.getStudents();
            ArrayList<Student> studentList = students.getStudents();
            
            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No students enrolled in this course.");
                return;
            }
            
            if (studentList.size() < 2) {
                JOptionPane.showMessageDialog(dialog, "Need at least 2 students to create groups.");
                return;
            }
            
            String[] options = {"Number of Groups", "Number of Students Per Group"};
            
            String selectedOption = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Group by:",
                    "Create Student Groups",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            
            if (selectedOption != null) {
                String input = JOptionPane.showInputDialog(dialog, "Enter number:");
                
                try {
                    int number = Integer.parseInt(input);
                    String result;
                    
                    if (selectedOption.equals("Number of Groups")) {
                        result = teacher.makeXGroups(course.getName(), number);
                    } else {
                        result = teacher.makeGroupsOfXStudents(course.getName(), number);
                    }
                    
                    JTextArea textArea = new JTextArea(20, 40);
                    textArea.setText(result);
                    textArea.setEditable(false);
                    
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(dialog, scrollPane, "Student Groups", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid number.");
                }
            }
        });
        
        assignFinalGradesButton.addActionListener(e -> {
            // This could set the completed flag on the course and potentially export grades
            int confirm = JOptionPane.showConfirmDialog(dialog, 
                    "This will assign final letter grades based on current averages. Continue?",
                    "Confirm Final Grades", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                StudentList students = course.getStudents();
                ArrayList<Student> studentList = students.getStudents();
                
                StringBuilder sb = new StringBuilder();
                sb.append("Final Grades for ").append(course.getName()).append(":\n\n");
                
                for (Student s : studentList) {
                    String letterGrade = s.getLetterGrade(course);
                    sb.append(s.getFirstName()).append(" ").append(s.getLastName())
                      .append(": ").append(letterGrade).append("\n");
                }
                
                JTextArea textArea = new JTextArea(20, 40);
                textArea.setText(sb.toString());
                textArea.setEditable(false);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(dialog, scrollPane, "Final Grades", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        setCompletedButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog, 
                    "Mark this course as completed? This cannot be undone.",
                    "Confirm Completion", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                course.setCompleted();
                JOptionPane.showMessageDialog(dialog, "Course marked as completed!");
                dialog.dispose();
            }
        });
        
        closeButton.addActionListener(e -> dialog.dispose());
        
        // Add buttons to panel
        buttonPanel.add(addAssignmentButton);
        buttonPanel.add(removeAssignmentButton);
        buttonPanel.add(addStudentButton);
        buttonPanel.add(removeStudentButton);
        buttonPanel.add(importStudentsButton);
        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(addGradesButton);
        buttonPanel.add(calculateAveragesButton);
        buttonPanel.add(calculateStudentAverageButton);
        buttonPanel.add(sortStudentsButton);
        buttonPanel.add(createGroupsButton);
        buttonPanel.add(assignFinalGradesButton);
        buttonPanel.add(setCompletedButton);
        buttonPanel.add(closeButton);
        
        // Add button panel to main panel with scroll
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
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