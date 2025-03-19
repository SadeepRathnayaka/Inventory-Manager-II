package com.inventory.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "item_type")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemType {

    @Id
    @Column(name = "item_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemTypeId;

    @Column(name = "name")
    private String itemTypeName;

    @OneToMany(mappedBy = "item_type")
    private List<Inventory> inventoryList;
}
