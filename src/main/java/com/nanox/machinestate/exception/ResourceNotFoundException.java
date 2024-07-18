package com.nanox.machinestate.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // You can add constructors for different use cases
    // For example, to include the cause of the exception
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}