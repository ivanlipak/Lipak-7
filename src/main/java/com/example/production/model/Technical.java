package com.example.production.model;

public sealed interface Technical permits Laptop {
    default Integer getWarranty() {
        return null;
    }
}
