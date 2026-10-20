/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import javax.swing.*;
import java.awt.*;

/**
 *
 * @author namir
 */
public class AdminPanel {
    private JFrame parentFrame;

    public AdminPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        showAdminUI();
    }

    private void showAdminUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        // Tabbed Panel untuk navigasi
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Kelola Film", new FilmPanel().getPanel());
        tabbedPane.add("Kelola Jadwal", new JadwalPanel().getPanel());
        tabbedPane.add("Pemesanan Tiket", new PemesananPanel().getPanel());

        panel.add(tabbedPane, BorderLayout.CENTER);

        parentFrame.getContentPane().removeAll();
        parentFrame.add(panel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

