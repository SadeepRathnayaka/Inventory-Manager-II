package com.inventory.inventory_service.dto.paginated;

import com.inventory.inventory_service.dto.response.ResInventoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedInventoryDTO {

    private List<ResInventoryDTO> resInventoryDTOList;
    private long count;
}
