package ru.buzynnikov.inventory_service.services;


import ru.buzynnikov.inventory_service.controllers.dto.ReceiptOfIngredients;
import ru.buzynnikov.inventory_service.models.ReceiptLog;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис для работы с журналом поступления ингредиентов.
 * Обеспечивает регистрацию поступлений и получение информации о них.
 */
public interface ReceiptLogService {

    /**
     * Регистрирует поступление ингредиентов на склад.
     * Создает записи в журнале поступлений для каждого ингредиента.
     *
     * @param receiptOfIngredients список DTO объектов с данными о поступивших ингредиентах,
     *                            где каждый объект содержит ID ингредиента, количество и другие параметры
     * @param invoiceNumber номер накладной/документа о поступлении
     * @return список созданных записей о поступлении
     */
    List<ReceiptLog> receiptOfIngredients(List<ReceiptOfIngredients> receiptOfIngredients, String invoiceNumber);

    /**
     * Возвращает все записи о поступлениях за указанный период времени.
     *
     * @param fromDate начальная дата периода (включительно)
     * @param toDate конечная дата периода (включительно)
     * @return список записей о поступлениях за указанный период
     * @throws IllegalArgumentException если fromDate > toDate
     */
    List<ReceiptLog> getAllReceiptsByTimePeriod(LocalDate fromDate, LocalDate toDate) throws IllegalArgumentException;
}
