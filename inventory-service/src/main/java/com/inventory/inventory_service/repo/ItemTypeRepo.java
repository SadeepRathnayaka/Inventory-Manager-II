package com.inventory.inventory_service.repo;

import com.inventory.inventory_service.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepo extends JpaRepository<ItemType, Integer> {

}
