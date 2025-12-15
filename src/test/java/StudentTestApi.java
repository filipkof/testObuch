import io.restassured.RestAssured;
import org.example.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTestApi {

    WireMock wireMock = new WireMock(5352);
    String baseUrl = "http://localhost";

    @BeforeAll
    static void settings() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 5352;
    }

    @BeforeEach
    void setUp() {
        wireMock.startWireMockServer();
    }

    @AfterEach
    void tearDown() {
        wireMock.stopWireMockServer();
    }

    @Test
    void testGetStudentValidId() {
        given()
                .when().get("/student/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Андрей"),
                        "id", equalTo("1"));
    }

    @Test
    void testGetStudentInvalidId() {
        given()
                .when().get("/student/2")
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
    }

    @Test
    void testUpdateStudentOldIdNewValidName() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "id": "777",
                          "name": "Дмитрий"
                        }
                        """)
                .when().post("/student")
                .then()
                .statusCode(201);
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
                .body("id", notNullValue());
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
        given()
                .when().delete("/student/2")
                .then()
                .statusCode(200);
    }

    @Test
    void testDeleteStudentInvalidId() {
        given()
                .when().delete("/student/3")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetTopStudentsEmptyList() {
        String body = given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .extract().asString();
        assertTrue(body == null || body.isBlank());
    }

    @Test
    void testGetTopStudentsWithoutGrades() {
        String body = given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .extract().asString();
        assertTrue(body == null || body.isBlank());
    }

    @Test
    void testGetTopStudentWithMaxAverageGrade() {
        given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .body("id", notNullValue(),
                        "name", notNullValue());
    }

    @Test
    void testGetTopStudentsWithMaxAverageGrades() {
        given()
                .when().get("/topStudent")
                .then()
                .statusCode(200)
                .body("students", not(empty()))
                .body("students[1].id", notNullValue())
                .body("students[1].name", notNullValue());
    }

}