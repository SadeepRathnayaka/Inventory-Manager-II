package com.user_inventory_manager.user_service.controller;

import com.user_inventory_manager.user_service.dto.request.*;
import com.user_inventory_manager.user_service.service.UserService;
import com.user_inventory_manager.user_service.utils.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/user-register")
    public ResponseEntity<StandardResponse> userRegister(@Valid @RequestBody ReqUserRegisterDTO reqUserRegisterDTO){
        return userService.userRegister(reqUserRegisterDTO);
    }

    @PostMapping(path = "/user-login")
    public ResponseEntity<StandardResponse> userLogin(@Valid @RequestBody ReqUserLoginDTO reqUserLoginDTO){
        return userService.userLogin(reqUserLoginDTO);
    }

    @GetMapping(path = "/user-details"
    )
    public ResponseEntity<StandardResponse> getUserDetails(){
        return userService.getUserDetails();
    }

    @PutMapping(path = "/user-update")
    public ResponseEntity<StandardResponse> userUpdate(@RequestBody ReqUserUpdateDTO reqUserUpdateDTO){
        return userService.userUpdate(reqUserUpdateDTO);
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<StandardResponse> forgotPassword(@RequestBody ReqEmailDTO reqEmailDTO){
        return userService.forgotPassword(reqEmailDTO);
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<StandardResponse> resetPassword(@RequestBody ReqResetPwDTO reqResetPwDTO){
        return userService.resetPassword(reqResetPwDTO);
    }

}
