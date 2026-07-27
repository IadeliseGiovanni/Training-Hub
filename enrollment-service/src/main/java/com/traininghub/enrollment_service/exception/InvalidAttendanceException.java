package com.traininghub.enrollment_service.exception;

public class InvalidAttendanceException extends RuntimeException {

    public InvalidAttendanceException(String message) {
        super(message);
    }
}