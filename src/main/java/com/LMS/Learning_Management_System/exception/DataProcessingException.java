package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when data serialization/deserialization fails.
 * This typically occurs during JSON processing operations.
 */
public class DataProcessingException extends RuntimeException {
    
    public DataProcessingException(String message) {
        super(message);
    }
    
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
