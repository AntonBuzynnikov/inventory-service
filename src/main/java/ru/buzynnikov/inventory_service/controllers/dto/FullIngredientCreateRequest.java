package ru.buzynnikov.inventory_service.controllers.dto;

import java.math.BigDecimal;


/**
 * Подкласс расширенного запроса на создание ингредиента, наследующий базовые характеристики от родительского класса
 * {@link IngredientCreateRequest}. Этот класс предназначен для передачи дополнительной информации при создании нового
 * ингредиента, включая процент отходов.
 */
public class FullIngredientCreateRequest extends IngredientCreateRequest{
    /**
     * Процент потерь продукта при использовании ингредиента.
     */
    private BigDecimal wastePercent;

    /**
     * Геттер для процента отходов.
     *
     * @return значение процента отходов
     */
    public BigDecimal getWastePercent() {
        return wastePercent;
    }

    /**
     * Сеттер для установки процента отходов.
     *
     * @param wastePercent новый процент отходов
     */
    public void setWastePercent(BigDecimal wastePercent) {
        this.wastePercent = wastePercent;
    }

    /**
     * Переопределённый метод toString(), возвращающий строковое представление класса с информацией о значении
     * процентов отходов и имени ингредиента.
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FullIngredientCreateRequest{");
        sb.append("name=").append(name);
        sb.append(", wastePercent='").append(wastePercent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
