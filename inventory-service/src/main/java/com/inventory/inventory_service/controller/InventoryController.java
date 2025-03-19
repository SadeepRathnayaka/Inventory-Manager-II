package com.inventory.inventory_service.controller;

import com.inventory.inventory_service.dto.request.ReqAddInventoryDTO;
import com.inventory.inventory_service.dto.request.ReqUpdateInventoryDTO;
import com.inventory.inventory_service.service.InventoryService;
import com.inventory.inventory_service.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(path = "/add-inventory")
    public ResponseEntity<StandardResponse> addInventory(@RequestBody ReqAddInventoryDTO reqAddInventoryDTO){
        return inventoryService.addInventory(reqAddInventoryDTO);
    }

    @PutMapping(path = "/update-inventory")
    public ResponseEntity<StandardResponse> updateInventory(@RequestBody ReqUpdateInventoryDTO reqUpdateInventoryDTO){
        return inventoryService.updateInventory(reqUpdateInventoryDTO);
    }

    @DeleteMapping(
            path = "delete-inventory",
            params = "inventoryId"
    )
    public ResponseEntity<StandardResponse> deleteInventory(@RequestParam(value = "inventoryId") int inventoryId){
        return inventoryService.deleteInventory(inventoryId);
    }

    @GetMapping(path = {"/search-inventory"}, params = {"page", "size"})
    public ResponseEntity<StandardResponse> searchInventory(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) Integer itemType,
            @RequestParam(required = false) List<Integer> brands
    ){
        return inventoryService.searchInventory(page, size, itemName, itemType, brands);
    }

    @GetMapping(path = {"/get-all-inventories"}, params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllInventories(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){
        return inventoryService.getAllInventories(page, size);
    }
}
