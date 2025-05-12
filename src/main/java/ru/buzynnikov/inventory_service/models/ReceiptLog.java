package ru.buzynnikov.inventory_service.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс представляет журнал учета прихода ингредиентов.
 */
@Entity
public class ReceiptLog {

    /**
     * Уникальный идентификатор записи журнала приходов.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер накладной документа прихода.
     */
    private String invoiceNumber;

    /**
     * Связанный ингредиент (отношение многие к одному).
     */
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * Количество принятого ингредиента.
     */
    private BigDecimal amount;

    /**
     * Дата прихода ингредиента.
     */
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
