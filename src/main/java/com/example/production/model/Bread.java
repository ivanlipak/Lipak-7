package com.example.production.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Bread extends Item implements Edible{

    private BigDecimal weight;
    private static final Integer CALORIES = 100;

    public Bread(Long id, String name, Category category, BigDecimal width, BigDecimal height,
                 BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }


    @Override
    public Integer calculateilocalories() {
        BigDecimal kilocalories=this.weight.multiply(BigDecimal.valueOf(CALORIES));
        return kilocalories.intValue();
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal price=this.weight.multiply(getSellingPrice());
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bread bread = (Bread) o;
        return weight.equals(bread.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}

