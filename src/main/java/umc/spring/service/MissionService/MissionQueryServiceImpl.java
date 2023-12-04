package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService{

    private final MissionRepository missionRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public boolean existMissionById(Long id) {
        return missionRepository.existsById(id);
    }

    @Override
    @Transactional
    public Page<Mission> getMissionList(Long restaurantId, Integer page) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        Page<Mission> missionPage = missionRepository.findAllByRestaurant(restaurant, PageRequest.of(page, 10));
        return missionPage;
    }
}
