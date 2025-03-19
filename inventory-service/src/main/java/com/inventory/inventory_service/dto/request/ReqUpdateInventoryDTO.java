package com.inventory.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUpdateInventoryDTO {

    @NotNull(message = "Inventory id is required")
    private int inventory_id;

    @NotBlank(message = "Item name is required")
    private String inventoryName;

    private String inventoryDescription;

    @NotNull(message = "Item price is required")
    private float inventoryPrice;

    private LocalDate inventoryExpireDate;

    @NotNull(message = "Item type is required")
    private int item_type;

    @NotNull(message = "Item brand is required")
    private int brand;
}
