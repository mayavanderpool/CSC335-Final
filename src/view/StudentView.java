package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import controller.StudentController;
import model.Student;
import model.Assignment;
import model.Course;
import model.Observer;

public class StudentView implements Observer {
	private StudentController controller;
	private Student student;
	private JFrame frame;
	private JPanel contentPanel;

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

		// Welcome message at the top
		JLabel welcomeLabel = new JLabel("Welcome, " + student.getFirstName() + "!", SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		// Header bar with navigation buttons (removed Grades button)
		JPanel headerPanel = new JPanel(new GridLayout(1, 3, 10, 0));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		JButton courselistButton = new JButton("Courselist");
		JButton assignmentsButton = new JButton("Assignments");
		JButton gpaButton = new JButton("GPA Calculator");

		headerPanel.add(courselistButton);
		headerPanel.add(assignmentsButton);
		headerPanel.add(gpaButton);

		// Content panel (will be populated based on selection)
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Add action listeners for header buttons (removed grades button listener)
		courselistButton.addActionListener(e -> showCourselistPanel());
		assignmentsButton.addActionListener(e -> showAssignmentsPanel());
		gpaButton.addActionListener(e -> showGPAPanel());

		// Add components to main panel
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(welcomeLabel, BorderLayout.NORTH);
		topPanel.add(headerPanel, BorderLayout.SOUTH);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Add logout button at the bottom
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(e -> controller.logout());
		bottomPanel.add(logoutButton);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		frame.add(mainPanel);
	}

	private void showCourselistPanel() {
		// Create a panel for viewing courses with grades
		JPanel courselistPanel = new JPanel(new BorderLayout(10, 10));

		// Create a label for instructions
		JLabel instructionLabel = new JLabel("Your Courses", SwingConstants.CENTER);
		instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create tabs for current and completed courses
		JTabbedPane tabbedPane = new JTabbedPane();

		// Current courses panel with table
		JPanel currentCoursesPanel = new JPanel(new BorderLayout(10, 10));
		ArrayList<Course> currentCourses = controller.getCurrentCourses();

		// Create table model with course name and grade columns
		String[] currentColumnNames = { "Course Name", "Current Grade" };
		DefaultTableModel currentTableModel = new DefaultTableModel(currentColumnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make cells non-editable
			}
		};

		// Populate table with course data including grades
		for (Course course : currentCourses) {
			double grade = controller.getGradeForCourse(course);
			String letterGrade = controller.getLetterGradeForCourse(course);
			Object[] rowData = {
					course.getName(),
					// Removed course.getTeacher() reference
					String.format("%.2f%% (%s)", grade, letterGrade)
			};
			currentTableModel.addRow(rowData);
		}

		// Create table and scroll pane
		JTable currentCoursesTable = new JTable(currentTableModel);
		currentCoursesTable.setRowHeight(25);
		currentCoursesTable.getTableHeader().setReorderingAllowed(false);

