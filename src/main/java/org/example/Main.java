package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String username = "sa";
        String password = "";

        DatabaseConnection dbConnection = new DatabaseConnection(url, username, password);
        StudentDao studentDao = new StudentDao(dbConnection);

        // Créer la table
        CreateTable createTable = new CreateTable(dbConnection);
        createTable.createTable();

        // Ajouter des étudiants
        Student student1 = studentDao.create(new Student(0, "Alice", 20));
        Student student2 = studentDao.create(new Student(0, "Bob", 22));
        Student student3 = studentDao.create(new Student(0, "Charlie", 21));

        // Afficher tous les étudiants
        List<Student> students = studentDao.findAll();
        System.out.println("Liste des étudiants :");
        for (Student student : students) {
            System.out.println(student);
        }

        // Rechercher un étudiant par ID
        Student foundStudent = studentDao.findById(student1.getId());
        System.out.println("Étudiant trouvé : " + foundStudent);

        // Mettre à jour un étudiant
        foundStudent.setName("Alice Updated");
        foundStudent.setAge(25);
        studentDao.update(foundStudent);

        // Afficher après la mise à jour
        System.out.println("Après mise à jour :");
        System.out.println(studentDao.findById(student1.getId()));

        // Supprimer un étudiant
        studentDao.delete(student2.getId());
        System.out.println("Après suppression :");
        students = studentDao.findAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
