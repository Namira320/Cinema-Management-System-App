/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import Database.DatabaseConnection;
import Models.User;

/**
 *
 * @author namir
 */
public class LoginPanel {

    private JFrame parentFrame;

    public LoginPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        showLoginUI();
    }

    private void showLoginUI() {
        JDialog dialog = new JDialog(parentFrame, "Login", true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2));

        dialog.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        dialog.add(usernameField);

        dialog.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        dialog.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> loginUser(usernameField.getText(), new String(passwordField.getPassword()), dialog));
        dialog.add(loginBtn);

        dialog.setVisible(true);
    }

    private void loginUser(String username, String password, JDialog dialog) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM akun WHERE username=? AND password=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                dialog.dispose();

                // ** Perbaikan: Buat objek User dulu **
                User user = new User(username, role);

                if (role.equals("admin")) {
                    new AdminPanel(parentFrame);
                } else {
                    // ** Perbaikan: Gunakan objek User untuk UserPanel **
                    UserPanel userPanel = new UserPanel(user);
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(userPanel.getPanel());
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
