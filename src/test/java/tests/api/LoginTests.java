package tests.api;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class LoginTests extends BaseApi {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        super.setup(null);
    }

    @Test(description = "Login using valid Credentials")
    public void loginValidCredentials() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + envConfig.getProperty("username") + "\", \"password\":\"" + envConfig.getProperty("pass") + "\"}")
                .when()
                .post("/api/login");

        // Validate status code
        int statusCode = response.getStatusCode();
        assertEquals("Unexpected status code", statusCode, 200);
        // Validate Success
        assertTrue(response.path("success"));
        // Validate Token
        assertNotNull(response.path("token"));
    }

    @Test(description = "Login using Empty Credentials")
    public void loginEmptyCredentials() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"\", \"password\":\"\"}")
                .when()
                .post("/api/login");

        // Validate status code
        int statusCode = response.getStatusCode();
        assertEquals("Authentication should fail", statusCode, 401);
        // Validate End Point Response
        assertFalse(response.path("success"));
        assertEquals("Authentication should fail", response.path("message"), "Invalid credentials");
    }

    @Test(description = "Login using Empty Credentials")
    public void loginEmptyPassword() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + envConfig.getProperty("username") + "\", \"password\":\"\"}")
                .when()
                .post("/api/login");

        // Validate status code
        int statusCode = response.getStatusCode();
        assertEquals("Authentication should fail", statusCode, 401);
        // Validate End Point Response
        assertFalse(response.path("success"));
        assertEquals("Authentication should fail", response.path("message"), "Invalid credentials");
    }

    @Test(description = "Login Invalid Password")
    public void loginInvalidPassword() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + envConfig.getProperty("username") + "\", \"password\":\"asdhkb\"}")
                .when()
                .post("/api/login");

        // Validate status code
        int statusCode = response.getStatusCode();
        assertEquals("Authentication should fail", statusCode, 401);
        // Validate End Point Response
        assertFalse(response.path("success"));
        assertEquals("Authentication should fail", response.path("message"), "Invalid credentials");
    }
}
