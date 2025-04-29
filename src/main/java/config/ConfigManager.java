package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    static {

        String env = System.getProperty("env","QA"); //default is QA

        try{

            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config/" + env.toLowerCase() + ".properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file for environment: ", e);
        }
    }

    public static String getBaseURL() {

        return properties.getProperty("baseURL");
    }

    public static String getBookingPath() {

        return properties.getProperty("bookingPath");
    }

    public static String getAuthPath(){

        return properties.getProperty("authPath");
    }

    public static String getEnv() {

        return properties.getProperty("env");
    }

    public static String getUser(){

        return properties.getProperty("username");
    }

    public static String getPassword(){

        return properties.getProperty("password");
    }
}
