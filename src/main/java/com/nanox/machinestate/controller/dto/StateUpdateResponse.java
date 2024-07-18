package com.nanox.machinestate.controller.dto;

public class StateUpdateResponse {
    private String previousValue;
    private String newValue;

    public StateUpdateResponse(String previousValue, String newValue) {
        this.previousValue = previousValue;
        this.newValue = newValue;
    }

    // Getters and Setters
    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
