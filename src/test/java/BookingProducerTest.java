

import org.example.bookingnotifier.model.Booking;
import org.example.bookingnotifier.rabbitmq.BookingProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingProducerTest {

    private FakeRabbitTemplate rabbitTemplate;
    private BookingProducer bookingProducer;

    @BeforeEach
    void setUp() {
        rabbitTemplate = new FakeRabbitTemplate();
        bookingProducer = new BookingProducer(rabbitTemplate);
    }

    @Test
    void testSendBookingNotification() {
        Booking booking = new Booking(12345, "Test Event", "testuser@gmail.com",
                "2025-02-26T08:00:00.000Z", List.of());

        bookingProducer.sendBookingNotification(booking);

        assertEquals(1, rabbitTemplate.getMessages().size());
        assertEquals(booking, rabbitTemplate.getMessages().get(0));
    }

    // Fake RabbitTemplate Implementation
    static class FakeRabbitTemplate extends RabbitTemplate {
        private final List<Booking> messages = new ArrayList<>();

        @Override
        public void convertAndSend(String exchange, String routingKey, Object message) {
            if (message instanceof Booking) {
                messages.add((Booking) message);
            }
        }

        public List<Booking> getMessages() {
            return messages;
        }
    }
}
