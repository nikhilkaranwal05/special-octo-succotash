package com.school;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            JFrame f = new JFrame("School Management — Students");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(900, 600);
            f.setLocationRelativeTo(null);
            f.setLayout(new BorderLayout());
            f.add(new StudentPanel(), BorderLayout.CENTER);
            f.setVisible(true);
        });
    }
}
