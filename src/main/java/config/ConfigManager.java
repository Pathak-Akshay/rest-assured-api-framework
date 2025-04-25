package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public static String getEnv() {

        return properties.getProperty("env");
    }
}
