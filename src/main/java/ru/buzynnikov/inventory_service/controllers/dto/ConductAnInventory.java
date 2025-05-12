package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;


/**
 * Класс DTO (Data Transfer Object), используемый для передачи данных инвентаризации ингредиентов.
 */
public class ConductAnInventory {

    /**
     * Идентификатор ингредиента.
     */
    private Short ingredientId;

    /**
     * Количество ингредиента.
     */
    private BigDecimal quantity;

    // Геттер и сеттер идентификатора ингредиента
    public Short getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Short ingredientId) {
        this.ingredientId = ingredientId;
    }

    // Геттер и сеттер количества ингредиента
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
