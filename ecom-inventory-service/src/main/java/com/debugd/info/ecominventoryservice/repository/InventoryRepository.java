package com.debugd.info.ecominventoryservice.repository;

import com.debugd.info.ecominventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
