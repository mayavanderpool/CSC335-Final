package view;

import javax.swing.*;
import java.awt.*;

import controller.LoginController;

public class LoginView {
    private LoginController controller;
    private boolean isStudent;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public LoginView(LoginController controller, boolean isStudent) {
        this.controller = controller;
        this.isStudent = isStudent;
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame(isStudent ? "Student Access" : "Teacher Access");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        
        // Create a card layout to switch between login and signup forms
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Add option panel (first screen)
        JPanel optionsPanel = createOptionsPanel();
        cardPanel.add(optionsPanel, "OPTIONS");
        
        // Add login panel
        JPanel loginPanel = createLoginPanel();
        cardPanel.add(loginPanel, "LOGIN");
        
        // Add signup panel
        JPanel signupPanel = createSignupPanel();
        cardPanel.add(signupPanel, "SIGNUP");
        
        frame.add(cardPanel);
    }
    
    private JPanel createOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel prompt = new JLabel("What would you like to do?", SwingConstants.CENTER);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Create Account");

        loginButton.addActionListener(e -> showLoginForm());
        signupButton.addActionListener(e -> showSignupForm());

        panel.add(prompt);
        panel.add(loginButton);
        panel.add(signupButton);
        
        return panel;
    }
    
    private JPanel createLoginPanel() {
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

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            controller.login(username, password);
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "OPTIONS"));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);

        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(new JLabel());
        panel.add(buttonPanel);
        
        return panel;
    }
    
    private JPanel createSignupPanel() {
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

        signupButton.addActionListener(e -> {
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (fname.isEmpty() || lname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showError("All fields are required.");
                return;
            }

            controller.createAccount(fname, lname, username, password);
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "OPTIONS"));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(signupButton);

        panel.add(fnamePanel);
        panel.add(lnamePanel);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(new JLabel());
        panel.add(buttonPanel);
        
        return panel;
    }
    
    public void showLoginForm() {
        cardLayout.show(cardPanel, "LOGIN");
    }
    
    public void showSignupForm() {
        cardLayout.show(cardPanel, "SIGNUP");
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void display() {
        frame.setVisible(true);
    }
    
    public void close() {
        frame.dispose();
    }
}