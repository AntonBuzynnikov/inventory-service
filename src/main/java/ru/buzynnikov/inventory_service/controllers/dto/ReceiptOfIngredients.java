package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;


/**
 * Класс DTO (Data Transfer Object), предназначенный для приема данных о поступлении ингредиентов.
 */
public class ReceiptOfIngredients {

    /**
     * Уникальный идентификатор ингредиента.
     */
    private Short ingredientId;

    /**
     * Объем поступления ингредиента.
     */
    private BigDecimal amount;


    /**
     * Получение уникального идентификатора ингредиента.
     *
     * @return уникальный идентификатор ингредиента.
     */
    public Short getIngredientId() {
        return ingredientId;
    }


    /**
     * Получение объема поступления ингредиента.
     *
     * @return объем поступления ингредиента.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Установка уникального идентификатора ингредиента.
     *
     * @param ingredientId новый уникальный идентификатор ингредиента.
     */
    public void setIngredientId(Short ingredientId) {
        this.ingredientId = ingredientId;
    }

    /**
     * Установка объема поступления ингредиента.
     *
     * @param amount новый объем поступления ингредиента.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
