package base;

import config.ConfigManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.AllureLogger;

import java.util.Arrays;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(){
        String env = ConfigManager.getEnv();
        AllureLogger.logStep("Running test in env : " + env);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result){
        String testName = result.getMethod().getMethodName();

        if(result.getStatus() == ITestResult.FAILURE){
            AllureLogger.logStep("Test Failed : " + testName);
            Throwable failureReason = result.getThrowable();

            if(failureReason != null){
                AllureLogger.attachPlainText("Failure Reason",
                        failureReason.toString() + "\n" +
                                Arrays.toString(failureReason.getStackTrace()));
            }

            else if(result.getStatus() == ITestResult.SUCCESS){
                AllureLogger.logStep("Test Passed: " + testName);
            }

            else if(result.getStatus() == ITestResult.SKIP){
                AllureLogger.logStep("Test Skipped: " + testName);
            }
        }
    }
}
