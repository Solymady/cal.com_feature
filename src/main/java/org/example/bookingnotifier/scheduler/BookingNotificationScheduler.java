package org.example.bookingnotifier.scheduler;

import org.example.bookingnotifier.model.Booking;
import org.example.bookingnotifier.service.BookingService;
import org.example.bookingnotifier.rabbitmq.BookingProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BookingNotificationScheduler {

    private final BookingService bookingService;
    private final BookingProducer bookingProducer;

    private final Set<Integer> notifiedBookings = new HashSet<>();

    public BookingNotificationScheduler(BookingService bookingService, BookingProducer bookingProducer) {
        this.bookingService = bookingService;
        this.bookingProducer = bookingProducer;
    }

    @Scheduled(cron = "0 * * * * ?") // Runs every minute
    public void checkAndSendBookingNotifications() {
        try {
            System.out.println("\n⏳ Running Booking Notification Check...");
            List<Booking> bookings = bookingService.fetchBookings();
            List<Booking> upcomingBookings = bookingService.filterUpcomingBookings(bookings);

            for (Booking booking : upcomingBookings) {
                if (!notifiedBookings.contains(booking.getId())) {
                    notifiedBookings.add(booking.getId());
                    System.out.println("📤 Publishing Booking ID: " + booking.getId() + " to RabbitMQ");
                    bookingProducer.sendBookingNotification(booking);
                }
            }

            System.out.println("✅ Booking Notification Check Complete.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}