package com.inventory.inventory_service.service.impl;

import com.inventory.inventory_service.dto.paginated.PaginatedInventoryDTO;
import com.inventory.inventory_service.dto.request.ReqAddInventoryDTO;
import com.inventory.inventory_service.dto.request.ReqUpdateInventoryDTO;
import com.inventory.inventory_service.dto.response.ResInventoryDTO;
import com.inventory.inventory_service.entity.Inventory;
import com.inventory.inventory_service.exception.AlreadyExistsException;
import com.inventory.inventory_service.exception.NotFoundException;
import com.inventory.inventory_service.repo.BrandRepo;
import com.inventory.inventory_service.repo.InventoryRepo;
import com.inventory.inventory_service.repo.ItemTypeRepo;
import com.inventory.inventory_service.service.InventoryService;
import com.inventory.inventory_service.utils.StandardResponse;
import com.inventory.inventory_service.utils.mappers.InventoryMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceIMPL implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ItemTypeRepo itemTypeRepo;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public ResponseEntity<StandardResponse> addInventory(ReqAddInventoryDTO reqAddInventoryDTO) {

        Inventory inventory = modelMapper.map(reqAddInventoryDTO, Inventory.class);
        inventory.setItem_type(itemTypeRepo.getReferenceById(reqAddInventoryDTO.getItem_type()));
        inventory.setBrand(brandRepo.getReferenceById(reqAddInventoryDTO.getBrand()));

        if (inventoryRepo.existsByInventoryName(inventory.getInventoryName())) {
            throw new AlreadyExistsException("Inventory Already Exists");}

        inventoryRepo.save(inventory);
        ResInventoryDTO resInventoryDTO = modelMapper.map(inventory, ResInventoryDTO.class);
        resInventoryDTO.setBrand(brandRepo.getReferenceById(reqAddInventoryDTO.getBrand()).getBrandName());
        resInventoryDTO.setItem_type(itemTypeRepo.getReferenceById(reqAddInventoryDTO.getItem_type()).getItemTypeName());
        return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", resInventoryDTO));
    }

    @Override
    public ResponseEntity<StandardResponse> updateInventory(ReqUpdateInventoryDTO reqUpdateInventoryDTO) {

        Inventory inventory = inventoryRepo.getReferenceById(reqUpdateInventoryDTO.getInventory_id());
        if (inventory != null){
            inventory.setInventoryName(reqUpdateInventoryDTO.getInventoryName());
            inventory.setInventoryDescription(reqUpdateInventoryDTO.getInventoryDescription());
            inventory.setInventoryPrice(reqUpdateInventoryDTO.getInventoryPrice());
            inventory.setInventoryExpireDate(reqUpdateInventoryDTO.getInventoryExpireDate());
            inventory.setItem_type(itemTypeRepo.getReferenceById(reqUpdateInventoryDTO.getItem_type()));
            inventory.setBrand(brandRepo.getReferenceById(reqUpdateInventoryDTO.getBrand()));

            inventoryRepo.save(inventory);
            return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", "Inventory Updated Successfully"));
        }
        throw new NotFoundException("Inventory Not Found");
    }

    @Override
    public ResponseEntity<StandardResponse> deleteInventory(int inventoryId) {

        if (inventoryRepo.existsById(inventoryId)){
            inventoryRepo.deleteById(inventoryId);
            return ResponseEntity.ok(new StandardResponse(200,"DELETE", "Inventory Deleted Successfully"));
        }throw new NotFoundException("Inventory Not Found");
    }

    @Override
    public ResponseEntity<StandardResponse> searchInventory(int page, int size, String itemName, Integer itemType, List<Integer> brands) {

        Page<Inventory> inventoryPage = inventoryRepo.searchInventory(itemName, itemType, brands, PageRequest.of(page-1,size));
        List<ResInventoryDTO> resInventoryDTOS = inventoryMapper.PageToListInventoryDto(inventoryPage);
        long count = inventoryRepo.countInventory(itemName, itemType, brands);

        if (count > 0){
            PaginatedInventoryDTO paginatedInventoryDTO = new PaginatedInventoryDTO(resInventoryDTOS, count);
            return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", paginatedInventoryDTO));
        }throw new NotFoundException("Inventory Not Found") ;
    }

    @Override
    public ResponseEntity<StandardResponse> getAllInventories(int page, int size) {
        Page<Inventory> inventoryPage = inventoryRepo.findAll(PageRequest.of(page-1, size));
        List<ResInventoryDTO> resInventoryDTOS = inventoryMapper.PageToListInventoryDto(inventoryPage);
        long count = inventoryRepo.count();

        if (count > 0){
            PaginatedInventoryDTO paginatedInventoryDTO = new PaginatedInventoryDTO(resInventoryDTOS, count);
            return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", paginatedInventoryDTO));
        }throw new NotFoundException("Inventory Not Found") ;
    }
}
