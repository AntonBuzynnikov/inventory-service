package ru.buzynnikov.inventory_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.inventory_service.controllers.dto.IngredientCreateRequest;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.services.IngredientService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<List<Ingredient>> findIngredientsByName(@PathVariable String name){
        return ResponseEntity.ok(ingredientService.getByName(name));
    }

    @PostMapping
    public <T extends IngredientCreateRequest> ResponseEntity<Ingredient> addIngredient(@RequestBody T request, UriComponentsBuilder builder){
        Ingredient addedIngredient = ingredientService.create(request);
        URI location = builder.path("/api/v1/ingredients/{id}").buildAndExpand(addedIngredient.getId()).toUri();
        return ResponseEntity.created(location).body(addedIngredient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findById(@PathVariable Short id){
        return ResponseEntity.ok(ingredientService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Short id){
        ingredientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }






}
