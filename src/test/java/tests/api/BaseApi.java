package tests.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class BaseApi {

    protected static Properties envConfig;
    protected static String authorization;
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    protected static String BASE_URL;

    @Parameters({"url"})
    @BeforeClass(alwaysRun = true)
    public void setup(@Optional String url) {
        // Get URL from Config properties files if it is not set in Test NG suite
        if (url == null) {
            InputStream configFile = null;
            try {
                configFile = new FileInputStream(System.getProperty("user.dir") +
                        "/src/test/java/tests/config/int.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            envConfig = new Properties();
            try {
                envConfig.load(configFile);
                BASE_URL = envConfig.getProperty("baseUrlApi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Request spec with base URI, content type, logging
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();

        // Response spec with logging and expected status
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

        // Set globally for all RestAssured requests
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    void getToken() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + envConfig.getProperty("username") + "\", \"password\":\"" + envConfig.getProperty("pass") + "\"}")
                .when()
                .post("/api/login");
        assertEquals("Unexpected status code", response.getStatusCode(), 200);
        authorization = response.path("token");
    }
}
