package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.SaleLog;

@Repository
public interface SaleLogRepository extends JpaRepository<SaleLog, Long> {

    @Modifying
    @Query("UPDATE SaleLog s SET s.deleted = true WHERE s.numberSale = :saleNumber")
    Integer setDeletedBySaleNumber(String saleNumber);
}
