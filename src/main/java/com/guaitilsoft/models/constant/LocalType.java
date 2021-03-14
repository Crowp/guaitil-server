package com.guaitilsoft.models.constant;

public enum LocalType {
    KITCHEN("Cocina"), WORKSHOP("Taller"), LODGING("Hospedaje"), OTHERS("Otro");

    private final String message;

    LocalType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
