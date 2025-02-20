package com.ioteam.order_management_platform.menu.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ioteam.order_management_platform.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, UUID>, MenuCustomRepository {

	@Query("Select u.userId from User u join Restaurant r on r.owner.userId = u.userId join Menu m on m.restaurant.resId = r.resId where m.rmId = :rmId ")
	UUID getRestaurantOwnerId(@Param("rmId") UUID rmId);
}
