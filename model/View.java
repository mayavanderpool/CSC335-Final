package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
	private static AccountManager aManager;

	public static void main(String[] args) {
		aManager = new AccountManager();
		createInitialPrompt();
	}

	public static void createInitialPrompt() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Welcome");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 250);
			frame.setLocationRelativeTo(null);

			JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			JLabel promptLabel = new JLabel("Please enter your first and last name:", SwingConstants.CENTER);
			JPanel fnamePanel = new JPanel(new BorderLayout());
			JLabel fnameLabel = new JLabel("First Name:");
			JTextField fnameField = new JTextField();
			fnamePanel.add(fnameLabel, BorderLayout.NORTH);
			fnamePanel.add(fnameField, BorderLayout.CENTER);

			JPanel lnamePanel = new JPanel(new BorderLayout());
			JLabel lnameLabel = new JLabel("Last Name:");
			JTextField lnameField = new JTextField();
			lnamePanel.add(lnameLabel, BorderLayout.NORTH);
			lnamePanel.add(lnameField, BorderLayout.CENTER);

			JButton enterButton = new JButton("Enter");

			panel.add(promptLabel);
			panel.add(fnamePanel);
			panel.add(lnamePanel);
			panel.add(new JLabel());
			panel.add(enterButton);

			frame.add(panel);
			frame.setVisible(true);

			enterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String fname = fnameField.getText().trim();
					String lname = lnameField.getText().trim();

					if (!aManager.personExists(fname, lname)) {
						JOptionPane.showMessageDialog(frame, "Person not found in system.");
						return;
					}

					frame.dispose();
					if (aManager.accountExists(fname, lname)) {
						showLoginForm(fname, lname);
					} else {
						showCreateAccountForm(fname, lname);
					}
				}
			});
		});
	}

	private static void showLoginForm(String fname, String lname) {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel userPanel = new JPanel(new BorderLayout());
		JLabel userLabel = new JLabel("Username:");
		JTextField usernameField = new JTextField();
		userPanel.add(userLabel, BorderLayout.NORTH);
		userPanel.add(usernameField, BorderLayout.CENTER);

		JPanel passPanel = new JPanel(new BorderLayout());
		JLabel passLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField();
		passPanel.add(passLabel, BorderLayout.NORTH);
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
			} else {
				JOptionPane.showMessageDialog(frame, "Invalid username or password.");
			}
		});
	}

	private static void showCreateAccountForm(String fname, String lname) {
		JFrame frame = new JFrame("Create Account");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel userPanel = new JPanel(new BorderLayout());
		JLabel userLabel = new JLabel("Create Username:");
		JTextField usernameField = new JTextField();
		userPanel.add(userLabel, BorderLayout.NORTH);
		userPanel.add(usernameField, BorderLayout.CENTER);

		JPanel passPanel = new JPanel(new BorderLayout());
		JLabel passLabel = new JLabel("Create Password:");
		JPasswordField passwordField = new JPasswordField();
		passPanel.add(passLabel, BorderLayout.NORTH);
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
		});
	}
}
