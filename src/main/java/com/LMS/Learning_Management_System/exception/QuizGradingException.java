package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when quiz grading operations fail or are incomplete.
 * This includes cases where grades are not available or processing errors occur.
 */
public class QuizGradingException extends Exception {
    
    public QuizGradingException(String message) {
        super(message);
    }
    
    public QuizGradingException(String message, Throwable cause) {
        super(message, cause);
    }
}
