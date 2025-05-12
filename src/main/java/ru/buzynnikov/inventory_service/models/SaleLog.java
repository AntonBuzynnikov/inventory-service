package ru.buzynnikov.inventory_service.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс описывает запись журнала продаж (продажи ингредиентов).
 */
@Entity
public class SaleLog {

    /**
     * Уникальный идентификатор записи журнала продаж.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер продажи (идентификационный номер конкретной продажи).
     */
    private String numberSale;


    /**
     * Инвентарный элемент, относящийся к данной продаже.
     */
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * Количество проданного товара.
     */
    private BigDecimal quantity;

    /**
     * Дата и время совершения продажи.
     */
    private LocalDateTime saleDate;

    /**
     * Признак удаления записи (логическая пометка).
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberSale() {
        return numberSale;
    }

    public void setNumberSale(String numberSale) {
        this.numberSale = numberSale;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
