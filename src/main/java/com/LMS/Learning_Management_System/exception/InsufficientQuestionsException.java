package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when there are insufficient questions available for quiz generation.
 */
public class InsufficientQuestionsException extends Exception {
    
    public InsufficientQuestionsException(String message) {
        super(message);
    }
    
    public InsufficientQuestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
