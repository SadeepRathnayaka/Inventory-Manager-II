package com.user_inventory_manager.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUserRegisterDTO {

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String rePassword;

    @NotBlank(message = "Display name is required")
    private String displayName;
}
