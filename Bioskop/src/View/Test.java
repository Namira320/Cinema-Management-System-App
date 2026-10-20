package View;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import javax.swing.*;
import java.awt.*;
package View;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import Database.DatabaseConnection;

public class Dashboard {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel topMovieLabel, newestMovieLabel, bestMovieLabel;
    private int topMovieIndex = 0, newestMovieIndex = 0, bestMovieIndex = 0;
    private Object[][] topMovies, newestMovies, bestMovies;

    public Dashboard() {
        initializeUI();
        loadMovieData();
        startCarousel();
    }

    private void initializeUI() {
        frame = new JFrame("Cinema INDO");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 40, 60));
        JLabel titleLabel = new JLabel("Cinema INDO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        topMovieLabel = new JLabel("", SwingConstants.CENTER);
        newestMovieLabel = new JLabel("", SwingConstants.CENTER);
        bestMovieLabel = new JLabel("", SwingConstants.CENTER);

        mainPanel.add(createMoviePanel("TOP MOVIES", topMovieLabel));
        mainPanel.add(createMoviePanel("NEWEST MOVIES", newestMovieLabel));
        mainPanel.add(createMoviePanel("BEST MOVIES", bestMovieLabel));

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createMoviePanel(String title, JLabel movieLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(movieLabel, BorderLayout.CENTER);

        return panel;
    }

    private void loadMovieData() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rsTop = stmt.executeQuery("SELECT nama_film, genre, durasi, gambar_path FROM film ORDER BY RAND() LIMIT 5");
            topMovies = extractMovieData(rsTop);

            ResultSet rsNewest = stmt.executeQuery("SELECT nama_film, genre, durasi, gambar_path FROM film ORDER BY film_id DESC LIMIT 5");
            newestMovies = extractMovieData(rsNewest);

            ResultSet rsBest = stmt.executeQuery(
                    "SELECT f.nama_film, f.genre, f.durasi, f.gambar_path " +
                    "FROM transaksi t " +
                    "JOIN jadwal j ON t.jadwal_id = j.jadwal_id " +
                    "JOIN film f ON j.film_id = f.film_id " +
                    "GROUP BY f.film_id " +
                    "ORDER BY COUNT(t.transaksi_id) DESC LIMIT 5"
            );
            bestMovies = extractMovieData(rsBest);

            updateMovieDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Object[][] extractMovieData(ResultSet rs) throws SQLException {
        Object[][] movies = new Object[5][4];
        int index = 0;
        while (rs.next() && index < 5) {
            movies[index][0] = rs.getString("nama_film");
            movies[index][1] = rs.getString("genre");
            movies[index][2] = rs.getInt("durasi");
            movies[index][3] = rs.getString("gambar_path");
            index++;
        }
        return movies;
    }

    private void startCarousel() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateMovieDisplay();
            }
        }, 0, 3000);
    }

    private void updateMovieDisplay() {
        if (topMovies != null && topMovies[topMovieIndex][0] != null) {
            setMovieLabel(topMovieLabel, topMovies[topMovieIndex]);
            topMovieIndex = (topMovieIndex + 1) % topMovies.length;
        }
        if (newestMovies != null && newestMovies[newestMovieIndex][0] != null) {
            setMovieLabel(newestMovieLabel, newestMovies[newestMovieIndex]);
            newestMovieIndex = (newestMovieIndex + 1) % newestMovies.length;
        }
        if (bestMovies != null && bestMovies[bestMovieIndex][0] != null) {
            setMovieLabel(bestMovieLabel, bestMovies[bestMovieIndex]);
            bestMovieIndex = (bestMovieIndex + 1) % bestMovies.length;
        }
    }

     else {
        imageLabel.setText("No Image");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    JLabel textLabel = new JLabel("<html><center><b>" + movieData[0] + "</b><br>" +
            "Genre: " + movieData[1] + "<br>" +
            "Durasi: " + movieData[2] + " menit<br></center></html>", SwingConstants.CENTER);
    textLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    panel.add(imageLabel, BorderLayout.CENTER);
    panel.add(textLabel, BorderLayout.SOUTH);

    label.setIcon(null);
    label.setText(null);
    label.setLayout(new BorderLayout());
    label.add(panel, BorderLayout.CENTER);
} else {
            label.setIcon(null);
        }

        label.setText(movieInfo);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import Database.DatabaseConnection;

