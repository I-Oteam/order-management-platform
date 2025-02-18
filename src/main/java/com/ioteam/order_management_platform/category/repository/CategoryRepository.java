package com.ioteam.order_management_platform.category.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

	Optional<Category> findByRcIdAndDeletedAtIsNull(UUID rcId);

	Page<Category> findAllByDeletedAtIsNull(Pageable pageable);
}
