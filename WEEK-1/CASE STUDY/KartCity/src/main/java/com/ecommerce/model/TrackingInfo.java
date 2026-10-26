package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TrackingInfo {
    private final String courierName;
    private final String trackingNumber;
    private final List<TrackingEvent> events = new ArrayList<>();

    public record TrackingEvent(LocalDateTime timestamp, String location, String description) {
    }

    public TrackingInfo(String courierName, String trackingNumber) {
        if (courierName == null || courierName.isBlank()) {
            throw new IllegalArgumentException("Courier name is required");
        }
        if (trackingNumber == null || trackingNumber.isBlank()) {
            throw new IllegalArgumentException("Tracking number is required");
        }
        this.courierName = courierName.trim();
        this.trackingNumber = trackingNumber.trim();
        addEvent("KartCity warehouse", "Order dispatched");
    }

    public String getCourierName() {
        return courierName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public List<TrackingEvent> getEvents() {
        return new ArrayList<>(events);
    }

    public void addEvent(String event) {
        addEvent("In transit", event);
    }

    public void addEvent(String location, String event) {
        if (event == null || event.isBlank()) {
            throw new IllegalArgumentException("Event is required");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Event location is required");
        }
        events.add(new TrackingEvent(LocalDateTime.now(), location.trim(), event.trim()));
    }
}
