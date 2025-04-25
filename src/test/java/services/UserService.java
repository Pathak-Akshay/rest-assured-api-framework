package services;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserService {

    public UserService(){

        RestAssured.baseURI = ConfigManager.getBaseURL();
    }

    public Response getUsers(int page){

        return given().queryParam("page",page)
                .when()
                .get("/api/users")
                .then()
                .extract()
                .response();
    }

    public Response createUser(User user){

        return given().header("Content-Type", "application/json")
                .body(user).when()
                .post("/api/users")
                .then()
                .extract()
                .response();
    }

    public Response getUserById(int userID){

        return given()
                .when()
                .get("/api/users/" + userID);
    }

    public Response updateUser(int userID, User user){

        return given()
                .contentType("application/json")
                .body(user)
                .when()
                .put("/api/users/" + userID);
    }

    public Response deleteUser(int userID){

        return given()
                .when()
                .delete("/api/users/" + userID);
    }
}
