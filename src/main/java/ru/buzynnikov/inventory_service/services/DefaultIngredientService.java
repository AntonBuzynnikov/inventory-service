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


@Service
public class DefaultIngredientService implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final InventoryRepository inventoryRepository;



    public DefaultIngredientService(IngredientRepository ingredientRepository, InventoryRepository inventoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional()
    public <T extends IngredientCreateRequest> Ingredient create(T request) {
        Ingredient ingredient = new Ingredient();
        if (request instanceof FullIngredientCreateRequest fullRequest) {
            ingredient.setName(fullRequest.getName());
            ingredient.setWastePercent(fullRequest.getWastePercent());
        } else {
            ingredient.setName(request.getName());
            ingredient.setWastePercent(BigDecimal.valueOf(0));
        }

        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        createInventory(savedIngredient);
        return savedIngredient;
    }

    @Override
    public Ingredient getById(Short ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(new IngredientNotFoundException("Ингредиент с идентификатором " + ingredientId + " не найден"));
    }

    @Override
    public List<Ingredient> getByName(String ingredientName) {
        return ingredientRepository.findByIngredientName(ingredientName);
    }

    @Override
    @Transactional
    public void deleteById(Short ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public void updateWastePercent(Short id, BigDecimal newWastePercent) {
        ingredientRepository.updateWastePercentById(id, newWastePercent);
    }

    @Override
    public Iterable<Ingredient> findAllById(List<Short> ids) {
        return ingredientRepository.findAllById(ids);
    }

    private void createInventory(Ingredient ingredient){
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setIngredient(ingredient);
        inventoryRepository.save(inventoryLog);
    }


}
