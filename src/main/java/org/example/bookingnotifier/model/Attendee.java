package org.example.bookingnotifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)  // Ignores any unknown fields from API response
public class Attendee implements Serializable {
    private String email;
    private String name;

    // ✅ Default Constructor (Needed for JSON deserialization)
    public Attendee() {}

    // ✅ Parameterized Constructor
    public Attendee(String email, String name) {
        this.email = email;
        this.name = name;
    }

    // ✅ Getters
    public String getEmail() { return email; }
    public String getName() { return name; }

    // ✅ Setters
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }

    // ✅ Override toString() for Debugging
    @Override
    public String toString() {
        return "Attendee{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}