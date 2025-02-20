package com.ioteam.order_management_platform.district.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.district.entity.District;

public interface DistrictRepository extends JpaRepository<District, UUID> {

	Optional<District> findByDistrictIdAndDeletedAtIsNull(UUID districtId);
}
