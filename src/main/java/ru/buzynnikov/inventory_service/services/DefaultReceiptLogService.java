package ru.buzynnikov.inventory_service.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.inventory_service.controllers.dto.ReceiptOfIngredients;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.models.ReceiptLog;
import ru.buzynnikov.inventory_service.repositories.ReceiptLogRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultReceiptLogService implements ReceiptLogService{

    private final ReceiptLogRepository receiptLogRepository;

    private final IngredientService ingredientService;

    public DefaultReceiptLogService(ReceiptLogRepository receiptLogRepository, IngredientService ingredientService) {
        this.receiptLogRepository = receiptLogRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<ReceiptLog> receiptOfIngredients(List<ReceiptOfIngredients> receiptOfIngredients, String invoiceNumber) {
        LocalDate createdAt = LocalDate.now();
        Map<Short, Ingredient> ingredientMap = createIngredientMap(findIngredients(receiptOfIngredients));
        List<ReceiptLog> receiptLogs = receiptOfIngredients.stream()
                .map(receipt -> createReceiptLog(receipt, ingredientMap.get(receipt.getIngredientId()), createdAt, invoiceNumber))
                .toList();

        return receiptLogRepository.saveAll(receiptLogs);
    }

    @Override
    public List<ReceiptLog> getAllReceiptsByTimePeriod(LocalDate fromDate, LocalDate toDate) {
        if(fromDate.isBefore(toDate)){
            return receiptLogRepository.findByDateBetween(fromDate, toDate);
        }
        throw new RuntimeException("Дата начала не может быть позже даты окончания");
    }








    private Iterable<Ingredient> findIngredients(List<ReceiptOfIngredients> receiptOfIngredients){
        return ingredientService.findAllById(receiptOfIngredients.stream().map(ReceiptOfIngredients::getIngredientId).toList());
    }

    private  Map<Short, Ingredient> createIngredientMap(Iterable<Ingredient> ingredients){
        List<Ingredient> ingredientsList = (List<Ingredient>) ingredients;
        return ingredientsList.stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }

    private ReceiptLog createReceiptLog(ReceiptOfIngredients receipt, Ingredient ingredient, LocalDate createdAt, String invoiceNumber){
        ReceiptLog receiptLog = new ReceiptLog();
        receiptLog.setDate(createdAt);
        receiptLog.setIngredient(ingredient);
        receiptLog.setNumber(invoiceNumber);
        receiptLog.setAmount(receipt.getAmount());
        return receiptLog;
    }
}
