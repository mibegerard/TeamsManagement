package org.example.TeamsEvent.service;

public class MeetingCreationException extends RuntimeException {

    public MeetingCreationException(String message) {
        super(message);
    }

    public MeetingCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

