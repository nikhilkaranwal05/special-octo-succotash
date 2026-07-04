package com.school;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class StudentPanel extends JPanel {
    private JTextField tfId = new JTextField();
    private JTextField tfName = new JTextField();
    private JTextField tfEmail = new JTextField();
    private JTextField tfPhone = new JTextField();
    private JTextField tfStandard = new JTextField();

    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone", "Standard"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private JTable table = new JTable(model);
    private StudentDAO dao = new StudentDAO();

    public StudentPanel() {
        setLayout(new BorderLayout(12, 12));

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        tfId.setEditable(false);
        String[][] labels = {
            {"ID", "tfId"},
            {"Name", "tfName"},
            {"Email", "tfEmail"},
            {"Phone", "tfPhone"},
            {"Standard", "tfStandard"}
        };
        JComponent[] fields = {tfId, tfName, tfEmail, tfPhone, tfStandard};
        for (int i = 0; i < labels.length; i++) {
            g.gridx = 0; g.gridy = row; g.weightx = 0;
            form.add(new JLabel(labels[i][0]), g);
            g.gridx = 1; g.weightx = 1;
            form.add(fields[i], g);
            row++;
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnRefresh = new JButton("Refresh");
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);
        buttons.add(btnRefresh);

        g.gridx = 0; g.gridy = row; g.gridwidth = 2;
        form.add(buttons, g);

        add(form, BorderLayout.NORTH);

        // Table
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        // Events
        btnAdd.addActionListener(this::onAdd);
        btnUpdate.addActionListener(this::onUpdate);
        btnDelete.addActionListener(this::onDelete);
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> reload());
        table.getSelectionModel().addListSelectionListener(e -> onTableSelect());

        reload();
    }

    private void onAdd(ActionEvent e) {
        try {
            Student s = new Student(
                tfName.getText().trim(),
                tfEmail.getText().trim(),
                tfPhone.getText().trim(),
                tfStandard.getText().trim()
            );
            if (s.getName().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required");
                return;
            }
            dao.insert(s);
            reload();
            clearForm();
            JOptionPane.showMessageDialog(this, "Student added");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void onUpdate(ActionEvent e) {
        try {
            if (tfId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a row to update");
                return;
            }
            Student s = new Student(
                Integer.parseInt(tfId.getText()),
                tfName.getText().trim(),
                tfEmail.getText().trim(),
                tfPhone.getText().trim(),
                tfStandard.getText().trim()
            );
            dao.update(s);
            reload();
            JOptionPane.showMessageDialog(this, "Student updated");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void onDelete(ActionEvent e) {
        try {
            if (tfId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a row to delete");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            int ok = JOptionPane.showConfirmDialog(this, "Delete student ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                dao.delete(id);
                reload();
                clearForm();
                JOptionPane.showMessageDialog(this, "Deleted");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void onTableSelect() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            tfId.setText(String.valueOf(model.getValueAt(row, 0)));
            tfName.setText(String.valueOf(model.getValueAt(row, 1)));
            tfEmail.setText(String.valueOf(model.getValueAt(row, 2)));
            tfPhone.setText(String.valueOf(model.getValueAt(row, 3)));
            tfStandard.setText(String.valueOf(model.getValueAt(row, 4)));
        }
    }

    private void clearForm() {
        tfId.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
        tfStandard.setText("");
        table.clearSelection();
    }

    private void reload() {
        try {
            List<Student> list = dao.findAll();
            model.setRowCount(0);
            for (Student s : list) {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getPhone(), s.getStandard()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
