package ru.buzynnikov.inventory_service.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.inventory_service.controllers.dto.SaleIngredients;
import ru.buzynnikov.inventory_service.models.Ingredient;
import ru.buzynnikov.inventory_service.models.SaleLog;
import ru.buzynnikov.inventory_service.repositories.SaleLogRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Реализация сервиса для работы с журналом продаж ингредиентов.
 * Обеспечивает регистрацию новых продаж и управление записями о продажах.
 */
@Service
public class DefaultSaleLogService implements SaleLogService{

    private final SaleLogRepository saleLogRepository;

    private final IngredientService ingredientService;

    public DefaultSaleLogService(SaleLogRepository saleLogRepository, IngredientService ingredientService) {
        this.saleLogRepository = saleLogRepository;
        this.ingredientService = ingredientService;
    }

    /**
     * Добавляет новые записи о продажах ингредиентов в журнал.
     * @param saleIngredients список DTO с данными о проданных ингредиентах
     * @param saleNumber уникальный номер накладной/документа продажи
     * @param saleDate дата и время совершения продажи
     * @return список сохраненных записей о продажах
     */
    @Override
    public List<SaleLog> addSaleLogs(List<SaleIngredients> saleIngredients, String saleNumber, LocalDateTime saleDate) {
        Map<Short, Ingredient> ingredientMap = createIngredientMap(findIngredients(saleIngredients));
        List<SaleLog> receiptLogs = saleIngredients.stream()
                .map(sale -> createSaleLog(sale, ingredientMap.get(sale.getIngredientId()), saleDate, saleNumber))
                .toList();

        return saleLogRepository.saveAll(receiptLogs);
    }

    /**
     * Помечает записи о продажах как удаленные по номеру накладной.
     * @param saleNumber номер накладной для удаления
     * @return строку с количеством обновленных записей
     */
    @Override
    public String deleteBySaleNumber(String saleNumber) {
        return "Обновлено " +  saleLogRepository.setDeletedBySaleNumber(saleNumber) + " записей";
    }


    /**
     * Находит ингредиенты по списку ID из запроса продажи.
     * @param saleIngredients список DTO с данными о продажах
     * @return список найденных ингредиентов
     */
    private Iterable<Ingredient> findIngredients(List<SaleIngredients> saleIngredients){
        return ingredientService.findAllById(saleIngredients.stream().map(SaleIngredients::getIngredientId).toList());
    }

    /**
     * Создает карту ингредиентов для быстрого доступа по ID.
     * @param ingredients список ингредиентов
     * @return карта вида ID ингредиента -> объект ингредиента
     */
    private Map<Short, Ingredient> createIngredientMap(Iterable<Ingredient> ingredients){
        List<Ingredient> ingredientsList = (List<Ingredient>) ingredients;
        return ingredientsList.stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }

    /**
     * Создает новую запись о продаже ингредиента.
     * @param sale DTO с данными о продаже
     * @param ingredient объект ингредиента
     * @param saleDate дата и время продажи
     * @param saleNumber номер накладной
     * @return созданный объект записи о продаже
     */
    private SaleLog createSaleLog(SaleIngredients sale, Ingredient ingredient, LocalDateTime saleDate, String saleNumber){
        SaleLog saleLog = new SaleLog();
        saleLog.setSaleDate(saleDate);
        saleLog.setIngredient(ingredient);
        saleLog.setNumberSale(saleNumber);
        saleLog.setQuantity(sale.getQuantity());
        saleLog.setDeleted(false);
        return saleLog;
    }
}
