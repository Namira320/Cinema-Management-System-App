/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import Database.DatabaseConnection;
import Models.User;

/**
 *
 * @author namir
 */
public class UserPanel {

    private JPanel panel;
    private JTable filmTable, historyTable;
    private DefaultTableModel filmModel, historyModel;
    private User currentUser;

    public UserPanel(User user) {
        this.currentUser = user;
        panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("User Dashboard - Selamat datang, " + user.getUsername(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        // Tabbed Panel
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Daftar Film", createFilmPanel());
        tabbedPane.add("History Pembelian", createHistoryPanel());

        panel.add(tabbedPane, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    // ** Melihat daftar film **
    private JPanel createFilmPanel() {
        JPanel filmPanel = new JPanel(new BorderLayout());

        filmModel = new DefaultTableModel(new String[]{"Nama Film", "Genre", "Durasi (Menit)", "Harga"}, 0);
        filmTable = new JTable(filmModel);
        loadFilmData();

        filmPanel.add(new JScrollPane(filmTable), BorderLayout.CENTER);
        return filmPanel;
    }

    private void loadFilmData() {
        filmModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nama_film, genre, durasi, harga FROM film");
            while (rs.next()) {
                filmModel.addRow(new Object[]{
                    rs.getString("nama_film"),
                    rs.getString("genre"),
                    rs.getInt("durasi"),
                    rs.getInt("harga")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ** Melihat History Pembelian Tiket **
    private JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());

        historyModel = new DefaultTableModel(new String[]{"Film", "Tanggal", "Jam", "Jumlah Tiket", "Total Harga"}, 0);
        historyTable = new JTable(historyModel);
        loadHistoryData();

        historyPanel.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        return historyPanel;
    }

    private void loadHistoryData() {
        historyModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT f.nama_film, j.tanggal, j.jam, t.jumlah_tiket, t.total_harga "
                    + "FROM transaksi t "
                    + "JOIN jadwal j ON t.jadwal_id = j.jadwal_id "
                    + "JOIN film f ON j.film_id = f.film_id "
                    + "WHERE t.akun_id = (SELECT akun_id FROM akun WHERE username = ?)"
            );
            stmt.setString(1, currentUser.getUsername());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                historyModel.addRow(new Object[]{
                    rs.getString("nama_film"),
                    rs.getDate("tanggal"),
                    rs.getTime("jam"),
                    rs.getInt("jumlah_tiket"),
                    rs.getInt("total_harga")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
