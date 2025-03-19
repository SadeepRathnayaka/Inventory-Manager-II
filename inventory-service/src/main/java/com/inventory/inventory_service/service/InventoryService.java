package com.inventory.inventory_service.service;

import com.inventory.inventory_service.dto.paginated.PaginatedInventoryDTO;
import com.inventory.inventory_service.dto.request.ReqAddInventoryDTO;
import com.inventory.inventory_service.dto.request.ReqUpdateInventoryDTO;
import com.inventory.inventory_service.utils.StandardResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    ResponseEntity<StandardResponse> addInventory(ReqAddInventoryDTO reqAddInventoryDTO);

    ResponseEntity<StandardResponse> updateInventory(ReqUpdateInventoryDTO reqUpdateInventoryDTO);

    ResponseEntity<StandardResponse> deleteInventory(int inventoryId);

    ResponseEntity<StandardResponse> searchInventory(int page, int size, String itemName, Integer itemType, List<Integer> brands);

    ResponseEntity<StandardResponse> getAllInventories(int page, int size);
}
