package ru.buzynnikov.inventory_service.services;


import ru.buzynnikov.inventory_service.controllers.dto.ReceiptOfIngredients;
import ru.buzynnikov.inventory_service.models.ReceiptLog;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptLogService {

    List<ReceiptLog> receiptOfIngredients(List<ReceiptOfIngredients> receiptOfIngredients, String invoiceNumber);

    List<ReceiptLog> getAllReceiptsByTimePeriod(LocalDate fromDate, LocalDate toDate);
}
