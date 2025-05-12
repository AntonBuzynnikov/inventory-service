package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.models.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {

    <T extends IngredientCreateRequest> Ingredient create(T request);

    Ingredient getById(Short ingredientId);

    List<Ingredient> getByName(String ingredientName);

    void deleteById(Short ingredientId);

    void updateWastePercent(Short id, BigDecimal newWastePercent);

    Iterable<Ingredient> findAllById(List<Short> ids);


}
