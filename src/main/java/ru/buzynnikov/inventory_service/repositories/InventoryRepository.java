package ru.buzynnikov.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.inventory_service.models.InventoryLog;


@Repository
public interface InventoryRepository extends JpaRepository<InventoryLog, Short> {

}
