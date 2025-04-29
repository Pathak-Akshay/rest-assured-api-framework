package services;

import config.ConfigManager;
import io.restassured.response.Response;
import models.Booking;
import utilities.AllureLogger;

public class BookingService extends BaseService {

    private final String BOOKING_PATH = ConfigManager.getBookingPath();

    // Create a new booking
    public Response createBooking(Booking booking) {
        AllureLogger.logStep("Creating booking: " + booking.toString());

        return baseRequest(BOOKING_PATH)
                .body(booking)
                .post();
    }

    // Get a booking by ID
    public Response getBooking(int bookingId) {
        String fullUrl = ConfigManager.getBaseURL() + BOOKING_PATH + "/" + bookingId;
        AllureLogger.logStep("Getting booking. Hitting URL: " + fullUrl);

        return baseRequest(BOOKING_PATH)
                .get("/" + bookingId);
    }

    // Update an existing booking
    public Response updateBooking(int bookingId, Booking booking, String token) {
        AllureLogger.logStep("Updating booking ID: " + bookingId);

        return baseRequest(BOOKING_PATH)
                .header("Cookie", "token=" + token)
                .body(booking)
                .put("/" + bookingId);
    }

    // Delete a booking by ID
    public Response deleteBooking(int bookingId, String token) {
        AllureLogger.logStep("Deleting booking ID: " + bookingId);

        return baseRequest(BOOKING_PATH)
                .header("Cookie", "token=" + token)
                .delete("/" + bookingId);
    }
}
