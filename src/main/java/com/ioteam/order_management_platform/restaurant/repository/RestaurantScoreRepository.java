package com.ioteam.order_management_platform.restaurant.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ioteam.order_management_platform.restaurant.entity.RestaurantScore;

public interface RestaurantScoreRepository extends JpaRepository<RestaurantScore, UUID> {

	@Modifying
	@Query("update RestaurantScore rs set rs.rsScore = "
		+ "(select round(avg(r.reviewScore), 1) "
		+ "from Review r "
		+ "where r.restaurant = rs.restaurant "
		+ "and r.reviewScore is not null "
		+ "and r.deletedAt is null "
		+ "and r.restaurant.deletedAt is null)")
	int bulkUpdateRestaurantScore();

	Optional<RestaurantScore> findByRestaurantResIdAndDeletedAtIsNull(UUID rsResId);
}
