package com.user_inventory_manager.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqEmailDTO {

    @NotBlank(message = "Email is required")
    private String email;
}
