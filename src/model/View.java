package model;

import javax.swing.*;
import java.awt.*;

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
				createStudentPrompt();
			});

			teacherButton.addActionListener(e -> {
				roleFrame.dispose();
				createTeacherPrompt();
			});
		});
	}

	public static void createStudentPrompt() {
		JFrame frame = new JFrame("Student Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel promptLabel = new JLabel("Enter your first and last name:", SwingConstants.CENTER);

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

		JButton enterButton = new JButton("Enter");

		panel.add(promptLabel);
		panel.add(fnamePanel);
		panel.add(lnamePanel);
		panel.add(new JLabel());
		panel.add(enterButton);

		frame.add(panel);
		frame.setVisible(true);

		enterButton.addActionListener(e -> {
			String fname = fnameField.getText().trim();
			String lname = lnameField.getText().trim();

			if (!aManager.personExists(fname, lname)) {
				JOptionPane.showMessageDialog(frame, "Student not found in system.");
				return;
			}

			frame.dispose();
			if (aManager.accountExists(fname, lname)) {
				showLoginForm(fname, lname, true);
			} else {
				showCreateAccountForm(fname, lname, true);
			}
		});
	}

	public static void createTeacherPrompt() {
		JFrame frame = new JFrame("Teacher Login/Create Account");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
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

		JButton continueButton = new JButton("Continue");

		panel.add(fnamePanel);
		panel.add(lnamePanel);
		panel.add(new JLabel());
		panel.add(continueButton);

		frame.add(panel);
		frame.setVisible(true);

		continueButton.addActionListener(e -> {
			String fname = fnameField.getText().trim();
			String lname = lnameField.getText().trim();
			frame.dispose();

			if (aManager.accountExists(fname, lname)) {
				showLoginForm(fname, lname, false);
			} else {
				showCreateAccountForm(fname, lname, false);
			}
		});
	}

	private static void showLoginForm(String fname, String lname, boolean isStudent) {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel userPanel = new JPanel(new BorderLayout(5, 5));
		JLabel userLabel = new JLabel("Username:");
		JTextField usernameField = new JTextField();
		userPanel.add(userLabel, BorderLayout.WEST);
		userPanel.add(usernameField, BorderLayout.CENTER);

		JPanel passPanel = new JPanel(new BorderLayout(5, 5));
		JLabel passLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField();
		passPanel.add(passLabel, BorderLayout.WEST);
		passPanel.add(passwordField, BorderLayout.CENTER);

		JButton loginButton = new JButton("Login");

		panel.add(userPanel);
		panel.add(passPanel);
		panel.add(new JLabel());
		panel.add(loginButton);

		frame.add(panel);
		frame.setVisible(true);

		loginButton.addActionListener(e -> {
			String username = usernameField.getText().trim();
			String password = new String(passwordField.getPassword());
			if (aManager.checkCredentials(username, password)) {
				JOptionPane.showMessageDialog(frame, "Welcome back, " + username + "!");
				frame.dispose();
				showHomePage(fname, lname, isStudent);
			} else {
				JOptionPane.showMessageDialog(frame, "Invalid username or password.");
			}
		});
	}

	private static void showCreateAccountForm(String fname, String lname, boolean isStudent) {
		JFrame frame = new JFrame("Create Account");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel userPanel = new JPanel(new BorderLayout(5, 5));
		JLabel userLabel = new JLabel("Create Username:");
		JTextField usernameField = new JTextField();
		userPanel.add(userLabel, BorderLayout.WEST);
		userPanel.add(usernameField, BorderLayout.CENTER);

		JPanel passPanel = new JPanel(new BorderLayout(5, 5));
		JLabel passLabel = new JLabel("Create Password:");
		JPasswordField passwordField = new JPasswordField();
		passPanel.add(passLabel, BorderLayout.WEST);
		passPanel.add(passwordField, BorderLayout.CENTER);

		JButton createButton = new JButton("Create Account");

		panel.add(userPanel);
		panel.add(passPanel);
		panel.add(new JLabel());
		panel.add(createButton);

		frame.add(panel);
		frame.setVisible(true);

		createButton.addActionListener(e -> {
			String username = usernameField.getText().trim();
			String password = new String(passwordField.getPassword());
			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter a username and password.");
				return;
			}
			aManager.addPassword(fname, lname, username, password);
			JOptionPane.showMessageDialog(frame, "Account created! Welcome, " + username + "!");
			frame.dispose();
			showHomePage(fname, lname, isStudent);
		});
	}

	private static void showHomePage(String fname, String lname, boolean isStudent) {
		JFrame frame = new JFrame("Home - " + fname + " " + lname);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel welcome = new JLabel("Welcome, " + fname + "!", SwingConstants.CENTER);
		welcome.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel.add(welcome);

		if (isStudent) {
			panel.add(new JButton("View Courses"));
			panel.add(new JButton("View Assignments"));
			panel.add(new JButton("Calculate Class Average"));
			panel.add(new JButton("Calculate GPA"));
		} else {
			panel.add(new JButton("Add Course"));
			panel.add(new JButton("View Courses"));
			panel.add(new JButton("Add/Remove Assignments"));
			panel.add(new JButton("Add/Remove Students"));
			panel.add(new JButton("Import Students from CSV"));
			panel.add(new JButton("View Students in Course"));
			panel.add(new JButton("Add Grades to Assignment"));
			panel.add(new JButton("Calculate Class Averages & Medians"));
			panel.add(new JButton("Calculate Student Average"));
			panel.add(new JButton("Sort Students by Name/Username"));
			panel.add(new JButton("Sort Students by Assignment Grade"));
			panel.add(new JButton("Group Students"));
			panel.add(new JButton("Assign Final Grades"));
			panel.add(new JButton("View Ungraded Assignments"));
			panel.add(new JButton("Set Class Average Calculation Mode"));
			panel.add(new JButton("Set Categories/Weights/Drops"));
		}

		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(e -> {
			frame.dispose();
			createRolePrompt();
		});

		panel.add(new JLabel()); // spacer
		panel.add(logoutButton);

		JScrollPane scroll = new JScrollPane(panel);
		frame.add(scroll);
		frame.setVisible(true);
	}
}
