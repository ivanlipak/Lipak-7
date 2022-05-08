package com.example.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Item extends NamedEntity implements Serializable {

    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private Discount discount;

    public Item(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(id, name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }
    public BigDecimal getHeight() {
        return height;
    }
    public void setHeight(BigDecimal height) {
        this.height = height;
    }
    public BigDecimal getLength() {
        return length;
    }
    public void setLength(BigDecimal length) {
        this.length = length;
    }
    public BigDecimal getProductionCost() {
        return productionCost;
    }
    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public BigDecimal calculatePrice () {
        return sellingPrice.multiply(new BigDecimal(1).subtract(this.discount.discountAmount().divide(new BigDecimal(100))));
    }
    public BigDecimal getVolume(){
        return (this.height.multiply(this.width).multiply(this.length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return category.equals(item.category) && width.equals(item.width) && height.equals(item.height) && length.equals(item.length) && productionCost.equals(item.productionCost) && sellingPrice.equals(item.sellingPrice) && discount.equals(item.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, width, height, length, productionCost, sellingPrice, discount);
    }

    @Override
    public String toString() {
        return "Item{" +
                ", name=" + getName() +
                ", category name=" + category.getName() +
                ", category description=" + category.getDescription() +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", productionCost=" + productionCost +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount.discountAmount()  +
                '}'
                + "\n";
    }

}
