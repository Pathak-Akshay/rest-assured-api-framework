package services;

import config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseService {

    protected RequestSpecification baseRequest(String basePath) {
        return given()
                .baseUri(ConfigManager.getBaseURL())
                .basePath(basePath)
                .header("Accept","application/json")
                .header("Content-Type","application/json");
    }
}