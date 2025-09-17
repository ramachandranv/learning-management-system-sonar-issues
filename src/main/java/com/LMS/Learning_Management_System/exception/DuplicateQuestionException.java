package com.LMS.Learning_Management_System.exception;

/**
 * Exception thrown when a question already exists in the system.
 * This prevents duplicate questions from being added to the question bank.
 */
public class DuplicateQuestionException extends Exception {
    
    public DuplicateQuestionException(String message) {
        super(message);
    }
    
    public DuplicateQuestionException(String message, Throwable cause) {
        super(message, cause);
    }
}
