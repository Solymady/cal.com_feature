package org.example.bookingnotifier.rabbitmq;

import org.example.bookingnotifier.model.Booking;
import org.example.bookingnotifier.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookingConsumer {

    private final EmailService emailService;

    public BookingConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "bookingQueue") // ✅ Automatically converts JSON to Booking
    public void receiveBookingNotification(Booking booking) {
        System.out.println("📥 Received Booking Notification for ID: " + booking.getId());

        for (var attendee : booking.getAttendees()) {
            System.out.println("📧 Sending Email to: " + attendee.getEmail());

            emailService.sendEmail(
                    attendee.getEmail(),
                    "📅 Reminder: " + booking.getTitle(),
                    "Hello " + attendee.getName() + ",\n\n"
                            + "Your booking **" + booking.getTitle() + "** is scheduled in **15 minutes**.\n\n"
                            + "📅 Event: " + booking.getTitle() + "\n"
                            + "👥 Attendees: " + booking.getAttendees().size() + "\n\n"
                            + "Best regards,\nYour Booking Team"
            );
        }
    }
}