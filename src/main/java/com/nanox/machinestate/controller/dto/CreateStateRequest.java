package com.nanox.machinestate.controller.dto;

public class CreateStateRequest {
    private String name;
    private Long flowId;
    private String value = ""; // Default to empty string if not provided

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
