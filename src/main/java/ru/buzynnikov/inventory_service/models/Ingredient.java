package ru.buzynnikov.inventory_service.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Модель сущности "Ингредиент", хранит информацию об ингредиенте в приложении.
 */
@Entity
public class Ingredient {

    /**
     * Уникальный идентификатор ингредиента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /**
     * Название ингредиента.
     */
    private String name;

    /**
     * Процент отходов при использовании ингредиента.
     */
    @Column(name = "waste_percentage", precision = 4, scale = 3)
    private BigDecimal wastePercent;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWastePercent() {
        return wastePercent;
    }

    public void setWastePercent(BigDecimal wastePercent) {
        this.wastePercent = wastePercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
