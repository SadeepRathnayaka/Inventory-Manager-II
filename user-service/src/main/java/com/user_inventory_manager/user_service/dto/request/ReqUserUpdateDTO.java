package com.user_inventory_manager.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUserUpdateDTO {

    private String userName;
    private String reUserName;
    private String email;
    private String displayName;
}
