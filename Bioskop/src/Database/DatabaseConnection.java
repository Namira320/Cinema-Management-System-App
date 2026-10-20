/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author namir
 */
public class DatabaseConnection {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bioskop", "root", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
