package com.user_inventory_manager.user_service.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResUserDTO {

    private String userName;
    private String email;
    private String displayName;

}
