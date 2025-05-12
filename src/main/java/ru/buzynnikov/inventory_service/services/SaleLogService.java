package ru.buzynnikov.inventory_service.services;

import ru.buzynnikov.inventory_service.controllers.dto.SaleIngredients;
import ru.buzynnikov.inventory_service.models.SaleLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с журналом продаж ингредиентов.
 * Обеспечивает регистрацию продаж и управление записями о продажах.
 */
public interface SaleLogService {

    /**
     * Добавляет записи о продажах ингредиентов в журнал.
     *
     * @param ingredients список DTO объектов с данными о проданных ингредиентах,
     *                    где каждый объект содержит ID ингредиента и количество
     * @param saleNumber уникальный номер документа продажи/накладной
     * @param saleDate дата и время совершения продажи
     * @return список созданных записей о продажах
     */
    List<SaleLog> addSaleLogs(List<SaleIngredients> ingredients, String saleNumber, LocalDateTime saleDate);

    /**
     * Удаляет все записи о продажах по номеру накладной.
     *
     * @param saleNumber номер накладной для удаления
     * @return сообщение о результате выполнения операции
     */
    String deleteBySaleNumber(String saleNumber);
}
