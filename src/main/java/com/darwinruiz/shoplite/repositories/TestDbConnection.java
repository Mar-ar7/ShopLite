package com.darwinruiz.shoplite.repositories;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDbConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexión exitosa a PostgreSQL");
            } else {
                System.out.println("No se pudo conectar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

