package com.guaitilsoft.models.constant;

public enum ActivityType {
    TOUR("Tour"), EXPERIENCE("Vivencia");

    private final String message;

    ActivityType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
