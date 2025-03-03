package org.example.bookingnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enables Spring's task scheduler
public class BookingNotifierApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingNotifierApplication.class, args);
    }
}