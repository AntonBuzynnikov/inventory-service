package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.SaleIngredients;
import ru.buzynnikov.inventory_service.models.SaleLog;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleLogService {

    List<SaleLog> addSaleLogs(List<SaleIngredients> ingredients, String saleNumber, LocalDateTime saleDate);

    String deleteBySaleNumber(String saleNumber);
}
