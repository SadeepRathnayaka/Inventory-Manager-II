package com.inventory.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventory {

    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventoryId;

    @Column(name = "name", nullable = false, length = 50)
    private String inventoryName;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String inventoryDescription;

    @Column(name = "price", nullable = false)
    private float inventoryPrice;

    @Column(name = "expiry_date")
    private LocalDate inventoryExpireDate;

    @ManyToOne
    @JoinColumn(name = "item_type_id", nullable = false)
    private ItemType item_type;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
}
