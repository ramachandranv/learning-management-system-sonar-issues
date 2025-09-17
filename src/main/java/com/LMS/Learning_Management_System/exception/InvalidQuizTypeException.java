package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when invalid quiz type is specified.
 * Quiz types must be within valid range (1-3).
 */
public class InvalidQuizTypeException extends Exception {
    
    public InvalidQuizTypeException(String message) {
        super(message);
    }
    
    public InvalidQuizTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
