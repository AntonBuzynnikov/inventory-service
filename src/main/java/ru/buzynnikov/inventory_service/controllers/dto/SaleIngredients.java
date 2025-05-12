package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;

public class SaleIngredients {

    private Short ingredientId;
    private BigDecimal quantity;

    public Short getIngredientId() {
        return ingredientId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

}
