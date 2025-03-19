package com.user_inventory_manager.user_service.security;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CustomUserDetails extends User {

    public CustomUserDetails(String username, String password){
        super(username, password, true, true, true, true, Collections.emptyList());
    }
}
