package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when a duplicate submission is attempted.
 * This prevents users from submitting multiple responses to the same quiz.
 */
public class DuplicateSubmissionException extends Exception {
    
    public DuplicateSubmissionException(String message) {
        super(message);
    }
    
    public DuplicateSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
