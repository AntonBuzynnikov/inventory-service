package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;

public class ConductAnInventory {

    private Short ingredientId;

    private BigDecimal quantity;

    public Short getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Short ingredientId) {
        this.ingredientId = ingredientId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
