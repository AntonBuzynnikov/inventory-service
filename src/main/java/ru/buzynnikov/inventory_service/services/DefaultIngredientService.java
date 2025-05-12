package ru.buzynnikov.inventory_service.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.inventory_service.controllers.dto.FullIngredientCreateRequest;
import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.exceptions.IngredientNotFoundException;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.models.InventoryLog;
import ru.buzynnikov.inventory_service.repositories.IngredientRepository;
import ru.buzynnikov.inventory_service.repositories.InventoryRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация сервиса для работы с ингредиентами
 */
@Service
public class DefaultIngredientService implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final InventoryRepository inventoryRepository;



    /**
     * Конструктор с внедрением зависимостей
     * @param ingredientRepository репозиторий ингредиентов
     * @param inventoryRepository репозиторий инвентаризации
     */
    public DefaultIngredientService(IngredientRepository ingredientRepository, InventoryRepository inventoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Создает новый ингредиент и соответствующую запись в инвентаризации
     * @param request DTO с данными для создания
     * @return созданный ингредиент
     */

    @Override
    @Transactional()
    public <T extends IngredientCreateRequest> Ingredient create(T request) {
        Ingredient ingredient = new Ingredient();

        // Если запрос содержит полные данные (включая процент отходов)
        if (request instanceof FullIngredientCreateRequest fullRequest) {
            ingredient.setName(fullRequest.getName());
            ingredient.setWastePercent(fullRequest.getWastePercent());
        } else {
            // Для базового запроса устанавливаем процент отходов по умолчанию (0)
            ingredient.setName(request.getName());
            ingredient.setWastePercent(BigDecimal.valueOf(0));
        }

        // Сохраняем ингредиент и создаем запись в инвентаризации
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        createInventory(savedIngredient);
        return savedIngredient;
    }

    /**
     * Получает ингредиент по ID
     * @param ingredientId ID ингредиента
     * @return найденный ингредиент
     * @throws IngredientNotFoundException если ингредиент не найден
     */
    @Override
    public Ingredient getById(Short ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(new IngredientNotFoundException("Ингредиент с идентификатором " + ingredientId + " не найден"));
    }

    /**
     * Ищет ингредиенты по названию
     * @param ingredientName название для поиска
     * @return список найденных ингредиентов
     */
    @Override
    public List<Ingredient> getByName(String ingredientName) {
        return ingredientRepository.findByIngredientName(ingredientName);
    }

    /**
     * Удаляет ингредиент по ID
     * @param ingredientId ID ингредиента для удаления
     */
    @Override
    @Transactional
    public void deleteById(Short ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    /**
     * Обновляет процент отходов для ингредиента
     * @param id ID ингредиента
     * @param newWastePercent новое значение процента отходов
     */
    @Override
    public void updateWastePercent(Short id, BigDecimal newWastePercent) {
        ingredientRepository.updateWastePercentById(id, newWastePercent);
    }

    /**
     * Находит все ингредиенты по списку ID
     * @param ids список ID ингредиентов
     * @return итерируемая коллекция ингредиентов
     */
    @Override
    public Iterable<Ingredient> findAllById(List<Short> ids) {
        return ingredientRepository.findAllById(ids);
    }

    /**
     * Создает запись в журнале инвентаризации для нового ингредиента
     * @param ingredient ингредиент для которого создается запись
     */
    private void createInventory(Ingredient ingredient){
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setId(Long.valueOf(ingredient.getId()));
        inventoryLog.setIngredient(ingredient);
        inventoryRepository.save(inventoryLog);
    }


}
