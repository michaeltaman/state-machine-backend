package com.nanox.machinestate.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime eventDate;
    private String eventName;
    private String stateStarted;
    private String stateEnded;

    public EventLog(LocalDateTime eventDate, String eventName, String stateStarted, String stateEnded) {
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.stateStarted = stateStarted;
        this.stateEnded = stateEnded;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getStateStarted() {
        return stateStarted;
    }

    public String getStateEnded() {
        return stateEnded;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStateStarted(String stateStarted) {
        this.stateStarted = stateStarted;
    }

    public void setStateEnded(String stateEnded) {
        this.stateEnded = stateEnded;
    }

    // Constructors, Getters, and Setters
}
