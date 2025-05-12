package ru.buzynnikov.inventory_service.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class InventoryLog {

    @Id
    private Long id;

    /**
     * Связанный ингредиент (связь один ко многим).
     */
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * Дата создания записи инвентаризации.
     */
    private LocalDate createdAt;

    /**
     * Фактическое количество ингредиента на момент инвентаризации.
     */
    private BigDecimal quantity;    //фактический остаток


    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
