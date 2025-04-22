package view;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.BorderLayout;

import controller.MainController;

public class MainView {
    private MainController controller;
    private JFrame frame;
    
    public MainView(MainController controller) {
        this.controller = controller;
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Select Role");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel prompt = new JLabel("Are you a student or teacher?", SwingConstants.CENTER);
        JButton studentButton = new JButton("Student");
        JButton teacherButton = new JButton("Teacher");

        studentButton.addActionListener(e -> controller.loginAsStudent());
        teacherButton.addActionListener(e -> controller.loginAsTeacher());

        panel.add(prompt);
        panel.add(studentButton);
        panel.add(teacherButton);

        frame.add(panel);
    }
    
    public void display() {
        frame.setVisible(true);
    }
    
    public void close() {
        frame.dispose();
    }
}