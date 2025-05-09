package ru.buzynnikov.inventory_service.exceptions;

import java.util.function.Supplier;

public class IngredientNotFoundException extends RuntimeException implements Supplier<IngredientNotFoundException> {
    public IngredientNotFoundException(String message) {
        super(message);
    }

    @Override
    public IngredientNotFoundException get() {
        return IngredientNotFoundException.this;
    }
}
