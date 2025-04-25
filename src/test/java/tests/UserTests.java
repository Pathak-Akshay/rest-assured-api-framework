package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.UserService;
import utilities.AllureLogger;
import utilities.Assertions;
import utilities.MockDataGenerator;
import static utilities.AllureLogger.*; // 👈 Important: Import your AllureLogger

public class UserTests extends BaseTest {

    private UserService userService;

    @BeforeClass
    public void setup() {
        userService = new UserService();
        AllureLogger.logStep("Initialized UserService instance for tests."); // Logging setup
    }

    @Test(description = "Verify user list can be retrieved successfully",
            groups = {"regression", "user", "get"},
            retryAnalyzer = listeners.RetryAnalyzer.class)
    public void testGetUserList() {

        AllureLogger.logStep("Starting test: Verify user list retrieval");

        String requestPayload = "{}"; // For GET request, no body generally
        AllureLogger.attachRequest(requestPayload);

        Response response = userService.getUsers(2);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 200, "Status code validation");
        Assertions.assertTrue(response.jsonPath().getList("data").size() > 0, "User list should not be empty");

        AllureLogger.logStep("Completed validation for retrieving user list.");
    }

    @Test(description = "Verify user can be created successfully",
            groups = {"smoke", "user", "create"})
    public void testCreateUser() {

        AllureLogger.logStep("Starting test: Verify user creation");

        String name = MockDataGenerator.getFirstName() + MockDataGenerator.generateLastName();
        String title = MockDataGenerator.generateJobTitle();

        User user = User.builder()
                .name(name)
                .job(title)
                .build();

        AllureLogger.attachPlainText("Generated User Data:", " Name = " + name + ", Job = " + title);

        Response response = userService.createUser(user);

        attachRequest(user.toString());
        attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 201, "Status code validation");
        Assertions.assertEquals(response.jsonPath().getString("name"), name, "Username validation failed");

        logStep("Completed validation for creating a new user.");
    }

    @Test(description = "Verify single user can be retrieved successfully",
            groups = {"regression", "user", "get"})
    public void testGetSingleUser() {

        AllureLogger.logStep("Sarting test : Get Single User");

        String requestPayload = "{}";

        AllureLogger.attachRequest(requestPayload);

        Response response = userService.getUserById(2);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 200, "Status code validation");
        Assertions.assertEquals(response.jsonPath().getInt("data.id"), 2, "User ID mismatch");

        AllureLogger.logStep("Completed validation for test : Get Single User");
    }

    @Test(description = "Verify error response when retrieving non-existent user",
            groups = {"negative", "user", "get"})
    public void testGetNonExistentUser() {

        AllureLogger.logStep("Starting test : Verify error response for non existent user");

        String requestPayload = "{}";

        AllureLogger.attachRequest(requestPayload);
        Response response = userService.getUserById(999);

        AllureLogger.attachResponse(response.getBody().asPrettyString());
        Assertions.assertEquals(response.getStatusCode(), 404, "Status code for non-existent user should be 404");

        AllureLogger.logStep("Completed test : Verify non existent user");
    }

    @Test(description = "Verify user can be updated successfully",
            groups = {"user", "update"})
    public void testUpdateUser() {
        String updatedJob = MockDataGenerator.generateJobTitle();

        User updatedUser = User.builder()
                .name("Updated Name")
                .job(updatedJob)
                .build();

        AllureLogger.attachPlainText("Updating user with new job title: " , updatedJob);

        Response response = userService.updateUser(2, updatedUser);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 200, "Status code validation for update");
        Assertions.assertEquals(response.jsonPath().getString("job"), updatedJob, "Updated job title mismatch");
    }

    @Test(description = "Verify user can be deleted successfully",
            groups = {"user", "delete"})
    public void testDeleteUser() {
        int userIdToDelete = 2;

        AllureLogger.logStep("Attempting to delete user with ID: " + userIdToDelete);

        Response response = userService.deleteUser(userIdToDelete);

        AllureLogger.attachResponse("Delete User Response Status: " + response.getStatusCode());

        Assertions.assertEquals(response.getStatusCode(), 204, "Status code validation for delete");
    }


}
