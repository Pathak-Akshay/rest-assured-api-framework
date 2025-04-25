package utilities;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class AllureLogger {

    public static void logStep(String message){
        Allure.step(message);
    }

    public static void attachRequest(String requestPayload){
        Allure.addAttachment("Request", "application/json", requestPayload, ".json");
    }


    public static void attachResponse(String responsePayload){
        Allure.addAttachment("Response", "application/json", responsePayload, ".json");
    }


    public static void attachPlainText(String title, String content){
        Allure.addAttachment(title, "text/plain", content);
    }
}
