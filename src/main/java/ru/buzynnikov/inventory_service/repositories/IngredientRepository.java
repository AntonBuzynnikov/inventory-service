package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.Ingredient;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Short> {
    @Query("SELECT i FROM Ingredient i WHERE i.name LIKE %:ingredientName%")
    List<Ingredient> findByIngredientName(@Param("ingredientName") String ingredientName);

    @Modifying
    @Query("UPDATE Ingredient i SET i.wastePercent = :wastePercent WHERE i.id = :id")
    void updateWastePercentById(Short id, BigDecimal wastePercent);


}
