package com.user_inventory_manager.user_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqResetPwDTO {

    private String email;
    private String otp;
    private String newPassword;
    private String reNewPassword;
}