public class Dashboard {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel topMovieLabel, newestMovieLabel, bestMovieLabel;
    private int topMovieIndex = 0, newestMovieIndex = 0, bestMovieIndex = 0;
    private Object[][] topMovies, newestMovies, bestMovies;

    public Dashboard() {
        initializeUI();
        loadMovieData();
        startCarousel();
    }

    private void initializeUI() {
        frame = new JFrame("Cinema INDO");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 40, 60));
        JLabel titleLabel = new JLabel("Cinema INDO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        topMovieLabel = new JLabel("", SwingConstants.CENTER);
        newestMovieLabel = new JLabel("", SwingConstants.CENTER);
        bestMovieLabel = new JLabel("", SwingConstants.CENTER);

        mainPanel.add(createMoviePanel("TOP MOVIES", topMovieLabel));
        mainPanel.add(createMoviePanel("NEWEST MOVIES", newestMovieLabel));
        mainPanel.add(createMoviePanel("BEST MOVIES", bestMovieLabel));

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createMoviePanel(String title, JLabel movieLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(movieLabel, BorderLayout.CENTER);

        return panel;
    }

    private void loadMovieData() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            // Load TOP MOVIES (Film dengan rating tertinggi)
            ResultSet rsTop = stmt.executeQuery("SELECT nama_film, genre, durasi, gambar_path FROM film ORDER BY rating DESC LIMIT 5");
            topMovies = extractMovieData(rsTop);

            // Load NEWEST MOVIES (Film terbaru berdasarkan ID)
            ResultSet rsNewest = stmt.executeQuery("SELECT nama_film, genre, durasi, gambar_path FROM film ORDER BY film_id DESC LIMIT 5");
            newestMovies = extractMovieData(rsNewest);

            // Load BEST MOVIES (Film dengan jumlah transaksi terbanyak)
            ResultSet rsBest = stmt.executeQuery(
                    "SELECT f.nama_film, f.genre, f.durasi, f.gambar_path " +
                    "FROM transaksi t " +
                    "JOIN jadwal j ON t.jadwal_id = j.jadwal_id " +
                    "JOIN film f ON j.film_id = f.film_id " +
                    "GROUP BY f.film_id " +
                    "ORDER BY COUNT(t.transaksi_id) DESC LIMIT 5"
            );
            bestMovies = extractMovieData(rsBest);

            updateMovieDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Object[][] extractMovieData(ResultSet rs) throws SQLException {
        Object[][] movies = new Object[5][4]; // 5 film, masing-masing punya 4 data (nama, genre, durasi, gambar)
        int index = 0;
        while (rs.next() && index < 5) {
            movies[index][0] = rs.getString("nama_film");
            movies[index][1] = rs.getString("genre");
            movies[index][2] = rs.getInt("durasi");
            movies[index][3] = rs.getString("gambar_path");
            index++;
        }
        return movies;
    }

    private void startCarousel() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateMovieDisplay();
            }
        }, 0, 3000); // Ganti setiap 3 detik
    }

    private void updateMovieDisplay() {
        if (topMovies != null && topMovies[topMovieIndex][0] != null) {
            setMovieLabel(topMovieLabel, topMovies[topMovieIndex]);
            topMovieIndex = (topMovieIndex + 1) % topMovies.length;
        }
        if (newestMovies != null && newestMovies[newestMovieIndex][0] != null) {
            setMovieLabel(newestMovieLabel, newestMovies[newestMovieIndex]);
            newestMovieIndex = (newestMovieIndex + 1) % newestMovies.length;
        }
        if (bestMovies != null && bestMovies[bestMovieIndex][0] != null) {
            setMovieLabel(bestMovieLabel, bestMovies[bestMovieIndex]);
            bestMovieIndex = (bestMovieIndex + 1) % bestMovies.length;
        }
    }

    private void setMovieLabel(JLabel label, Object[] movieData) {
        String movieInfo = "<html><center><b>" + movieData[0] + "</b><br>" +
                "Genre: " + movieData[1] + "<br>" +
                "Durasi: " + movieData[2] + " menit<br>";

        // Load gambar dari database
        File imageFile = new File((String) movieData[3]);
        if (imageFile.exists()) {
            ImageIcon icon = new ImageIcon(new ImageIcon((String) movieData[3])
                    .getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH));
            label.setIcon(icon);
        } else {
            label.setIcon(null);
        }

        label.setText(movieInfo);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
