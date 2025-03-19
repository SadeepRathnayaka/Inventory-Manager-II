package com.inventory.inventory_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResInventoryDTO {

    private String inventoryName;
    private String inventoryDescription;
    private float inventoryPrice;
    private LocalDate inventoryExpireDate;
    private String item_type;
    private String brand;
}
