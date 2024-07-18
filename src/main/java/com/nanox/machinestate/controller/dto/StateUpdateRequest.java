package com.nanox.machinestate.controller.dto;

public class StateUpdateRequest {
    private String value; // This field will capture the new value for the state

    // Constructor
    public StateUpdateRequest() {}

    // Getter and Setter
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
