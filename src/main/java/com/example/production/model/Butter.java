package com.example.production.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Butter extends Item implements Edible{

    private static final Integer CALORIES = 200;
    private BigDecimal weight;

    public Butter(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    public Integer getCALORIES() {
        return CALORIES;
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
        Butter butter = (Butter) o;
        return weight.equals(butter.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}
