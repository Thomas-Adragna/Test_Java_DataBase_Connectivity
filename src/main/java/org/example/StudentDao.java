package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private final DatabaseConnection dbConnection;

    public StudentDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Méthode findAll : Récupère tous les étudiants
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                students.add(new Student(id, name, age));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étudiants : " + e.getMessage());
        }

        return students;
    }

    // Méthode findById : Récupère un étudiant par son ID
    public Student findById(int id) {
        String query = "SELECT * FROM Students WHERE id = ?";
        Student student = null;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    student = new Student(id, name, age);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'étudiant avec l'ID " + id + " : " + e.getMessage());
        }

        return student;
    }

    public Student create(Student student) {
        String query = "INSERT INTO Students (name, age) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Définir les paramètres
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());

            // Exécuter la requête
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création de l'étudiant a échoué, aucune ligne affectée.");
            }

            // Récupérer l'ID généré
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("L'ID généré n'a pas pu être récupéré.");
                }
            }

            System.out.println("Étudiant créé avec succès : " + student);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'étudiant : " + e.getMessage());
        }
        return student;
    }


    public boolean update(Student student) {
        String query = "UPDATE Students SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Définir les paramètres
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setInt(3, student.getId());

            // Exécuter la mise à jour
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Étudiant mis à jour avec succès : " + student);
                return true;
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID : " + student.getId());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'étudiant : " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Students WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Définir le paramètre
            preparedStatement.setInt(1, id);

            // Exécuter la suppression
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Étudiant supprimé avec succès avec l'ID : " + id);
                return true;
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID : " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
            return false;
        }
    }



}
