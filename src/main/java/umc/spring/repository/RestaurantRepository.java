package umc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
