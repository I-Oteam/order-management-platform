package com.ioteam.order_management_platform.category.repository;

import com.ioteam.order_management_platform.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
