/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Database.DatabaseConnection;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author namir
 */


public class FilmPanel {
    private JPanel panel;
    private JTable filmTable;
    private DefaultTableModel filmModel;
    private String selectedImagePath = "";

    public FilmPanel() {
        panel = new JPanel(new BorderLayout());
        filmModel = new DefaultTableModel(new String[]{"ID", "Nama Film", "Genre", "Durasi", "Harga"}, 0);
        filmTable = new JTable(filmModel);
        loadFilmData();

        JButton addFilmBtn = new JButton("Tambah Film");
        addFilmBtn.addActionListener(e -> showAddFilmDialog());

        JButton deleteFilmBtn = new JButton("Hapus Film");
        deleteFilmBtn.addActionListener(e -> deleteSelectedFilm());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addFilmBtn);
        buttonPanel.add(deleteFilmBtn);

        panel.add(new JScrollPane(filmTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadFilmData() {
        filmModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM film");
            while (rs.next()) {
                filmModel.addRow(new Object[]{rs.getInt("film_id"), rs.getString("nama_film"), rs.getString("genre"), rs.getInt("durasi"), rs.getInt("harga")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAddFilmDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Tambah Film", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(6, 2));

        JTextField nameField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField priceField = new JTextField();
        JButton uploadButton = new JButton("Upload Gambar");

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                selectedImagePath = "uploads/" + file.getName();
                try {
                    Files.copy(file.toPath(), new File(selectedImagePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO film (nama_film, genre, durasi, harga, gambar_path) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, nameField.getText());
                stmt.setString(2, genreField.getText());
                stmt.setInt(3, Integer.parseInt(durationField.getText()));
                stmt.setInt(4, Integer.parseInt(priceField.getText()));
                stmt.setString(5, selectedImagePath);
                stmt.executeUpdate();
                dialog.dispose();
                loadFilmData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        dialog.add(new JLabel("Nama Film:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Genre:"));
        dialog.add(genreField);
        dialog.add(new JLabel("Durasi (menit):"));
        dialog.add(durationField);
        dialog.add(new JLabel("Harga:"));
        dialog.add(priceField);
        dialog.add(uploadButton);
        dialog.add(saveButton);

        dialog.setVisible(true);
    }

    private void deleteSelectedFilm() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Pilih film yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int filmId = (int) filmModel.getValueAt(selectedRow, 0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM film WHERE film_id=?");
            stmt.setInt(1, filmId);
            stmt.executeUpdate();
            loadFilmData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

