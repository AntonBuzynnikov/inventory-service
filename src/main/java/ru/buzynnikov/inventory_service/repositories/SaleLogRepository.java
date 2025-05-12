package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.SaleLog;

/**
 * Интерфейс репозитория для работы с журналом продаж (SaleLog).
 */
@Repository
public interface SaleLogRepository extends JpaRepository<SaleLog, Long> {

    /**
     * Запрос для отметки записи журнала продаж как удалённой.
     *
     * @param saleNumber номер продажи, которую нужно отметить как удалённую.
     * @return количество изменённых записей (обычно должно быть 1).
     */
    @Modifying
    @Query("UPDATE SaleLog s SET s.deleted = true WHERE s.numberSale = :saleNumber")
    Integer setDeletedBySaleNumber(String saleNumber);
}
