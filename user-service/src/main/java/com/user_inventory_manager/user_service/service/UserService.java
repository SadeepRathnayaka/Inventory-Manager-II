package com.user_inventory_manager.user_service.service;

import com.user_inventory_manager.user_service.dto.request.*;
import com.user_inventory_manager.user_service.utils.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<StandardResponse> userRegister(ReqUserRegisterDTO reqUserRegisterDTO);

    ResponseEntity<StandardResponse> getUserDetails();

    ResponseEntity<StandardResponse> userLogin(ReqUserLoginDTO reqUserLoginDTO);

    ResponseEntity<StandardResponse> userUpdate(ReqUserUpdateDTO reqUserUpdateDTO);

    ResponseEntity<StandardResponse> forgotPassword(ReqEmailDTO reqEmailDTO);

    ResponseEntity<StandardResponse> resetPassword(ReqResetPwDTO reqResetPwDTO);
}
