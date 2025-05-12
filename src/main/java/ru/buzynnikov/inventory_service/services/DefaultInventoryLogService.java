package ru.buzynnikov.inventory_service.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.inventory_service.controllers.dto.ConductAnInventory;

import ru.buzynnikov.inventory_service.models.InventoryLog;
import ru.buzynnikov.inventory_service.repositories.InventoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultInventoryLogService implements InventoryLogService{

    private final InventoryRepository inventoryRepository;

    public DefaultInventoryLogService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<InventoryLog> conductAnInventory(List<ConductAnInventory> conductAnInventory) {
        LocalDate dateConduct = LocalDate.now();
        List<InventoryLog> inventoryLogs =  inventoryRepository.findAllById(conductAnInventory.stream()
                .map(ConductAnInventory::getIngredientId)
                .toList());
        Map<Short, ConductAnInventory> conductMap = createConductMap(conductAnInventory);
        inventoryLogs.forEach(inventoryLog -> {
            ConductAnInventory conduct = conductMap.get(inventoryLog.getIngredient().getId());
            if (conduct != null) {
                inventoryLog.setQuantity(conduct.getQuantity());
                inventoryLog.setCreatedAt(dateConduct);
            }
        });


        return inventoryRepository.saveAll(inventoryLogs);
    }

    @Override
    public void closeInventory() {

    }




    private Map<Short, ConductAnInventory> createConductMap(List<ConductAnInventory> conductAnInventory){
        return conductAnInventory.stream()
                .collect(Collectors.toMap(ConductAnInventory::getIngredientId, Function.identity()));
    }

}
