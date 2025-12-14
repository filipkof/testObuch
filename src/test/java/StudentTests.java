import org.example.Student;
import org.example.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentTests {
    WireMock wireMock = new WireMock(5352);

    @BeforeEach
    void setUp() {
        wireMock.startCheckGrade();
    }

    @AfterEach
    void tearDown() {
        wireMock.stopWireMock();
    }

    @Test
    public void testAddValidGrade() {
        Student student = new Student("Андрей");
        student.addGrade(5);
        assertEquals(Integer.valueOf(5), student.getGrades().getFirst());
    }

    @Test
    public void testAddInvalidLowGrade() {
        Student student = new Student("Андрей");
        assertThrows(IllegalArgumentException.class, () -> student.addGrade(1));
    }

    @Test
    public void testAddInvalidHighGrade() {
        Student student = new Student("Андрей");
        assertThrows(IllegalArgumentException.class, () -> student.addGrade(6));
    }
}



