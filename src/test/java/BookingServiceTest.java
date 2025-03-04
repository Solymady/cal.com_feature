

import org.example.bookingnotifier.model.Booking;
import org.example.bookingnotifier.model.Attendee;
import org.example.bookingnotifier.service.BookingService;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private final BookingService bookingService = new BookingService();


    @Test
    public void testFilterUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.plusMinutes(Booking.untile);

        Booking testBooking = new Booking(
                12345, "Test Event", "testuser@gmail.com",
                startTime.toString(), List.of(new Attendee("testuser@gmail.com", "Test User"))
        );

        List<Booking> filtered = bookingService.filterUpcomingBookings(List.of(testBooking));

        assertEquals(1, filtered.size(), "Should find exactly 1 booking that starts in 15 min");
    }

    @Test
    public void testFilterNoUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.plusMinutes(30);

        Booking testBooking = new Booking(
                12346, "Test Event", "testuser@gmail.com",
                startTime.toString(), List.of(new Attendee("testuser@gmail.com", "Test User"))
        );

        List<Booking> filtered = bookingService.filterUpcomingBookings(List.of(testBooking));

        assertEquals(0, filtered.size(), "Should find no bookings if time is not exactly 15 min");
    }
}