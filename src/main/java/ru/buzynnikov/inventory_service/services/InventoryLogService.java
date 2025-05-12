package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.ConductAnInventory;
import ru.buzynnikov.inventory_service.models.InventoryLog;

import java.util.List;

/**
 * Сервис для работы с журналом инвентаризации.
 * Обеспечивает проведение инвентаризации и управление статусами инвентаризационных записей.
 */
public interface InventoryLogService {

    /**
     * Проводит инвентаризацию для списка ингредиентов.
     * Создает или обновляет записи в журнале инвентаризации на основе предоставленных данных.
     *
     * @param conductAnInventory список DTO объектов с данными для проведения инвентаризации,
     *                           где каждый объект содержит ID ингредиента и информацию по инвентаризации
     * @return список созданных/обновленных записей журнала инвентаризации
     */
    List<InventoryLog> conductAnInventory(List<ConductAnInventory> conductAnInventory);

    /**
     * Закрывает текущую инвентаризацию.
     * Выполняет финализацию процесса инвентаризации, возможно:
     * - изменение статуса записей
     * - расчет итогов
     * - архивирование данных
     */
    void closeInventory();
}
