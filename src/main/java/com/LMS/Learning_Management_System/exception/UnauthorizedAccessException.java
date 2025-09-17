package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when a user attempts to perform an unauthorized action.
 * This includes access violations, permission failures, and role-based restrictions.
 */
public class UnauthorizedAccessException extends Exception {
    
    public UnauthorizedAccessException(String message) {
        super(message);
    }
    
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
