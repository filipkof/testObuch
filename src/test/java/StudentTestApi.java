import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.example.StudentGenerator;
import org.example.WireMock;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTestApi {

    StudentGenerator studentGenerator = new StudentGenerator();

    @BeforeAll
    static void settings() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void testGetStudentValidId() {
        studentGenerator.createStudent("1", "Андрей");
        given()
                .when().get("/student/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Андрей"),
                        "id", equalTo(1));
    }

    @Test
    void testGetStudentInvalidId() {
        given()
                .when().get("/student/9999")
                .then()
                .statusCode(404);
    }

    @Test
    void testCreateStudentNewIdValidName() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "id": "777",
                          "name": "Андрей"
                        }
                        """)
                .when().post("/student")
                .then()
                .statusCode(201);
        String responseBody = studentGenerator.getStudent(777);
        Assertions.assertFalse(responseBody.isBlank());
    }

    @Test
    void testUpdateStudentOldIdNewValidName() {
        studentGenerator.createStudent("555", "Андрей");
        given()
                .contentType("application/json")
                .body("""
                        {
                          "id": "555",
                          "name": "Дмитрий"
                        }
                        """)
                .when().post("/student")
                .then()
                .statusCode(201);
        String responseBody = studentGenerator.getStudent(555);
        String name = JsonPath.from(responseBody).getString("name");
        Assertions.assertEquals("Дмитрий", name);
    }

    @Test
    void testCreateStudentNullIdValidName() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "name": "Иван"
                        }
                        """)
                .when().post("/student")
                .then()
                .statusCode(201)
                .body(not(isEmptyOrNullString()));
    }

    @Test
    void testCreateStudentNullName() {
        given()
                .contentType("application/json")
                .body("""
                        {
                        }
                        """)
                .when().post("/student")
                .then()
                .statusCode(400);
    }

    @Test
    void testDeleteStudentValidId() {
        studentGenerator.createStudent("10", "Андрей");
        given()
                .when().delete("/student/10")
                .then()
                .statusCode(200);
        String responseBody = studentGenerator.getStudent(10);
        Assertions.assertTrue(responseBody.isBlank());
    }

    @Test
    void testDeleteStudentInvalidId() {
        given()
                .when().delete("/student/2222")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(1)
    void testGetTopStudentsEmptyList() {
        String body = given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .extract().asString();
        assertTrue(body == null || body.isBlank());
    }

    @Test
    @Order(2)
    void testGetTopStudentsWithoutGrades() {
        studentGenerator.createStudent("272", "Михаил");
        String body = given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .extract().asString();
        assertTrue(body == null || body.isBlank());
    }

    @Test
    @Order(3)
    void testGetTopStudentWithMaxAverageGrade() {
        studentGenerator.createStudentWithMarks("828", "Олег", new int[]{5, 5, 5, 5});
        given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1),
                        "[0].id", equalTo(828),
                        "[0].name", equalTo("Олег"));
    }

    @Test
    @Order(4)
    void testGetTopStudentsWithMaxAverageGrades() {
        studentGenerator.createStudentWithMarks("827", "Гело", new int[]{5, 5, 5, 5});
        given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2),
                        "[0].id", equalTo(828),
                        "[0].name", equalTo("Олег"),
                        "[1].id", equalTo(827),
                        "[1].name", equalTo("Гело"));
    }
}