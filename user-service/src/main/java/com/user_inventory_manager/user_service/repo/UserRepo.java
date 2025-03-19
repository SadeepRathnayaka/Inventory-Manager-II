package com.user_inventory_manager.user_service.repo;

import com.user_inventory_manager.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByUserName(String userName);

    User findByUserName(String username);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    boolean existsByOtp(String otp);

    User findByOtp(String otp);
}