		// Add double-click listener to view course details
		currentCoursesTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int row = currentCoursesTable.getSelectedRow();
					if (row >= 0 && row < currentCourses.size()) {
						Course selectedCourse = currentCourses.get(row);
						openCourseDetails(selectedCourse);
					}
				}
			}
		});

		currentCoursesPanel.add(new JScrollPane(currentCoursesTable), BorderLayout.CENTER);

		// Completed courses panel with table
		JPanel completedCoursesPanel = new JPanel(new BorderLayout(10, 10));
		ArrayList<Course> completedCourses = controller.getCompletedCourses();

		// Create table model with course name and final grade columns
		String[] completedColumnNames = { "Course Name", "Final Grade" };
		DefaultTableModel completedTableModel = new DefaultTableModel(completedColumnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make cells non-editable
			}
		};

		// Populate table with course data including final grades
		for (Course course : completedCourses) {
			double grade = controller.getGradeForCourse(course);
			String letterGrade = controller.getLetterGradeForCourse(course);
			Object[] rowData = {
					course.getName(),
					String.format("%.2f%% (%s)", grade, letterGrade)
			};
			completedTableModel.addRow(rowData);
		}

		// Create table and scroll pane
		JTable completedCoursesTable = new JTable(completedTableModel);
		completedCoursesTable.setRowHeight(25);
		completedCoursesTable.getTableHeader().setReorderingAllowed(false);

		// Add double-click listener for completed courses as well
		completedCoursesTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int row = completedCoursesTable.getSelectedRow();
					if (row >= 0 && row < completedCourses.size()) {
						Course selectedCourse = completedCourses.get(row);
						openCourseDetails(selectedCourse);
					}
				}
			}
		});

		completedCoursesPanel.add(new JScrollPane(completedCoursesTable), BorderLayout.CENTER);

		// Add panels to tabs
		tabbedPane.addTab("Current Courses", currentCoursesPanel);
		tabbedPane.addTab("Completed Courses", completedCoursesPanel);

		// Add components to the courselist panel
		courselistPanel.add(instructionLabel, BorderLayout.NORTH);
		courselistPanel.add(tabbedPane, BorderLayout.CENTER);

		// Update the content panel
		contentPanel.removeAll();
		contentPanel.add(courselistPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void showAssignmentsPanel() {
		// Create a panel for viewing assignments
		JPanel assignmentsPanel = new JPanel(new BorderLayout(10, 10));

		// Create a label for instructions
		JLabel instructionLabel = new JLabel("Your Assignments", SwingConstants.CENTER);
		instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create a panel for the course selection
		JPanel selectionPanel = new JPanel(new BorderLayout(10, 10));
		JLabel selectLabel = new JLabel("Select a course to view assignments:");

		ArrayList<Course> courses = controller.getAllCourses();
		String[] courseNames = new String[courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			courseNames[i] = courses.get(i).getName();
		}

		JComboBox<String> courseComboBox = new JComboBox<>(courseNames);
		JButton viewButton = new JButton("View Assignments");

		JPanel comboPanel = new JPanel(new BorderLayout(5, 0));
		comboPanel.add(courseComboBox, BorderLayout.CENTER);
		comboPanel.add(viewButton, BorderLayout.EAST);

		selectionPanel.add(selectLabel, BorderLayout.NORTH);
		selectionPanel.add(comboPanel, BorderLayout.CENTER);

		// Create a table for displaying assignments
		String[] columnNames = { "Assignment", "Total Points", "Your Grade", "Status" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells non-editable
			}
		};
		JTable assignmentsTable = new JTable(tableModel);
		assignmentsTable.setRowHeight(25);
		assignmentsTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane tableScrollPane = new JScrollPane(assignmentsTable);

		// Add action listener for view button
		viewButton.addActionListener(e -> {
			String selectedCourse = (String) courseComboBox.getSelectedItem();
			if (selectedCourse != null) {
				Course course = findCourse(courses, selectedCourse);
				if (course != null) {
					// Clear previous entries
					tableModel.setRowCount(0);

					// For each assignment in the course, add a row
					for (Assignment a : course.getAssignments()) {
						double grade = student.getAssgGrade(a, course.getName());
						String status = grade > 0 ? "Graded" : "Not Graded";

						Object[] rowData = {
								a.getName(),
								a.getTotalPoints(),
								grade > 0 ? String.format("%.2f", grade) : "-",
								status
						};
						tableModel.addRow(rowData);
					}
				}
			}
		});

		// Add components to the assignments panel
		assignmentsPanel.add(instructionLabel, BorderLayout.NORTH);
		assignmentsPanel.add(selectionPanel, BorderLayout.BEFORE_FIRST_LINE);
		assignmentsPanel.add(tableScrollPane, BorderLayout.CENTER);

		// Update the content panel
		contentPanel.removeAll();
		contentPanel.add(assignmentsPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void showGPAPanel() {
		// Create a panel for calculating GPA
		JPanel gpaPanel = new JPanel(new BorderLayout(10, 10));

		// Create a label for instructions
		JLabel instructionLabel = new JLabel("GPA Calculator", SwingConstants.CENTER);
		instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create a panel for displaying the GPA
		JPanel displayPanel = new JPanel(new BorderLayout(10, 10));

		double gpa = controller.getGPA();

		JLabel gpaLabel = new JLabel("Your current GPA is: " + String.format("%.2f", gpa), SwingConstants.CENTER);
		gpaLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

		// Add some color coding based on GPA
		if (gpa >= 3.5) {
			gpaLabel.setForeground(new Color(0, 150, 0)); // Green for high GPA
		} else if (gpa >= 2.0) {
			gpaLabel.setForeground(new Color(150, 150, 0)); // Yellow for medium GPA
		} else {
			gpaLabel.setForeground(new Color(150, 0, 0)); // Red for low GPA
		}

		displayPanel.add(gpaLabel, BorderLayout.CENTER);

		// Add a button to recalculate GPA
		JButton recalculateButton = new JButton("Recalculate GPA");
		recalculateButton.addActionListener(e -> {
			double updatedGpa = controller.getGPA();
			gpaLabel.setText("Your current GPA is: " + String.format("%.2f", updatedGpa));

			// Update color
			if (updatedGpa >= 3.5) {
				gpaLabel.setForeground(new Color(0, 150, 0));
			} else if (updatedGpa >= 2.0) {
				gpaLabel.setForeground(new Color(150, 150, 0));
			} else {
				gpaLabel.setForeground(new Color(150, 0, 0));
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(recalculateButton);

		// Show both current and completed courses that contribute to GPA
		JPanel coursesPanel = new JPanel(new BorderLayout(10, 10));
		JLabel coursesLabel = new JLabel("Courses included in GPA calculation:", SwingConstants.LEFT);
		coursesPanel.add(coursesLabel, BorderLayout.NORTH);

		StringBuilder coursesSb = new StringBuilder();

		// Add completed courses
		ArrayList<Course> completedCourses = controller.getCompletedCourses();
		if (!completedCourses.isEmpty()) {
			coursesSb.append("Completed Courses:\n");
			for (Course c : completedCourses) {
				double grade = controller.getGradeForCourse(c);
				String letterGrade = controller.getLetterGradeForCourse(c);
				coursesSb.append("- ").append(c.getName()).append(": ")
						.append(String.format("%.2f%%", grade))
						.append(" (").append(letterGrade).append(")\n");
			}
			coursesSb.append("\n");
		}

		JTextArea coursesTextArea = new JTextArea(coursesSb.toString());
		coursesTextArea.setEditable(false);
		coursesPanel.add(new JScrollPane(coursesTextArea), BorderLayout.CENTER);

		// Add components to the GPA panel
		gpaPanel.add(instructionLabel, BorderLayout.NORTH);
		gpaPanel.add(displayPanel, BorderLayout.BEFORE_FIRST_LINE);
		gpaPanel.add(buttonPanel, BorderLayout.CENTER);
		gpaPanel.add(coursesPanel, BorderLayout.SOUTH);

		// Update the content panel
		contentPanel.removeAll();
		contentPanel.add(gpaPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void openCourseDetails(Course course) {
		// Create a detail view that shows assignments and grades for the selected
		// course
		JFrame detailFrame = new JFrame(course.getName() + " Details");
		detailFrame.setSize(600, 400);
		detailFrame.setLocationRelativeTo(frame);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Header with course info
		JLabel headerLabel = new JLabel(course.getName() + " - Current Grade: " +
				String.format("%.2f%% (%s)",
						controller.getGradeForCourse(course),
						controller.getLetterGradeForCourse(course)),
				SwingConstants.CENTER);
		headerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create table for assignments
		String[] columnNames = { "Assignment", "Points", "Your Grade", "Status" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Get assignments for this course
		ArrayList<Assignment> assignments = course.getAssignments();
		for (Assignment assignment : assignments) {
			double grade = student.getAssgGrade(assignment, course.getName());
			String status = grade > 0 ? "Graded" : "Not Graded";

			Object[] rowData = {
					assignment.getName(),
					assignment.getTotalPoints(),
					grade > 0 ? grade : "N/A",
					status
			};
			tableModel.addRow(rowData);
		}

		JTable assignmentsTable = new JTable(tableModel);
		assignmentsTable.setRowHeight(25);
		assignmentsTable.getTableHeader().setReorderingAllowed(false);

		JScrollPane tableScrollPane = new JScrollPane(assignmentsTable);

		// Add components to main panel
		mainPanel.add(headerLabel, BorderLayout.NORTH);
		mainPanel.add(tableScrollPane, BorderLayout.CENTER);

		// Close button at the bottom
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> detailFrame.dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(closeButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Add main panel to frame
		detailFrame.add(mainPanel);

		// Show the frame
		detailFrame.setVisible(true);
	}

	/**
	 * Helper method to find a course by name
	 */
	private Course findCourse(ArrayList<Course> courses, String name) {
		for (Course c : courses) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public void display() {
		// Default to courselist view
		showCourselistPanel();
		frame.setVisible(true);
	}

	public void close() {
		frame.dispose();
	}

	@Override
	public void update() {
		showCourselistPanel();
	}
}