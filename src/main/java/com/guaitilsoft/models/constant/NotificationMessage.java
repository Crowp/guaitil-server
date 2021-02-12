package com.guaitilsoft.models.constant;

public enum NotificationMessage {
    PRODUCT_NOTIFICATION("Hay un nuevo producto por revisar"),
    ACTIVITY_NOTIFICATION("Has sido invitado a la actividad ");

    private final String message;

    NotificationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
