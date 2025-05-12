package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.ReceiptLog;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptLogRepository extends JpaRepository<ReceiptLog, Integer> {

    @Query("SELECT r FROM ReceiptLog r WHERE r.date BETWEEN :start AND :end")
    List<ReceiptLog> findByDateBetween(LocalDate start, LocalDate end);
}
