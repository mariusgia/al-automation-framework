package tests.api;

import base.Utils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ItemTests extends BaseApi {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        super.setup(null);
        super.getToken();
    }

    @Test(description = "Add , Edit, Delete, Get an Item")
    public void addEditAndDeleteItem() {
        // POST - Create New Item
        String newItemName = "ItemA_" + Utils.getUniqueValue();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .body("{\"text\":\"" + newItemName + "\"}")
                .when()
                .post("/api/items");

        // Check POST response
        assertEquals("Unexpected status code", response.getStatusCode(), 200);

        // Get Item ID
        String itemId = String.valueOf(response.jsonPath().getInt("id"));

        // GET - Check Item in List
        response = getAndCheckItem(response, newItemName);
        assertTrue(response.path("").toString().contains(newItemName));

        // PUT - Edit Item
        newItemName = "ItemAu_" + Utils.getUniqueValue();
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .body("{\"text\":\"" + newItemName + "\"}")
                .when()
                .put("/api/items/" + itemId);
        assertEquals("Unexpected status code", response.getStatusCode(), 200);

        // GET - Check Item in List
        response = getAndCheckItem(response, newItemName);
        assertTrue(response.jsonPath().get().toString().contains(newItemName));

        // DELETE - Delete Item
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .when()
                .delete("/api/items/" + itemId);
        assertEquals("Unexpected status code", response.getStatusCode(), 200);

        // GET - Check Item in List
        response = getAndCheckItem(response, newItemName);
        assertFalse(response.jsonPath().get().toString().contains(newItemName));
    }

    @Test(description = "Try to Add an Item no token")
    public void addItemNoToken() {
        // POST - Create New Item
        String newItemName = "ItemA_" + Utils.getUniqueValue();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "")
                .body("{\"text\":\"" + newItemName + "\"}")
                .when()
                .post("/api/items");

        // Check POST response
        assertEquals("Unexpected status code", response.getStatusCode(), 403);
        assertEquals("Unexpected message", response.jsonPath().getString("message"), "Unauthorized");
    }

    @Test(description = "Try to Edit / Delete an Item no token")
    public void tryEditAndDeleteItemNoToken() {
        // POST - Create New Item
        String newItemName = "ItemA_" + Utils.getUniqueValue();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .body("{\"text\":\"" + newItemName + "\"}")
                .when()
                .post("/api/items");

        // Check POST response
        assertEquals("Unexpected status code", response.getStatusCode(), 200);

        // Get Item ID
        String itemId = String.valueOf(response.jsonPath().getInt("id"));

        // PUT - Edit Item
        newItemName = "ItemAu_" + Utils.getUniqueValue();
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "")
                .body("{\"text\":\"" + newItemName + "\"}")
                .when()
                .put("/api/items/" + itemId);
        assertEquals("Unexpected status code", response.getStatusCode(), 403);
        assertEquals("Unexpected message", response.jsonPath().getString("message"), "Unauthorized");


        // DELETE - Delete Item
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "")
                .when()
                .delete("/api/items/" + itemId);
        assertEquals("Unexpected status code", response.getStatusCode(), 403);
        assertEquals("Unexpected message", response.jsonPath().getString("message"), "Unauthorized");
    }

    @Test(description = "Try to Add an Item using empty value - BUG")
    public void addItemEmptyValue() {
        // POST - Create New Item
        String newItemName = "ItemA_" + Utils.getUniqueValue();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .body("{\"text\":\"\"}")
                .when()
                .post("/api/items");

        // Check POST response
        assertEquals("Unexpected status code", response.getStatusCode(), 422);
        assertEquals("Unexpected message", response.jsonPath().getString("message"), "Unprocessable Entity");
    }

    // Common method
    private Response getAndCheckItem(Response response, String newItemName) {
        // GET - Check Item in List
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .when()
                .get("/api/items");

        assertEquals("Unexpected status code", response.getStatusCode(), 200);
        return response;
    }
}

