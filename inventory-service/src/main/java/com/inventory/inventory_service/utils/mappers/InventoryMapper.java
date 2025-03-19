package com.inventory.inventory_service.utils.mappers;

import com.inventory.inventory_service.dto.response.ResInventoryDTO;
import com.inventory.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "item_type.itemTypeName", target = "item_type" )
    @Mapping(source = "brand.brandName", target = "brand" )
    ResInventoryDTO InventoryToResInventoryDTO(Inventory inventory);
    List<ResInventoryDTO> PageToListInventoryDto(Page<Inventory> inventoryPage);

}
