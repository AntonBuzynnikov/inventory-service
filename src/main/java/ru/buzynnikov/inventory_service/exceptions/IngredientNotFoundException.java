package ru.buzynnikov.inventory_service.exceptions;

import java.util.function.Supplier;

/**
 * Исключение, возникающее при отсутствии запрашиваемого ингредиента.
 */
public class IngredientNotFoundException extends RuntimeException implements Supplier<IngredientNotFoundException> {

    /**
     * Конструктор исключения с сообщением.
     *
     * @param message сообщение об ошибке.
     */
    public IngredientNotFoundException(String message) {
        super(message);
    }

    /**
     * Реализация интерфейса {@link Supplier}, позволяющая получение экземпляра текущего исключения.
     *
     * @return экземпляр самого себя.
     */
    @Override
    public IngredientNotFoundException get() {
        return IngredientNotFoundException.this;
    }
}
