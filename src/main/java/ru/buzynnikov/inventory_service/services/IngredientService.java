package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.models.Ingredient;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для работы с ингредиентами
 */
public interface IngredientService {

    /**
     * Создает новый ингредиент на основе данных из запроса
     * @param request DTO с данными для создания ингредиента
     * @param <T> тип DTO, наследующий IngredientCreateRequest
     * @return созданный ингредиент
     */
    <T extends IngredientCreateRequest> Ingredient create(T request);

    /**
     * Получает ингредиент по его идентификатору
     * @param ingredientId идентификатор ингредиента
     * @return найденный ингредиент
     */
    Ingredient getById(Short ingredientId);

    /**
     * Ищет ингредиенты по названию
     * @param ingredientName название ингредиента для поиска
     * @return список найденных ингредиентов
     */
    List<Ingredient> getByName(String ingredientName);

    /**
     * Удаляет ингредиент по его идентификатору
     * @param ingredientId идентификатор ингредиента для удаления
     */
    void deleteById(Short ingredientId);

    /**
     * Обновляет процент отходов для указанного ингредиента
     * @param id идентификатор ингредиента
     * @param newWastePercent новое значение процента отходов
     */
    void updateWastePercent(Short id, BigDecimal newWastePercent);

    /**
     * Находит все ингредиенты по списку идентификаторов
     * @param ids список идентификаторов ингредиентов
     * @return итерируемая коллекция найденных ингредиентов
     */
    Iterable<Ingredient> findAllById(List<Short> ids);


}
