package com.example.production.model;

import java.math.BigDecimal;
import java.util.Objects;

public non-sealed class Laptop extends Item implements Technical{

    private Integer warrantyInMonths;

    public Laptop(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost,
                  BigDecimal sellingPrice, Discount discount, Integer warrantyInMonths) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyInMonths = warrantyInMonths;
    }

    public void setWarranty(Integer warranty) {
        this.warrantyInMonths = warranty;
    }

    @Override
    public Integer getWarranty() {
        return warrantyInMonths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return warrantyInMonths.equals(laptop.warrantyInMonths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), warrantyInMonths);
    }

}
