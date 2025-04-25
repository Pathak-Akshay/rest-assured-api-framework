package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 0;
    private static final int maxRetry = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {

        if(attempt < maxRetry){

            attempt++;
            return true;
        }
        return false;
    }
}
