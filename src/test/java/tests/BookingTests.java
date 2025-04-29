package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.AuthService;
import services.BookingService;
import utilities.AllureLogger;
import utilities.Assertions;
import utilities.MockDataGenerator;
import static utilities.AllureLogger.*; // 👈 Important: Import your AllureLogger

public class BookingTests extends BaseTest {

    private BookingService bookingService;
    private AuthService authService;
    private static int bookingID;
    private String token;

    @BeforeClass
    public void setup() {
        bookingService = new BookingService();
        authService = new AuthService();

        token = authService.getToken();
        AllureLogger.logStep("Initialized BookingService instance for tests."); // Logging setup
    }

    @Test(description = "Verify booking can be created successfully",
            groups = {"regression", "booking", "create"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
    priority = 1)
    public void testCreateBooking() {

        AllureLogger.logStep("Starting test: Create Booking");

        BookingDates bookingDates = new BookingDates("2025-04-01","2025-04-02");

        Booking booking = new Booking("Jim","Brown",5000,true,bookingDates,"NA");

        AllureLogger.attachRequest(booking.toString());

        Response response = bookingService.createBooking(booking);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        bookingID = response.jsonPath().getInt("bookingid");

        AllureLogger.logStep("Generated Booking ID : " + bookingID);

        Assertions.assertEquals(response.getStatusCode(), 200, "Status code validation for booking creation.");
        Assertions.assertEquals(response.jsonPath().getString("booking.firstname"), "Jim", "First Name validation failed.");
        Assertions.assertEquals(response.jsonPath().getString("booking.lastname"), "Brown", "Last Name validation failed.");

        AllureLogger.logStep("Completed validation for create booking.");
    }

    @Test(description = "Verify booking can be retrieved successfully",
            groups = {"smoke", "booking", "get"},
            dependsOnMethods = "testCreateBooking",
    priority = 2)
    public void testGetBooking() {

        AllureLogger.logStep("Starting test: Verify retrieve booking");

        AllureLogger.logStep("Booking ID : " + bookingID);

        Response response = bookingService.getBooking(bookingID);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 200, "Status code validation for retrieving booking");
        Assertions.assertNotNull(response.jsonPath().getString("firstname"), "Firstname should not be null");
        Assertions.assertNotNull(response.jsonPath().getString("lastname"), "Lastname should not be null");

        logStep("Completed validation for retrieving a booking.");
    }

    @Test(description = "Verify booking can be updated successfully",
            groups = {"regression", "booking", "update"},
            dependsOnMethods = "testGetBooking",
    priority = 3)
    public void testUpdateBooking() {

        AllureLogger.logStep("Starting test : Update Booking");

        String updatedFirstName = MockDataGenerator.getFirstName();

        BookingDates bookingDates = new BookingDates("2025-04-01","2025-04-02");

        Booking booking = new Booking(updatedFirstName,"Brown",5000,true,bookingDates,"NA");

        AllureLogger.attachRequest(booking.toString());

        Response response = bookingService.updateBooking(bookingID, booking, token);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 200, "Status code validation for booking update");
        Assertions.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName, "Firstname mismatch after update");

        AllureLogger.logStep("Completed validation for test : Update Booking");
    }

    @Test(description = "Verify error response for non-existent booking",
            groups = {"negative", "booking", "get"},
    priority = 4)
    public void testGetNonExistentBooking() {

        AllureLogger.logStep("Starting test : Verify error response for non existent booking");

        String requestPayload = "{}";

        AllureLogger.attachRequest(requestPayload);
        Response response = bookingService.getBooking(999999);

        AllureLogger.attachResponse(response.getBody().asPrettyString());
        Assertions.assertEquals(response.getStatusCode(), 404, "Status code for non-existent booking should be 404");

        AllureLogger.logStep("Completed test : Verify non existent booking");
    }

    @Test(description = "Verify booking creation fails with missing data",
            groups = {"booking", "negative", "create"},
    priority = 5)
    public void testCreateBookingWithMissingData() {

        Booking booking = new Booking("Jim", "Brown", 5000, true, null, null);

        AllureLogger.attachRequest(booking.toString());

        Response response = bookingService.createBooking(booking);

        AllureLogger.attachResponse(response.getBody().asPrettyString());

        Assertions.assertEquals(response.getStatusCode(), 500, "Status code validation for missing data during booking creation");
    }

    @Test(description = "Verify booking can be deleted successfully",
            groups = {"booking", "delete"},
            dependsOnMethods = "testUpdateBooking",
    priority = 6)
    public void testDeleteBooking() {

        AllureLogger.logStep("Attempting to delete booking with ID: " + bookingID);

        Response response = bookingService.deleteBooking(bookingID, token);

        AllureLogger.attachResponse("Delete Booking Response Status: " + response.getStatusCode());

        Assertions.assertEquals(response.getStatusCode(), 201, "Status code validation for delete");
    }


}
