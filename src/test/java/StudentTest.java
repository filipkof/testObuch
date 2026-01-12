import org.example.Student;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void testSetName() {
        Student student = new Student("Андрей");
        student.setName("Дмитрий");
        assertEquals("Дмитрий", student.getName());
    }

    @Test
    public void testName() {
        Student student = new Student("Андрей");
        assertEquals("Андрей", student.getName());
    }

    @Test
    public void testGetGrade() {
        Student student = new Student("Андрей");
        student.addGrade(2);
        student.addGrade(3);
        student.addGrade(4);
        student.addGrade(5);
        assertEquals(4, student.getGrades().size());
    }

    @Test
    public void testGetGradeEncapsulation() {
        Student student = new Student("Андрей");
        student.addGrade(3);
        List<Integer> origGrades = student.getGrades();

        List<Integer> copyGrades = student.getGrades();
        copyGrades.add(5);
        assertNotEquals(copyGrades, origGrades);
    }

    @Test
    public void testAddValidGrade() {
        Student student = new Student("Андрей");
        student.addGrade(3);
        assertEquals(Integer.valueOf(3), student.getGrades().getFirst());
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

    @Test
    public void testHashCodeEquals() {
        Student studentA = new Student("Андрей");
        studentA.addGrade(3);

        Student studentB = new Student("Андрей");
        studentB.addGrade(3);

        assertEquals(studentA.hashCode(), studentB.hashCode());
    }

    @Test
    public void testHashCodeNotEqualsName() {
        Student studentA = new Student("Андрей");
        studentA.addGrade(3);

        Student studentB = new Student("Дмитрий");
        studentB.addGrade(3);

        assertNotEquals(studentA.hashCode(), studentB.hashCode());
    }

    @Test
    public void testHashCodeNotEqualsGrades() {
        Student studentA = new Student("Андрей");
        studentA.addGrade(3);

        Student studentB = new Student("Андрей");
        studentB.addGrade(4);

        assertNotEquals(studentA.hashCode(), studentB.hashCode());
    }

    @Test
    public void testEqualsEqual() {
        Student studentA = new Student("Андрей");
        studentA.addGrade(3);

        Student studentB = new Student("Андрей");
        studentB.addGrade(3);

        assertTrue(studentB.equals(studentA));
    }

    @Test
    public void testEqualsNameNotEquals() {
        Student studentA = new Student("Андрей");
        studentA.addGrade(3);

        Student studentB = new Student("Дмитрий");
        studentB.addGrade(3);

        assertFalse(studentB.equals(studentA));
    }

    @Test
    public void testEqualsGradeNotEquals() {
        Student studentA = new Student("Андрей");
        studentA.addGrade(3);

        Student studentB = new Student("Андрей");
        studentB.addGrade(4);

        assertFalse(studentB.equals(studentA));
    }

    @Test
    public void testToString() {
        Student student = new Student("Андрей");
        student.addGrade(3);
        student.addGrade(4);
        student.addGrade(5);
        String expectedSystemOut = "Student{name=Андрей, marks=[3, 4, 5]}";
        assertEquals(expectedSystemOut, student.toString());
    }
}
