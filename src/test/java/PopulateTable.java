import org.example.CreateTable;
import org.example.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopulateTable {
    DatabaseConnection dbConnection;

    // Méthode pour insérer des données dans la table Students
    public void populateStudentTable() {
        String insertSQL = "INSERT INTO Students (name, age) VALUES (?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, "Alice");
            preparedStatement.setInt(2, 20);
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("Alice ID : " + rs.getInt(1));
                }
            }

            preparedStatement.setString(1, "Bob");
            preparedStatement.setInt(2, 22);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Charlie");
            preparedStatement.setInt(2, 21);
            preparedStatement.executeUpdate();

            System.out.println("Données insérées avec succès dans la table Students !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion des données : " + e.getMessage());
        }
    }

    // Test unitaire
    @Test
    public void testPopulateStudentTable() {
        // Configuration de la connexion
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String username = "sa";
        String password = "";

        dbConnection = new DatabaseConnection(url, username, password);

        // Créer la table et insérer les données
        CreateTable createTable = new CreateTable(dbConnection);
        createTable.createTable();

        PopulateTable populateTable = new PopulateTable();
        populateTable.dbConnection = dbConnection; // Injection manuelle
        populateTable.populateStudentTable();
    }
}
