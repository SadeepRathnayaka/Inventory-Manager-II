package com.user_inventory_manager.user_service.service.impl;

import com.user_inventory_manager.user_service.dto.request.*;
import com.user_inventory_manager.user_service.dto.respond.ResUserDTO;
import com.user_inventory_manager.user_service.entity.User;
import com.user_inventory_manager.user_service.exception.AlreadyExistsException;
import com.user_inventory_manager.user_service.exception.InvalidOTPException;
import com.user_inventory_manager.user_service.exception.MisMatchException;
import com.user_inventory_manager.user_service.repo.UserRepo;
import com.user_inventory_manager.user_service.security.JWTService;
import com.user_inventory_manager.user_service.service.UserService;
import com.user_inventory_manager.user_service.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public ResponseEntity<StandardResponse> userRegister(ReqUserRegisterDTO reqUserRegisterDTO) {

        if (!reqUserRegisterDTO.getPassword().equals(reqUserRegisterDTO.getRePassword())){
            throw new MisMatchException("Passwords are not matched");
        } else if (userRepo.existsByUserName(reqUserRegisterDTO.getUserName())) {
            throw new AlreadyExistsException("Username is taken");
        }
        else{
            User user = new User(
                    reqUserRegisterDTO.getUserName(),
                    reqUserRegisterDTO.getEmail(),
                    passwordEncoder.encode(reqUserRegisterDTO.getPassword()),
                    reqUserRegisterDTO.getDisplayName()
                    );

            userRepo.save(user);

            ResUserDTO resUserDTO = new ResUserDTO() ;
            resUserDTO.setUserName(user.getUserName());
            resUserDTO.setEmail(user.getEmail());
            resUserDTO.setDisplayName(user.getDisplayName());
            return ResponseEntity.ok(new StandardResponse(200, "User Registration Successful", resUserDTO));
        }
    }

    @Override
    public ResponseEntity<StandardResponse> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()){
            User user = userRepo.findByUserName(authentication.getName());

            ResUserDTO resUserDTO = new ResUserDTO(
                    user.getUserName(),
                    user.getEmail(),
                    user.getDisplayName()
            ) ;

            return ResponseEntity.ok(new StandardResponse(200,"SUCCESS", resUserDTO));
        }
        return null;
    }

    @Override
    public ResponseEntity<StandardResponse> userLogin(ReqUserLoginDTO reqUserLoginDTO) {
        Authentication authentication;
    try {
         authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                reqUserLoginDTO.getUserName(),
                                reqUserLoginDTO.getPassword()));

    }catch (BadCredentialsException e){
        throw new BadCredentialsException("Incorrect Credentials");
    }
        if (authentication.isAuthenticated()){
            System.out.println(jwtService.generateToken(reqUserLoginDTO.getUserName()));
            User user = userRepo.findByUserName(reqUserLoginDTO.getUserName());

            ResUserDTO resUserDTO = new ResUserDTO(
                    user.getUserName(),
                    user.getEmail(),
                    user.getDisplayName()
                    );

            return ResponseEntity.ok(new StandardResponse(200, "Login Successful", resUserDTO));
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public ResponseEntity<StandardResponse> userUpdate(ReqUserUpdateDTO reqUserUpdateDTO) {

            User user = userRepo.findByUserName(reqUserUpdateDTO.getUserName());

            if (! user.getEmail().equals(reqUserUpdateDTO.getEmail())){
                boolean isEmailExists = userRepo.existsByEmail(reqUserUpdateDTO.getEmail());
                if (isEmailExists){ throw new AlreadyExistsException("Email Already Exists");}
                user.setEmail(reqUserUpdateDTO.getEmail());

            }if (! user.getUserName().equals(reqUserUpdateDTO.getReUserName())){
                boolean isUserNameExists = userRepo.existsByUserName(reqUserUpdateDTO.getReUserName());
                if (isUserNameExists) { throw new AlreadyExistsException("Username Already Exists");}
                user.setUserName(reqUserUpdateDTO.getReUserName());
            }

            user.setDisplayName(reqUserUpdateDTO.getDisplayName());
            userRepo.save(user);
            return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", "User Update Successfully"));
    }

    @Override
    public ResponseEntity<StandardResponse> forgotPassword(ReqEmailDTO reqEmailDTO) {
        User user = userRepo.findByEmail(reqEmailDTO.getEmail());

        if (user != null){
            user.setOtp(this.generateOTP());
            user.setOtpExpire(LocalDateTime.now().plusMinutes(5));
            userRepo.save(user);
            return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", "OTP has been sent to the email"));
        }else{
            throw new UsernameNotFoundException("Email Not Found");
        }
    }

    @Override
    public ResponseEntity<StandardResponse> resetPassword(ReqResetPwDTO reqResetPwDTO) {

        if (! reqResetPwDTO.getNewPassword().equals(reqResetPwDTO.getReNewPassword())){
            throw new MisMatchException("Passwords are not matched");
        }
        if(! this.validateOTP(reqResetPwDTO.getOtp())){
            throw new InvalidOTPException("Invalid OTP");
        }

        User user = userRepo.findByOtp(reqResetPwDTO.getOtp());
        user.setPassword(passwordEncoder.encode(reqResetPwDTO.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpire(null);
        userRepo.save(user);
        return ResponseEntity.ok(new StandardResponse(200, "SUCCESS" ,"Password updated successfully"));

    }

    public String generateOTP(){
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }

    public boolean validateOTP(String otp){
        User user = userRepo.findByOtp(otp);

        if (user != null){
            return user.getOtpExpire().isAfter(LocalDateTime.now());
        }return false;
    }
}
