package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;

/**
 * Класс DTO (Data Transfer Object), представляющий данные продажи ингредиентов.
 */
public class SaleIngredients {


    /**
     * Идентификатор ингредиента.
     */
    private Short ingredientId;
    /**
     * Количество проданного ингредиента.
     */
    private BigDecimal quantity;

    /**
     * Получить идентификатор ингредиента.
     *
     * @return идентификатор ингредиента.
     */
    public Short getIngredientId() {
        return ingredientId;
    }

    /**
     * Получить количество проданного ингредиента.
     *
     * @return количество проданного ингредиента.
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

}
