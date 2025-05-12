package ru.buzynnikov.inventory_service.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.inventory_service.controllers.dto.SaleIngredients;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.models.SaleLog;
import ru.buzynnikov.inventory_service.repositories.SaleLogRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultSaleLogService implements SaleLogService{

    private final SaleLogRepository saleLogRepository;

    private final IngredientService ingredientService;

    public DefaultSaleLogService(SaleLogRepository saleLogRepository, IngredientService ingredientService) {
        this.saleLogRepository = saleLogRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<SaleLog> addSaleLogs(List<SaleIngredients> saleIngredients, String saleNumber, LocalDateTime saleDate) {
        Map<Short, Ingredient> ingredientMap = createIngredientMap(findIngredients(saleIngredients));
        List<SaleLog> receiptLogs = saleIngredients.stream()
                .map(sale -> createSaleLog(sale, ingredientMap.get(sale.getIngredientId()), saleDate, saleNumber))
                .toList();

        return saleLogRepository.saveAll(receiptLogs);
    }

    @Override
    public String deleteBySaleNumber(String saleNumber) {
        return "Обновлено " +  saleLogRepository.setDeletedBySaleNumber(saleNumber) + " записей";
    }


    private Iterable<Ingredient> findIngredients(List<SaleIngredients> saleIngredients){
        return ingredientService.findAllById(saleIngredients.stream().map(SaleIngredients::getIngredientId).toList());
    }

    private Map<Short, Ingredient> createIngredientMap(Iterable<Ingredient> ingredients){
        List<Ingredient> ingredientsList = (List<Ingredient>) ingredients;
        return ingredientsList.stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }

    private SaleLog createSaleLog(SaleIngredients sale, Ingredient ingredient, LocalDateTime saleDate, String saleNumber){
        SaleLog saleLog = new SaleLog();
        saleLog.setSaleDate(saleDate);
        saleLog.setIngredient(ingredient);
        saleLog.setNumberSale(saleNumber);
        saleLog.setQuantity(sale.getQuantity());
        saleLog.setDeleted(false);
        return saleLog;
    }
}
