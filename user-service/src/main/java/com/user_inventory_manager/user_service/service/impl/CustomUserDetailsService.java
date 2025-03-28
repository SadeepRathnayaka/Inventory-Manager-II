package com.user_inventory_manager.user_service.service.impl;

import com.user_inventory_manager.user_service.entity.User;
import com.user_inventory_manager.user_service.repo.UserRepo;
import com.user_inventory_manager.user_service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);

        if (user == null){
            throw new UsernameNotFoundException("User Not Found");
        }else{
            CustomUserDetails customUserDetails = new CustomUserDetails(user.getUserName(), user.getPassword());
            return customUserDetails;
        }
    }

}
