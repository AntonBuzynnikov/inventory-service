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

/**
 * Реализация сервиса для работы с журналом инвентаризации.
 * Обеспечивает проведение инвентаризации и управление записями инвентаризации.
 */
@Service
public class DefaultInventoryLogService implements InventoryLogService{

    private final InventoryRepository inventoryRepository;

    public DefaultInventoryLogService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Проводит инвентаризацию для указанного списка ингредиентов.
     * Обновляет количество и дату проведения для существующих записей инвентаризации.
     *
     * @param conductAnInventory список DTO с данными для инвентаризации (ID ингредиента и количество)
     * @return список обновленных записей инвентаризации
     */
    @Override
    public List<InventoryLog> conductAnInventory(List<ConductAnInventory> conductAnInventory) {

        // Устанавливаем текущую дату для всех записей инвентаризации
        LocalDate dateConduct = LocalDate.now();

        // Получаем существующие записи инвентаризации по ID ингредиентов
        List<InventoryLog> inventoryLogs =  inventoryRepository.findAllById(conductAnInventory.stream()
                .map(ConductAnInventory::getIngredientId)
                .toList());

        // Создаем мапу для быстрого поиска данных инвентаризации по ID ингредиента
        Map<Short, ConductAnInventory> conductMap = createConductMap(conductAnInventory);

        // Обновляем записи инвентаризации
        inventoryLogs.forEach(inventoryLog -> {
            ConductAnInventory conduct = conductMap.get(inventoryLog.getIngredient().getId());
            if (conduct != null) {
                inventoryLog.setQuantity(conduct.getQuantity());
                inventoryLog.setCreatedAt(dateConduct);
            }
        });

        // Сохраняем обновленные записи
        return inventoryRepository.saveAll(inventoryLogs);
    }

    @Override
    public void closeInventory() {
        // TODO: Реализовать логику закрытия инвентаризации
        // Возможные действия:
        // - Фиксация итогов
        // - Изменение статуса записей
        // - Генерация отчетов
    }




    /**
     * Создает мапу для быстрого доступа к данным инвентаризации по ID ингредиента.
     *
     * @param conductAnInventory список DTO с данными инвентаризации
     * @return карта, где ключ - ID ингредиента, значение - соответствующий DTO объект
     */
    private Map<Short, ConductAnInventory> createConductMap(List<ConductAnInventory> conductAnInventory){
        return conductAnInventory.stream()
                .collect(Collectors.toMap(ConductAnInventory::getIngredientId, Function.identity()));
    }

}
