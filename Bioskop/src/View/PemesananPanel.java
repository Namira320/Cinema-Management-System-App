/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Database.DatabaseConnection;

/**
 *
 * @author namir
 */
public class PemesananPanel {

    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public PemesananPanel() {
        panel = new JPanel(new BorderLayout());
        initializeUI();
        loadPemesananData();
    }

    private void initializeUI() {
        String[] columnNames = {"ID", "User", "Film", "Jadwal", "Jumlah Tiket", "Total Harga"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Pesan Tiket");
        JButton deleteButton = new JButton("Hapus Pemesanan");

        addButton.addActionListener(e -> showAddPemesananDialog());
        deleteButton.addActionListener(e -> deletePemesanan());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showAddPemesananDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Pesan Tiket", true);
        dialog.setSize(350, 300);
        dialog.setLayout(new GridLayout(6, 2));

        JComboBox<String> userDropdown = new JComboBox<>(getUserList().toArray(new String[0]));
        JComboBox<String> filmDropdown = new JComboBox<>(getFilmList().toArray(new String[0]));
        JComboBox<String> jadwalDropdown = new JComboBox<>();
        JTextField jumlahField = new JTextField();
        JButton saveButton = new JButton("Pesan");

        filmDropdown.addActionListener(e -> {
            String selectedFilm = (String) filmDropdown.getSelectedItem();
            jadwalDropdown.setModel(new DefaultComboBoxModel<>(getJadwalList(selectedFilm).toArray(new String[0])));
        });
        filmDropdown.setSelectedIndex(0);
        jadwalDropdown.setModel(new DefaultComboBoxModel<>(getJadwalList((String) filmDropdown.getSelectedItem()).toArray(new String[0])));

        saveButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO transaksi (akun_id, jadwal_id, jumlah_tiket, total_harga) "
                        + "VALUES ((SELECT akun_id FROM akun WHERE username=?), "
                        + "(SELECT jadwal_id FROM jadwal WHERE CONCAT(tanggal, ' ', jam) = ?), ?, "
                        + "((SELECT harga FROM film WHERE film_id = (SELECT film_id FROM jadwal WHERE jadwal_id = "
                        + "(SELECT jadwal_id FROM jadwal WHERE CONCAT(tanggal, ' ', jam) = ?))) * ?))"
                );
                stmt.setString(1, (String) userDropdown.getSelectedItem());
                stmt.setString(2, (String) jadwalDropdown.getSelectedItem());
                stmt.setInt(3, Integer.parseInt(jumlahField.getText()));
                stmt.setString(4, (String) jadwalDropdown.getSelectedItem());
                stmt.setInt(5, Integer.parseInt(jumlahField.getText()));
                stmt.executeUpdate();
                dialog.dispose();
                loadPemesananData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        dialog.add(new JLabel("User:"));
        dialog.add(userDropdown);
        dialog.add(new JLabel("Film:"));
        dialog.add(filmDropdown);
        dialog.add(new JLabel("Jadwal:"));
        dialog.add(jadwalDropdown);
        dialog.add(new JLabel("Jumlah Tiket:"));
        dialog.add(jumlahField);
        dialog.add(new JLabel());
        dialog.add(saveButton);
        dialog.setVisible(true);
    }

    private void loadPemesananData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT t.transaksi_id, a.username, f.nama_film, j.tanggal, t.jumlah_tiket, t.total_harga "
                    + "FROM transaksi t "
                    + "JOIN akun a ON t.akun_id = a.akun_id "
                    + "JOIN jadwal j ON t.jadwal_id = j.jadwal_id "
                    + "JOIN film f ON j.film_id = f.film_id"
            );
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("transaksi_id"),
                    rs.getString("username"),
                    rs.getString("nama_film"),
                    rs.getString("tanggal"),
                    rs.getInt("jumlah_tiket"),
                    rs.getInt("total_harga")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<String> getUserList() {
        List<String> userList = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM akun");
            while (rs.next()) {
                userList.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private List<String> getFilmList() {
        List<String> filmList = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nama_film FROM film");
            while (rs.next()) {
                filmList.add(rs.getString("nama_film"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmList;
    }

    private List<String> getJadwalList(String film) {
        List<String> jadwalList = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT CONCAT(tanggal, ' ', jam) AS jadwal FROM jadwal WHERE film_id = (SELECT film_id FROM film WHERE nama_film=?)");
            stmt.setString(1, film);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jadwalList.add(rs.getString("jadwal"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jadwalList;
    }

    private void deletePemesanan() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Pilih pemesanan yang ingin dihapus!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int transaksiId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM transaksi WHERE transaksi_id=?");
            stmt.setInt(1, transaksiId);
            stmt.executeUpdate();
            loadPemesananData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
