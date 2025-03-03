package org.example.bookingnotifier.rabbitmq;

import org.example.bookingnotifier.model.Booking;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingProducer {

    private final RabbitTemplate rabbitTemplate;

    public BookingProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBookingNotification(Booking booking) {
        System.out.println("📤 Sending booking notification to RabbitMQ: " + booking.getId());
        rabbitTemplate.convertAndSend("bookingExchange", "booking.notify", booking); // ✅ Now Booking is converted to JSON
    }
}