package view;

import controller.TeacherController;
import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CourseView {
	private TeacherController controller;
	private Teacher teacher;
	private Course course;
	private JFrame frame;
	private JPanel contentPanel;

	public CourseView(TeacherController controller, Teacher teacher, Course course) {
		this.controller = controller;
		this.teacher = teacher;
		this.course = course;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Course: " + course.getName());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);

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
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Add action listener for complete button
		completeButton.addActionListener(e -> {
			controller.markCourseAsCompleted(course);
			completeButton.setText("Course Completed");
			completeButton.setEnabled(false);
			completeButton.setBackground(new Color(200, 255, 200)); // Light green
		});

		// Add action listeners for header buttons
		gradesButton.addActionListener(e -> showGradesPanel());
		classlistButton.addActionListener(e -> showClasslistPanel());
		assignmentsButton.addActionListener(e -> showAssignmentsPanel());
		groupsButton.addActionListener(e -> showGroupsPanel());

		// Add components to main panel
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(courseTitle, BorderLayout.NORTH);
		topPanel.add(headerPanel, BorderLayout.SOUTH);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Add a back button at the bottom
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton backButton = new JButton("Back to Dashboard");
		backButton.addActionListener(e -> frame.dispose());
		bottomPanel.add(backButton);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		frame.add(mainPanel);
	}

	public void display() {
		// Default to classlist view
		showClasslistPanel();
		frame.setVisible(true);
	}

	private void showClasslistPanel() {
		// Create a panel with BorderLayout
		JPanel classlistPanel = new JPanel(new BorderLayout(10, 10));

		// Create a label for instructions
		JLabel instructionLabel = new JLabel("Class List for " + course.getName(), SwingConstants.CENTER);
		instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create a table model for students
		String[] columnNames = { "Student", "Username", "Course Average" };
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
			double grade = student.getGrade(course); // Get student's grade for this course
			Object[] rowData = {
					student.getFirstName() + " " + student.getLastName(),
					student.getUserName(),
					String.format("%.2f%%", grade)
			};
			tableModel.addRow(rowData);
		}

		// Create the table and add it to a scroll pane
		JTable studentsTable = new JTable(tableModel);
		studentsTable.setRowHeight(25);
		studentsTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane tableScrollPane = new JScrollPane(studentsTable);

		// ---- Sorting Controls ----
		JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel sortLabel = new JLabel("Sort by:");
		String[] sortOptions = { "First Name", "Last Name", "Username" };
		JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
		JButton sortButton = new JButton("Sort");

		sortPanel.add(sortLabel);
		sortPanel.add(sortComboBox);
		sortPanel.add(sortButton);

		sortButton.addActionListener(e -> {
			String selected = (String) sortComboBox.getSelectedItem();
			switch (selected) {
				case "First Name":
					controller.sortByFirst(course.getName());
					break;
				case "Last Name":
					controller.sortByLast(course.getName());
					break;
				case "Username":
					controller.sortByUser(course.getName());
					break;
			}
			showClasslistPanel(); // Re-render the panel after sorting
		});

		// ---- Add/Remove/Import Buttons ----
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton addStudentButton = new JButton("Add Student");
		JButton removeStudentButton = new JButton("Remove Student");
		JButton importStudentsButton = new JButton("Import Students");

		addStudentButton.addActionListener(e -> {
			String firstName = JOptionPane.showInputDialog(contentPanel, "Enter student's first name:");
			if (firstName != null && !firstName.trim().isEmpty()) {
				String lastName = JOptionPane.showInputDialog(contentPanel, "Enter student's last name:");
				if (lastName != null && !lastName.trim().isEmpty()) {
					controller.addStudentToCourse(course, firstName, lastName);
					showClasslistPanel();
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

				controller.removeStudentFromCourse(course, firstName, lastName);
				showClasslistPanel();
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
				controller.importStudents(selectedFile, course);
				showClasslistPanel();
			}
		});

		buttonPanel.add(addStudentButton);
		buttonPanel.add(removeStudentButton);
		buttonPanel.add(importStudentsButton);

		// Add components to the classlist panel
		classlistPanel.add(instructionLabel, BorderLayout.NORTH);
		classlistPanel.add(sortPanel, BorderLayout.BEFORE_FIRST_LINE);
		classlistPanel.add(tableScrollPane, BorderLayout.CENTER);
		classlistPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Update the content panel
		contentPanel.removeAll();
		contentPanel.add(classlistPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void showGradesPanel() {
		// Main container
		JPanel gradesPanel = new JPanel(new BorderLayout(10, 10));
	
		// Top label
		JLabel instructionLabel = new JLabel("Assignment Grades Overview", SwingConstants.CENTER);
		instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
	
		// Split pane for assignments (top) and student grades (bottom)
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.4);
	
		// --- TOP PANEL: Assignments table ---
		JPanel topPanel = new JPanel(new BorderLayout(5, 5));
		String[] assignCols = { "Assignment", "Total Points", "Average", "Median", "Completed/Total" };
		DefaultTableModel assignModel = new DefaultTableModel(assignCols, 0) {
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};
		for (Assignment a : course.getAssignments()) {
			assignModel.addRow(new Object[]{
				a.getName(),
				a.getTotalPoints(),
				controller.getAssignmentClassAverage(course, a),
				controller.getAssignmentMedian(course, a),
				controller.getCompletedData(course, a)
			});
		}
		JTable assignTable = new JTable(assignModel);
		assignTable.setRowHeight(25);
		assignTable.getTableHeader().setReorderingAllowed(false);
		topPanel.add(new JScrollPane(assignTable), BorderLayout.CENTER);
	
		// --- BOTTOM PANEL: Student grades table ---
		JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
		JLabel studentLabel = new JLabel("Select an assignment to view student grades", SwingConstants.CENTER);
		studentLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
		bottomPanel.add(studentLabel, BorderLayout.NORTH);
	
		String[] studentCols = { "Student", "Grade", "Status" };
		DefaultTableModel studentModel = new DefaultTableModel(studentCols, 0) {
			@Override public boolean isCellEditable(int r, int c) { return c == 1; } 
		};
		JTable studentTable = new JTable(studentModel);
		studentTable.setRowHeight(25);
		studentTable.getTableHeader().setReorderingAllowed(false);
		bottomPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
	
		// When an assignment is selected, populate the student table **and** attach the listener
		assignTable.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selRow = assignTable.getSelectedRow();
				if (selRow < 0) return;
	
				// Update the label
				String assnName = (String) assignModel.getValueAt(selRow, 0);
				studentLabel.setText("Student Grades for: " + assnName);
	
				// Find the Assignment object
				Assignment selAssignment = findAssignment(course.getAssignments(), assnName);
	
				// Clear and repopulate
				studentModel.setRowCount(0);
				for (Student s : course.getStudents().getStudents()) {
					double g = s.getAssgGrade(selAssignment, course.getName());
					boolean graded = g > 0;
					studentModel.addRow(new Object[]{
						s.getFirstName() + " " + s.getLastName(),
						graded ? g : "",
						graded ? "Graded" : "Not Graded"
					});
				}
	
				// Remove any existing listeners to avoid duplicates
				for (TableModelListener l : studentModel.getTableModelListeners()) {
					if (l instanceof TableModelListener) {
						studentModel.removeTableModelListener(l);
					}
				}
	
				// Attach a fresh listener that only updates the model in place
				studentModel.addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent evt) {
						if (evt.getType() == TableModelEvent.UPDATE && evt.getColumn() == 1) {
							int row = evt.getFirstRow();
							Object val = studentModel.getValueAt(row, 1);
							if (val == null || val.toString().isEmpty()) return;
							try {
								double newGrade = Double.parseDouble(val.toString());
								// Update status cell
								studentModel.setValueAt("Graded", row, 2);
								// Persist via controller
								String fullName = (String) studentModel.getValueAt(row, 0);
								String[] parts = fullName.split(" ");
								controller.addAssignmentGrade(parts[0], parts[1], course.getName(), selAssignment, newGrade);
							} catch (NumberFormatException ex) {
								// invalid input—ignore or show error
							}
						}
					}
				});
			}
		});
	
		// Assemble panels
		splitPane.setTopComponent(topPanel);
		splitPane.setBottomComponent(bottomPanel);
		gradesPanel.add(instructionLabel, BorderLayout.NORTH);
		gradesPanel.add(splitPane, BorderLayout.CENTER);
	
		// Swap into view
		contentPanel.removeAll();
		contentPanel.add(gradesPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	

	private void showGroupsPanel() {
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
		resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		resultsArea.setLineWrap(true);
		resultsArea.setWrapStyleWord(true);

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
					result = controller.makeXGroups(course.getName(), number);
				} else {
					result = controller.makeGroupsOfXStudents(course.getName(), number);
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
		// Create a center panel to hold both options and results
		JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

		// Put options at the top of the center panel
		centerPanel.add(optionsPanel, BorderLayout.NORTH);

		// Put results in center so it grows dynamically
		centerPanel.add(resultsScrollPane, BorderLayout.CENTER);

		// Add main pieces to groups panel
		groupsPanel.add(instructionLabel, BorderLayout.NORTH);
		groupsPanel.add(centerPanel, BorderLayout.CENTER);

		// Update the content panel
		contentPanel.removeAll();
		contentPanel.add(groupsPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void showAssignmentsPanel() {
		// Create a panel with BorderLayout
		JPanel assignmentsPanel = new JPanel(new BorderLayout(10, 10));

		// Create a label for instructions
		JLabel instructionLabel = new JLabel("Assignments for " + course.getName(), SwingConstants.CENTER);
		instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create a table model for assignments with statistics
		String[] columnNames = { "Assignment", "Total Points", "Average", "Median", "Completed" };
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
			// Calculate statistics using controller methods
			String avgDisplay = "0.0";
			String medianDisplay = "";

			try {
				// Get average grade using controller method
				double avgGrade = controller.getAssignmentClassAverage(course, assignment);
				if (!Double.isNaN(avgGrade)) {
					avgDisplay = String.format("%.1f", avgGrade);
				}

				// Get median grade using controller method
				String medianGrade = controller.getAssignmentMedian(course, assignment);
				if (!medianGrade.equals("-1")) {
					medianDisplay = medianGrade;
				}
			} catch (Exception ex) {
				// Handle any exceptions during calculation
			}

			Object[] rowData = {
					assignment.getName(),
					assignment.getTotalPoints(),
					avgDisplay,
					medianDisplay,
					controller.getCompletedData(course, assignment)
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

					// Use controller to add the assignment
					controller.addAssignment(course, name, points);

					// Refresh the assignments panel
					showAssignmentsPanel();
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
					// Use controller to remove the assignment
					controller.removeAssignment(course, selectedAssignment);

					// Refresh the assignments panel
					showAssignmentsPanel();
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

		// Update the content panel
		contentPanel.removeAll();
		contentPanel.add(assignmentsPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void importStudentsFromFile(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int count = 0;

			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 2) {
					String lastName = parts[0].trim();
					String firstName = parts[1].trim();

					// Use controller to add student from file
					controller.addStudentToCourse(course, lastName, firstName);
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
	private Assignment findAssignment(ArrayList<Assignment> assignments, String name) {
		for (Assignment a : assignments) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Custom TableModelListener for handling grade entry and saving
	 */
	private class GradeEntryListener implements TableModelListener {
		private TeacherController controller;
		private String courseName;
		private Assignment assignment;
		private DefaultTableModel tableModel;
		private ArrayList<Student> studentList;

		public GradeEntryListener(TeacherController controller, String courseName, Assignment assignment,
				DefaultTableModel tableModel, ArrayList<Student> studentList) {
			this.controller = controller;
			this.courseName = courseName;
			this.assignment = assignment;
			this.tableModel = tableModel;
			this.studentList = studentList;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			// Only respond to edits in the “Grade” column (index 1)
			if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 1) {
				int row = e.getFirstRow();

				// 1) Read the new value from the model
				Object gradeObj = tableModel.getValueAt(row, 1);
				if (gradeObj == null || gradeObj.toString().isEmpty()) {
					return;
				}

				// 2) Parse and persist
				try {
					double grade = Double.parseDouble(gradeObj.toString());

					// 3) Update the “Status” column (index 2) in the same model
					tableModel.setValueAt("Graded", row, 2);

					// 4) Figure out which student this is
					String fullName = (String) tableModel.getValueAt(row, 0);
					String[] parts = fullName.split(" ");
					String firstName = parts[0];
					String lastName = parts.length > 1 ? parts[1] : "";

					// 5) Delegate to controller to save in your data model
					controller.addAssignmentGrade(firstName, lastName, courseName, assignment, grade);

				} catch (NumberFormatException ex) {
					// Optional: show a dialog or revert cell
				}
			}
		}

	}
}
