package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.Ingredient;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Short> {
    @Query("SELECT i FROM Ingredient i WHERE i.ingredientName = ?1")
    Optional<Ingredient> findByIngredientName(String ingredientName);

    @Modifying
    @Query("UPDATE Ingredient i SET i.amount = ?2 WHERE i.ingredientName = ?1")
    void updateAmountByName(String ingredientName, BigDecimal amount);

    @Modifying
    @Query("UPDATE Ingredient i SET i.amount = ?2 WHERE i.ingredientId = ?1")
    void updateAmountById(short ingredientId, BigDecimal amount);

}
