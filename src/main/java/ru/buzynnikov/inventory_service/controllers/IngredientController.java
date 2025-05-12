package ru.buzynnikov.inventory_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.services.IngredientService;

import java.net.URI;
import java.util.List;

/**
 * Контроллер для управления ингредиентами.
 */
@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    /**
     * Метод для поиска ингредиентов по совпадению в имени.
     *
     * @param name имя ингредиента.
     * @return список найденных ингредиентов, совпадающих с данным именем.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Ingredient>> findIngredientsByName(@PathVariable String name){
        return ResponseEntity.ok(ingredientService.getByName(name));
    }

    /**
     * Метод для добавления нового ингредиента.
     *
     * @param request объект запроса для создания ингредиента.
     * @param builder построитель URI для формирования адреса ресурса.
     * @return созданный ресурс с HTTP-статусом Created и ссылкой на новый ресурс.
     */
    @PostMapping
    public <T extends IngredientCreateRequest> ResponseEntity<Ingredient> addIngredient(@RequestBody T request, UriComponentsBuilder builder){
        Ingredient addedIngredient = ingredientService.create(request);
        URI location = builder.path("/api/v1/ingredients/{id}").buildAndExpand(addedIngredient.getId()).toUri();
        return ResponseEntity.created(location).body(addedIngredient);
    }

    /**
     * Метод для получения конкретного ингредиента по ID.
     *
     * @param id идентификатор ингредиента.
     * @return искомый ингредиент.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findById(@PathVariable Short id){
        return ResponseEntity.ok(ingredientService.getById(id));
    }

    /**
     * Метод для удаления ингредиента по ID.
     *
     * @param id идентификатор удаляемого ингредиента.
     * @return пустой отклик с HTTP статусом No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Short id){
        ingredientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }






}
