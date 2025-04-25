package utilities;

import org.testng.Assert;

public class Assertions {

    public static void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message + " | Expected: " + expected + " but got: " + actual);
    }

    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message + " | Condition was expected to be true but was false.");
    }

    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message + " | Condition was expected to be false but was true.");
    }

    public static void assertNotNull(Object object, String message) {
        Assert.assertNotNull(object, message + " | Object was expected to be not null.");
    }

    public static void assertNull(Object object, String message) {
        Assert.assertNull(object, message + " | Object was expected to be null.");
    }
}
