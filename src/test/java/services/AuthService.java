package services;

import config.ConfigManager;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthService {

    private final String baseURL;
    private final String authPath;
    private static String token;

    public AuthService(){

        baseURL = ConfigManager.getBaseURL();
        authPath = ConfigManager.getAuthPath();
    }

    public String getToken(){

        if (token == null){
            token = getAuthToken();
        }
        return token;
    }

    public String getAuthToken(){

        Map<String,String> credentials = new HashMap<>();

        credentials.put("username", ConfigManager.getUser());
        credentials.put("password", ConfigManager.getPassword());

        Response response = given()
                .baseUri(baseURL)
                .basePath(authPath)
                .contentType("application/json")
                .body(credentials)
                .post();

        if(response.statusCode() != 200){
            throw new RuntimeException("Authentication failed: " + response.getBody().asString());
        }

        return response.jsonPath().getString("token");
    }
}