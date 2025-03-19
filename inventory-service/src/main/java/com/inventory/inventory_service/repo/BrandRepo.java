package com.inventory.inventory_service.repo;

import com.inventory.inventory_service.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Integer> {

}
