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

/**
 * Реализация сервиса для работы с журналом поступления ингредиентов.
 * Обеспечивает регистрацию новых поступлений и поиск по датам.
 */
@Service
public class DefaultReceiptLogService implements ReceiptLogService{

    private final ReceiptLogRepository receiptLogRepository;

    private final IngredientService ingredientService;

    public DefaultReceiptLogService(ReceiptLogRepository receiptLogRepository, IngredientService ingredientService) {
        this.receiptLogRepository = receiptLogRepository;
        this.ingredientService = ingredientService;
    }

    /**
     * Регистрирует поступление партии ингредиентов на склад.
     * @param receiptOfIngredients список DTO с данными о поступивших ингредиентах
     * @param invoiceNumber номер накладной/документа поставки
     * @return список сохраненных записей о поступлении
     */
    @Override
    public List<ReceiptLog> receiptOfIngredients(List<ReceiptOfIngredients> receiptOfIngredients, String invoiceNumber) {
        LocalDate createdAt = LocalDate.now();
        Map<Short, Ingredient> ingredientMap = createIngredientMap(findIngredients(receiptOfIngredients));
        List<ReceiptLog> receiptLogs = receiptOfIngredients.stream()
                .map(receipt -> createReceiptLog(receipt, ingredientMap.get(receipt.getIngredientId()), createdAt, invoiceNumber))
                .toList();

        return receiptLogRepository.saveAll(receiptLogs);
    }

    /**
     * Возвращает все записи о поступлениях за указанный период
     * @param fromDate начальная дата периода (включительно)
     * @param toDate конечная дата периода (включительно)
     * @return список записей о поступлениях
     * @throws IllegalArgumentException если начальная дата позже конечной
     */
    @Override
    public List<ReceiptLog> getAllReceiptsByTimePeriod(LocalDate fromDate, LocalDate toDate) {
        if(fromDate.isBefore(toDate)){
            return receiptLogRepository.findByDateBetween(fromDate, toDate);
        }
        throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
    }








    /**
     * Находит ингредиенты по списку ID из запроса поступления
     * @param receiptOfIngredients список DTO с данными о поступлении
     * @return список найденных ингредиентов
     */
    private Iterable<Ingredient> findIngredients(List<ReceiptOfIngredients> receiptOfIngredients){
        return ingredientService.findAllById(receiptOfIngredients.stream().map(ReceiptOfIngredients::getIngredientId).toList());
    }

    /**
     * Создает карту ингредиентов для быстрого доступа по ID
     * @param ingredients список ингредиентов
     * @return карта вида ID ингредиента -> объект ингредиента
     */
    private  Map<Short, Ingredient> createIngredientMap(Iterable<Ingredient> ingredients){
        List<Ingredient> ingredientsList = (List<Ingredient>) ingredients;
        return ingredientsList.stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }

    /**
     * Создает новую запись о поступлении ингредиента
     * @param receipt DTO с данными о поступлении
     * @param ingredient объект ингредиента
     * @param createdAt дата создания записи
     * @param invoiceNumber номер накладной
     * @return созданный объект записи о поступлении
     */
    private ReceiptLog createReceiptLog(ReceiptOfIngredients receipt, Ingredient ingredient, LocalDate createdAt, String invoiceNumber){
        ReceiptLog receiptLog = new ReceiptLog();
        receiptLog.setDate(createdAt);
        receiptLog.setIngredient(ingredient);
        receiptLog.setNumber(invoiceNumber);
        receiptLog.setAmount(receipt.getAmount());
        return receiptLog;
    }
}
