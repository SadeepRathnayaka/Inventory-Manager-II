package com.inventory.inventory_service.repo;

import com.inventory.inventory_service.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {

    boolean existsByInventoryName(String inventoryName);

    @Query(value =
            "SELECT i FROM Inventory i " +
            "WHERE (:itemName IS NULL OR i.inventoryName LIKE %:itemName%) " +
            "AND (:itemType IS NULL OR i.item_type.itemTypeId = :itemType) " +
            "AND (:brands IS NULL OR i.brand.brandId IN :brands)")
    Page<Inventory> searchInventory(
            @Param("itemName") String itemName,
            @Param("itemType") Integer itemType,
            @Param("brands") List<Integer> brands,
            Pageable pageable
    );

    @Query(value =
            "SELECT COUNT(*) FROM Inventory i " +
            "WHERE (:itemName IS NULL OR i.inventoryName LIKE %:itemName%) " +
            "AND (:itemType IS NULL OR i.item_type.itemTypeId = :itemType) " +
            "AND (:brands IS NULL OR i.brand.brandId IN :brands)")
    long countInventory(
            @Param("itemName") String itemName,
            @Param("itemType") Integer itemType,
            @Param("brands") List<Integer> brands
    );

}
