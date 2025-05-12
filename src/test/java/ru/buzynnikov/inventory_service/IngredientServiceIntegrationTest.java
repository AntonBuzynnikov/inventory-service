package ru.buzynnikov.inventory_service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.buzynnikov.inventory_service.controllers.dto.FullIngredientCreateRequest;
import ru.buzynnikov.inventory_service.exceptions.IngredientNotFoundException;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.services.IngredientService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты для сервиса работы с ингредиентами.
 * Тесты используют реальную базу данных, которая инициализируется перед каждым тестом
 * и очищается после каждого теста.
 */
@SpringBootTest
@Sql(scripts = {"/schema.sql", "/test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/clean-up.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class IngredientServiceIntegrationTest {

    @Autowired
    private IngredientService ingredientService;

    /**
     * Тест проверяет корректность создания нового ингредиента.
     * Должен сохранить ингредиент в БД и вернуть созданный объект.
     */
    @Test
    void createShouldSaveAndReturnIngredient() {
        FullIngredientCreateRequest request = new FullIngredientCreateRequest();
        request.setName("Ingredient1");
        request.setWastePercent(BigDecimal.valueOf(0.1));

        Ingredient result = ingredientService.create(request);

        assertNotNull(result.getId());
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getWastePercent(), result.getWastePercent());

        Ingredient fromDb  = ingredientService.getById(result.getId());
        assertEquals(result.getId(), fromDb.getId());
    }

    /**
     * Тест проверяет получение ингредиента по ID.
     * Должен вернуть существующий ингредиент с корректными данными.
     */
    @Test
    void getByIdShouldReturnIngredientWhenExists(){
         Short existsId = 1;

         Ingredient result = ingredientService.getById(existsId);

         assertNotNull(result.getId());
         assertEquals(existsId, result.getId());
         assertEquals("Лосось", result.getName());
         assertEquals(BigDecimal.valueOf(0.13), result.getWastePercent());
    }

    /**
     * Тест проверяет поиск ингредиентов по имени.
     * Должен вернуть список всех ингредиентов с указанным именем.
     */
    @Test
    void getByNameShouldReturnIngredientMatching(){
        List<Ingredient> result = ingredientService.getByName("Лосось");

        assertEquals(2, result.size());
        assertEquals("Лосось", result.getFirst().getName());
    }

    /**
     * Тест проверяет удаление ингредиента по ID.
     * После удаления попытка получить ингредиент должна вызывать исключение.
     */
    @Test
    void deleteByIdShouldRemoveIngredient(){
        Short idToDelete = 3;
        assertNotNull(ingredientService.getById(idToDelete));

        ingredientService.deleteById(idToDelete);

        assertThrows(IngredientNotFoundException.class, () -> ingredientService.getById(idToDelete));
    }

    /**
     * Тест проверяет обновление процента отходов для ингредиента.
     * Должен изменить значение процента отходов для указанного ингредиента.
     */
    @Test
    void updateWastePercentageShouldChangeValue(){
        Short ingredientIdToUpdate = 1;
        BigDecimal newWastePercent = BigDecimal.valueOf(0.15);
        Ingredient getIngredient = ingredientService.getById(ingredientIdToUpdate);
        assertNotEquals(newWastePercent, getIngredient.getWastePercent());

        ingredientService.updateWastePercent(ingredientIdToUpdate, newWastePercent);

        Ingredient updatedIngredient = ingredientService.getById(ingredientIdToUpdate);

        assertEquals(newWastePercent, updatedIngredient.getWastePercent());
    }

    /**
     * Тест проверяет поиск нескольких ингредиентов по списку ID.
     * Должен вернуть список запрошенных ингредиентов.
     */
    @Test
    void findAllById_ShouldReturnRequestedIngredients() {

        List<Short> ids = List.of((short)1, (short)2);


        Iterable<Ingredient> result = ingredientService.findAllById(ids);

        assertEquals(2, ((List<Ingredient>)result).size());
        assertTrue(((List<Ingredient>)result).stream()
                .anyMatch(i -> i.getName().equals("Лосось")));
        assertTrue(((List<Ingredient>)result).stream()
                .anyMatch(i -> i.getName().equals("Лосось терияки")));
    }
}
