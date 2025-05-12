package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.Ingredient;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс репозитория для работы с ингредиентами.
 */
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Short> {
    /**
     * Запрос для поиска ингредиентов по частичному совпадению названия.
     *
     * @param ingredientName строка, содержащая фрагмент названия ингредиента.
     * @return список ингредиентов, соответствующих запросу.
     */
    @Query("SELECT i FROM Ingredient i WHERE i.name LIKE %:ingredientName%")
    List<Ingredient> findByIngredientName(@Param("ingredientName") String ingredientName);

    /**
     * Модифицирующий запрос для обновления процента отходов у определенного ингредиента.
     *
     * @param id           идентификатор ингредиента.
     * @param wastePercent обновленное значение процента отходов.
     */
    @Modifying
    @Query("UPDATE Ingredient i SET i.wastePercent = :wastePercent WHERE i.id = :id")
    void updateWastePercentById(Short id, BigDecimal wastePercent);


}
