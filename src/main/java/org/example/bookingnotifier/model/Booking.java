package org.example.bookingnotifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking implements Serializable {
    private int id;
    private String title;
    private String userPrimaryEmail;
    private String startTime;
    private List<Attendee> attendees;
    public static Integer untile =-91;

    //  Default Constructor (Required for JSON Deserialization)
    public Booking() {}

    // Parameterized Constructor (Fixes your test error)
    public Booking(int id, String title, String userPrimaryEmail, String startTime, List<Attendee> attendees) {
        this.id = id;
        this.title = title;
        this.userPrimaryEmail = userPrimaryEmail;
        this.startTime = startTime;
        this.attendees = attendees;
    }

    public <E> Booking(long l, String teamMeeting, List<E> attendee1) {
    }

    //  Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getUserPrimaryEmail() { return userPrimaryEmail; }
    public String getStartTime() { return startTime; }
    public List<Attendee> getAttendees() { return attendees; }


    // ✅ Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setUserPrimaryEmail(String userPrimaryEmail) { this.userPrimaryEmail = userPrimaryEmail; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setAttendees(List<Attendee> attendees) { this.attendees = attendees; }

    // ✅ Override toString() for Debugging
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userPrimaryEmail='" + userPrimaryEmail + '\'' +
                ", startTime='" + startTime + '\'' +
                ", attendees=" + attendees +
                '}';
    }
}