package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    private final DatabaseConnection dbConnection;

    public CreateTable(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createTable() {
        String sql = """
    CREATE TABLE Students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT\s
    );
   \s""";
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            // Exécuter la requête de création de table
            statement.executeUpdate(sql);
            System.out.println("Table Student creee avec succes !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }

}

