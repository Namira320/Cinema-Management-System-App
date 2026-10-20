/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author namir
 */

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import Database.DatabaseConnection;

public class RegisterPanel {
    public RegisterPanel(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Sign Up", true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2));

        dialog.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        dialog.add(usernameField);

        dialog.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        dialog.add(passwordField);

        JButton registerBtn = new JButton("Sign Up");
        registerBtn.addActionListener(e -> registerUser(usernameField.getText(), new String(passwordField.getPassword()), dialog));
        dialog.add(registerBtn);

        dialog.setVisible(true);
    }

    private void registerUser(String username, String password, JDialog dialog) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO akun (username, password, role) VALUES (?, ?, 'user')");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(dialog, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

