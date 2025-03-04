package org.example.bookingnotifier.rabbitmq;

import org.example.bookingnotifier.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.example.bookingnotifier.model.Booking;
import org.example.bookingnotifier.service.EmailService;

@Service
public class BookingConsumer {

    private final EmailService emailService;

    public BookingConsumer(EmailService emailService) {
        this.emailService = emailService;
        System.out.println("🚀 BookingConsumer initialized and listening for messages...");
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveBookingNotification(Booking booking) {
        System.out.println("📥 Received Booking Notification for ID: " + booking.getId());

        if (booking.getAttendees() == null || booking.getAttendees().isEmpty()) {
            System.out.println("⚠️ No attendees found, skipping email...");
            return;
        }

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
