package com.school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void insert(Student s) throws SQLException {
        String sql = "INSERT INTO students(name, email, phone, standard) VALUES (?, ?, ?, ?)";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getStandard());
            ps.executeUpdate();
        }
    }

    public void update(Student s) throws SQLException {
        String sql = "UPDATE students SET name=?, email=?, phone=?, standard=? WHERE id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getStandard());
            ps.setInt(5, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Student> findAll() throws SQLException {
        String sql = "SELECT id, name, email, phone, standard FROM students ORDER BY id DESC";
        List<Student> list = new ArrayList<>();
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("standard")
                ));
            }
        }
        return list;
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT id, name, email, phone, standard FROM students WHERE id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("standard")
                    );
                }
            }
        }
        return null;
    }
}
