package umc.spring.service.RestaurantService;

import umc.spring.domain.Restaurant;

import java.util.Optional;

public interface RestaurantQueryService {

    public boolean existRestaurantById(Long id);

    public Optional<Restaurant> findRestaurant(Long id);
}
