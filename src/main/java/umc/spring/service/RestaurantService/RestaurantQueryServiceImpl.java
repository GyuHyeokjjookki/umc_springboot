package umc.spring.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryServiceImpl implements RestaurantQueryService{

    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public boolean existRestaurantById(Long id) {
        return restaurantRepository.existsById(id);
    }
}
