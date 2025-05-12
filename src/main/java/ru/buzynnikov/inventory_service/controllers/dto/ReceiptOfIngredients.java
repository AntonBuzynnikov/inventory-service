package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;

public class ReceiptOfIngredients {

    private Short ingredientId;

    private BigDecimal amount;

    public Short getIngredientId() {
        return ingredientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setIngredientId(Short ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
