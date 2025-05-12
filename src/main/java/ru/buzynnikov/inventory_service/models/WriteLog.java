package ru.buzynnikov.inventory_service.models;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Класс моделирует запись журнала списания ингредиентов.
 */
@Entity
public class WriteLog {

    /**
     * Уникальный идентификатор записи журнала списания.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Комментарий к записи списания (причина списания и дополнительная информация).
     */
    private String comment;

    /**
     * Связанная запись ингредиента, который был списан.
     */
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * Дата списания ингредиента.
     */
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

