package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {


    public List<Product> findAll(int limit, int offset) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, stock FROM products ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, stock FROM products ORDER BY id";

        try (Connection conn = DbConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Optional<Product> findById(long id) {
        String sql = "SELECT id, name, price, stock FROM products WHERE id = ?";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean add(Product product) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE id = ?";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setLong(4, product.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM products";
        try (Connection conn = DbConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock")
        );
    }
}
