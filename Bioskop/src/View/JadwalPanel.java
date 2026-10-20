/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import Database.DatabaseConnection;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author namir
 */
public class JadwalPanel {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public JadwalPanel() {
        panel = new JPanel(new BorderLayout());
        initializeUI();
        loadJadwalData();
    }

    private void initializeUI() {
        String[] columnNames = {"ID", "Film", "Tanggal", "Jam"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah Jadwal");
        JButton editButton = new JButton("Edit Jadwal");
        JButton deleteButton = new JButton("Hapus Jadwal");

        addButton.addActionListener(e -> showAddJadwalDialog());
        editButton.addActionListener(e -> showEditJadwalDialog());
        deleteButton.addActionListener(e -> deleteJadwal());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadJadwalData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT j.jadwal_id, f.nama_film, j.tanggal, j.jam FROM jadwal j JOIN film f ON j.film_id = f.film_id");
            while (rs.next()) {
                int id = rs.getInt("jadwal_id");
                String film = rs.getString("nama_film");
                String tanggal = rs.getString("tanggal");
                String jam = rs.getString("jam");
                tableModel.addRow(new Object[]{id, film, tanggal, jam});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showEditJadwalDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Pilih jadwal yang ingin diedit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jadwalId = (int) tableModel.getValueAt(selectedRow, 0);
        String film = (String) tableModel.getValueAt(selectedRow, 1);
        String tanggal = (String) tableModel.getValueAt(selectedRow, 2);
        String jam = (String) tableModel.getValueAt(selectedRow, 3);

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Edit Jadwal", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(4, 2));

        JComboBox<String> filmDropdown = new JComboBox<>(getFilmList().toArray(new String[0]));
        filmDropdown.setSelectedItem(film);
        JSpinner tanggalSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner jamSpinner = new JSpinner(new SpinnerDateModel());

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(tanggalSpinner, "yyyy-MM-dd");
        tanggalSpinner.setEditor(dateEditor);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(jamSpinner, "HH:mm:ss");
        jamSpinner.setEditor(timeEditor);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE jadwal SET film_id=(SELECT film_id FROM film WHERE nama_film=?), tanggal=?, jam=? WHERE jadwal_id=?");
                stmt.setString(1, (String) filmDropdown.getSelectedItem());
                stmt.setString(2, dateEditor.getFormat().format(tanggalSpinner.getValue()));
                stmt.setString(3, timeEditor.getFormat().format(jamSpinner.getValue()));
                stmt.setInt(4, jadwalId);
                stmt.executeUpdate();
                dialog.dispose();
                loadJadwalData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        dialog.add(new JLabel("Film:"));
        dialog.add(filmDropdown);
        dialog.add(new JLabel("Tanggal:"));
        dialog.add(tanggalSpinner);
        dialog.add(new JLabel("Jam:"));
        dialog.add(jamSpinner);
        dialog.add(new JLabel());
        dialog.add(updateButton);
        dialog.setVisible(true);
    }

    private void deleteJadwal() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Pilih jadwal yang ingin dihapus!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int jadwalId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM jadwal WHERE jadwal_id=?");
            stmt.setInt(1, jadwalId);
            stmt.executeUpdate();
            loadJadwalData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAddJadwalDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Tambah Jadwal", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(4, 2));

        JComboBox<String> filmDropdown = new JComboBox<>(getFilmList().toArray(new String[0]));
        JSpinner tanggalSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner jamSpinner = new JSpinner(new SpinnerDateModel());

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(tanggalSpinner, "yyyy-MM-dd");
        tanggalSpinner.setEditor(dateEditor);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(jamSpinner, "HH:mm:ss");
        jamSpinner.setEditor(timeEditor);

        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO jadwal (film_id, tanggal, jam) VALUES ((SELECT film_id FROM film WHERE nama_film=?), ?, ?)");
                stmt.setString(1, (String) filmDropdown.getSelectedItem());
                stmt.setString(2, dateEditor.getFormat().format(tanggalSpinner.getValue()));
                stmt.setString(3, timeEditor.getFormat().format(jamSpinner.getValue()));
                stmt.executeUpdate();
                dialog.dispose();
                loadJadwalData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        dialog.add(new JLabel("Film:"));
        dialog.add(filmDropdown);
        dialog.add(new JLabel("Tanggal:"));
        dialog.add(tanggalSpinner);
        dialog.add(new JLabel("Jam:"));
        dialog.add(jamSpinner);
        dialog.add(new JLabel());
        dialog.add(saveButton);
        dialog.setVisible(true);
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

    public JPanel getPanel() {
        return panel;
    }
}
