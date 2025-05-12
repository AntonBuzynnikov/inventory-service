package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.ConductAnInventory;
import ru.buzynnikov.inventory_service.models.InventoryLog;

import java.util.List;

public interface InventoryLogService {

    List<InventoryLog> conductAnInventory(List<ConductAnInventory> conductAnInventory);

    void closeInventory();
}
