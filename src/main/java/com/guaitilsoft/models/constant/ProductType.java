package com.guaitilsoft.models.constant;

public enum ProductType {
    FOOD("Comida"), HANDICRAFT("Artesanía"), OTHER("Otro");

    private final String message;

    ProductType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
