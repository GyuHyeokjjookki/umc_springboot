package umc.spring.service.RestaurantService;

import umc.spring.domain.Restaurant;
import umc.spring.web.dto.RestaurantRequestDTO;

public interface RestaurantCommandService {

    public Restaurant addRestaurant(RestaurantRequestDTO.AddDTO request);
}
