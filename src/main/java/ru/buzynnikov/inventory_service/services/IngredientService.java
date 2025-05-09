package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.models.Ingredient;

import java.math.BigDecimal;

public interface IngredientService {

    Ingredient create(IngredientCreateRequest request);

    Ingredient getById(Short ingredientId);

    Ingredient getByName(String ingredientName);

    void deleteById(Short ingredientId);

    void updateAmountByName(String ingredientName, BigDecimal amount);

    void updateSettlementAmountByName(String ingredientName, BigDecimal amount, Boolean isReceipt);

    void updateAmountById(Short ingredientId, BigDecimal amount);

    void updateSettlementAmountById(Short ingredientId, BigDecimal amount, Boolean isReceipt);

}
