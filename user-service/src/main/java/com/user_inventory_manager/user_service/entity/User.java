package com.user_inventory_manager.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "otp" )
    private String otp;

    @Column(name = "otp_expire")
    private LocalDateTime otpExpire;

    public User(String userName, String email, String password, String displayName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }

}
