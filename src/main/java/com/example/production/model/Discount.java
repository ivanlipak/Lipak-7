package com.example.production.model;
import java.io.Serializable;
import java.math.BigDecimal;

public record Discount(BigDecimal discountAmount) implements Serializable {

    @Override
    public BigDecimal discountAmount() {
        return discountAmount;
    }
}
