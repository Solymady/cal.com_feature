package org.example.bookingnotifier.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookingnotifier.model.Booking;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.net.http.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import static org.example.bookingnotifier.model.Booking.untile;

@Service
public class BookingService {
    private static final String API_URL = "https://api.cal.com/v2/bookings";
    private static final String API_KEY = "cal_live_5e316539224dc0c3708f314819541fa8";

    public List<Booking> fetchBookings() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("cal-api-version", "2.0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode bookingsNode = rootNode.path("data").path("bookings");

        // Deserialize the bookings
        List<Booking> bookings = Arrays.asList(objectMapper.treeToValue(bookingsNode, Booking[].class));

        //  Debug: Print fetched bookings
        System.out.println("Fetched Bookings:");
        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getId());
            System.out.println("Title: " + booking.getTitle());
            System.out.println("Start Time: " + booking.getStartTime());
            System.out.println("Attendees: " + (booking.getAttendees() != null ? booking.getAttendees().size() : 0));
            System.out.println("-------------------------------");
        }

        return bookings;
    }

    public List<Booking> filterUpcomingBookings(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime targetTime = now.plusMinutes(untile);

        System.out.println("\n🔍 Checking bookings for events starting in 15 minutes");
        System.out.println("⏳ Current Time: " + now);
        System.out.println("🎯 Target Booking Start Time (Approx): " + targetTime);

        List<Booking> upcomingBookings = bookings.stream()
                .filter(booking -> {
                    LocalDateTime bookingTime = LocalDateTime.parse(booking.getStartTime(), DateTimeFormatter.ISO_DATE_TIME)
                            .truncatedTo(ChronoUnit.MINUTES);

                    long minutesUntilStart = ChronoUnit.MINUTES.between(now, bookingTime);

                    // Allow small drift (-1 or +1 minute) to prevent test mismatches
                    boolean isMatch = (minutesUntilStart >= untile-1 && minutesUntilStart <= untile+1);

                    System.out.println(" Booking ID: " + booking.getId() +
                            " | Start Time: " + booking.getStartTime() +
                            " | Minutes Until Start: " + minutesUntilStart +
                            " | Match: " + isMatch);

                    return isMatch;
                })
                .collect(Collectors.toList());

        System.out.println(" Found " + upcomingBookings.size() + " bookings that start in approximately 15 minutes.\n");
        return upcomingBookings;
    }
}