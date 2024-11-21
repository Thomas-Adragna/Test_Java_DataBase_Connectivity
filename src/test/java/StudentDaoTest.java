import org.example.DatabaseConnection;
import org.example.Student;
import org.example.StudentDao;
import org.example.CreateTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDaoTest {
    private static StudentDao studentDao;

    @BeforeAll
    public static void setUp() {
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String username = "sa";
        String password = "";

        DatabaseConnection dbConnection = new DatabaseConnection(url, username, password);
        studentDao = new StudentDao(dbConnection);

        // Création de la table et insertion des données
        CreateTable createTable = new CreateTable(dbConnection);
        createTable.createTable();

        PopulateTable populateTable = new PopulateTable();
        populateTable.dbConnection = dbConnection;
        populateTable.populateStudentTable();
    }

    @Test
    public void testFindAll() {
        List<Student> students = studentDao.findAll();

        // Vérifiez que 3 étudiants sont présents
        assertEquals(3, students.size(), "Le nombre d'étudiants récupérés devrait être 3");

        // Vérifiez les noms des étudiants
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Bob", students.get(1).getName());
        assertEquals("Charlie", students.get(2).getName());
    }

    @Test
    public void testFindById() {
        Student student = studentDao.findById(1);

        // Vérifiez que l'étudiant a été trouvé
        assertNotNull(student, "L'étudiant avec l'ID 1 devrait exister");
        assertEquals("Alice", student.getName(), "Le nom de l'étudiant avec l'ID 1 devrait être Alice");
        assertEquals(20, student.getAge(), "L'âge de l'étudiant avec l'ID 1 devrait être 20");

        // Vérifiez qu'un étudiant inexistant renvoie null
        Student nonExistentStudent = studentDao.findById(99);
        assertNull(nonExistentStudent, "Un étudiant avec un ID inexistant devrait renvoyer null");
    }

    @Test
    public void testCreate() {
        Student student = new Student(0, "Diana", 25);
        Student createdStudent = studentDao.create(student);

        assertNotNull(createdStudent.getId(), "L'ID de l'étudiant créé ne devrait pas être null.");
        assertEquals("Diana", createdStudent.getName());
        assertEquals(25, createdStudent.getAge());
    }

    @Test
    public void testDelete() {
        boolean result = studentDao.delete(1);

        assertTrue(result, "La suppression devrait réussir.");
        Student deletedStudent = studentDao.findById(1);
        assertNull(deletedStudent, "L'étudiant supprimé ne devrait plus exister.");
    }
}
