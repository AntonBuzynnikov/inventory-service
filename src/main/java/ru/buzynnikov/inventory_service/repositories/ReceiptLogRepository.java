package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.ReceiptLog;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для работы с журналом приходов ингредиентов.
 */
@Repository
public interface ReceiptLogRepository extends JpaRepository<ReceiptLog, Integer> {

    /**
     * Запрос для выборки записей журнала приходов за указанный диапазон дат.
     *
     * @param start начальная дата диапазона.
     * @param end   конечная дата диапазона.
     * @return список записей журнала приходов за указанный период.
     */
    @Query("SELECT r FROM ReceiptLog r WHERE r.date BETWEEN :start AND :end")
    List<ReceiptLog> findByDateBetween(LocalDate start, LocalDate end);
}
