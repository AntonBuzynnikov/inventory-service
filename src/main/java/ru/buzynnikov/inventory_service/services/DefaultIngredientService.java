package ru.buzynnikov.inventory_service.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.exceptions.IngredientNotFoundException;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.repositories.IngredientRepository;

import java.math.BigDecimal;

/**
 * Сервисный класс для управления ингредиентами в инвентаре.
 */

@Service
public class DefaultIngredientService implements IngredientService{

    /**
     * Репозиторий ингредиентов для взаимодействия с базой данных.
     */
    private final IngredientRepository ingredientRepository;

    /**
     * Конструктор сервиса с инъекцией репозитория ингредиентов.
     *
     * @param ingredientRepository репозиторий ингредиентов
     */
    public DefaultIngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Создает новый ингредиент в инвентаре.
     *
     * @param request объект запроса на создание нового ингредиента
     * @return созданный ингредиент
     */
    @Override
    @Transactional()
    public Ingredient create(IngredientCreateRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setAmount(BigDecimal.valueOf(0));
        ingredient.setSettlementAmount(BigDecimal.valueOf(0));
        ingredient.setWastePercent(BigDecimal.valueOf(0));
        return ingredientRepository.save(ingredient);
    }

    /**
     * Получает ингредиент по идентификатору.
     *
     * @param ingredientId идентификатор ингредиента
     * @return найденный ингредиент
     * @throws RuntimeException если ингредиент не найден
     */
    @Override
    public Ingredient getById(Short ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(new IngredientNotFoundException("Ингредиент с идентификатором " + ingredientId + " не найден" ));
    }

    /**
     * Получает ингредиент по названию.
     *
     * @param ingredientName название ингредиента
     * @return найденный ингредиент
     * @throws IngredientNotFoundException если ингредиент не найден
     */
    @Override
    public Ingredient getByName(String ingredientName) {
        return ingredientRepository.findByIngredientName(ingredientName)
                .orElseThrow(new IngredientNotFoundException("Ингредиент с именем " + ingredientName + " не найден"));
    }

    /**
     * Удаляет ингредиент по идентификатору.
     *
     * @param ingredientId идентификатор удаляемого ингредиента
     */
    @Override
    public void deleteById(Short ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    /**
     * Обновляет количество ингредиента по названию.
     *
     * @param ingredientName название ингредиента
     * @param amount новое значение количества
     */
    @Override
    public void updateAmountByName(String ingredientName, BigDecimal amount) {
        ingredientRepository.updateAmountByName(ingredientName, amount);
    }

    /**
     * Обновляет расчетную сумму ингредиента по названию с учетом прихода или расхода.
     *
     * @param ingredientName название ингредиента
     * @param amount величина изменения суммы
     * @param isReceipt признак поступления (`true`) или списания (`false`)
     */
    @Override
    @Transactional
    public void updateSettlementAmountByName(String ingredientName, BigDecimal amount, Boolean isReceipt) {
        Ingredient ingredient = getByName(ingredientName);
        if(isReceipt){
            ingredient.setSettlementAmount(ingredient.getSettlementAmount().add(amount));
        } else {
            ingredient.setSettlementAmount(ingredient.getSettlementAmount().subtract(amount));
        }
        ingredientRepository.save(ingredient);
    }

    /**
     * Обновляет количество ингредиента по идентификатору.
     *
     * @param ingredientId идентификатор ингредиента
     * @param amount новое значение количества
     */
    @Override
    public void updateAmountById(Short ingredientId, BigDecimal amount) {
        ingredientRepository.updateAmountById(ingredientId, amount);
    }

    /**
     * Обновляет расчетную сумму ингредиента по идентификатору с учетом прихода или расхода.
     *
     * @param ingredientId идентификатор ингредиента
     * @param amount величина изменения суммы
     * @param isReceipt признак поступления (`true`) или списания (`false`)
     */
    @Override
    public void updateSettlementAmountById(Short ingredientId, BigDecimal amount, Boolean isReceipt) {
        Ingredient ingredient = getById(ingredientId);
        if(isReceipt){
            ingredient.setSettlementAmount(ingredient.getSettlementAmount().add(amount));
        } else {
            ingredient.setSettlementAmount(ingredient.getSettlementAmount().subtract(amount));
        }
        ingredientRepository.save(ingredient);
    }


}
