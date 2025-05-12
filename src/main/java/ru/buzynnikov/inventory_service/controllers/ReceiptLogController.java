package ru.buzynnikov.inventory_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.buzynnikov.inventory_service.controllers.dto.ReceiptOfIngredients;
import ru.buzynnikov.inventory_service.models.ReceiptLog;
import ru.buzynnikov.inventory_service.services.ReceiptLogService;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для работы с журналом поступлений ингредиентов
 */
@RestController
@RequestMapping("/api/v1/receipt")
public class ReceiptLogController {

    private final ReceiptLogService receiptLogService;

    public ReceiptLogController(ReceiptLogService receiptLogService) {
        this.receiptLogService = receiptLogService;
    }

    /**
     * Обработка POST запроса для сохранения записи о поступлении ингредиентов
     * @param ingredients Список поступивших ингредиентов (тело запроса)
     * @param invoiceNumber Номер накладной (из пути запроса)
     * @return ResponseEntity со списком созданных записей в журнале
     */
    @PostMapping("/{invoiceNumber}")
    public ResponseEntity<List<ReceiptLog>> saveReceiptLog(@RequestBody List<ReceiptOfIngredients> ingredients, @PathVariable String invoiceNumber){
        return ResponseEntity.ok(receiptLogService.receiptOfIngredients(ingredients, invoiceNumber));
    }

    /**
     * Обработка GET запроса для получения всех поступлений за указанный период
     * @param fromDate Начальная дата периода (параметр запроса)
     * @param toDate Конечная дата периода (параметр запроса)
     * @return ResponseEntity со списком записей из журнала за указанный период
     */
    @GetMapping
    public ResponseEntity<List<ReceiptLog>> getAllReceiptFromDateToDate(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate){
        return ResponseEntity.ok(receiptLogService.getAllReceiptsByTimePeriod(fromDate, toDate));
    }

}
