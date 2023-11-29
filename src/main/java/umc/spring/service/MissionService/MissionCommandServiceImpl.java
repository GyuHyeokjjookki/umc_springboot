package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MissionHandler;
import umc.spring.apiPayload.exception.handler.ReviewHandler;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.RestaurantRepository;
import umc.spring.web.dto.Mission.MissionRequestDTO;
import umc.spring.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionCommandServiceImpl implements MissionCommandService {

    private final RestaurantRepository restaurantRepository;
    private final MissionRepository missionRepository;

    @Override
    @Transactional
    public Mission addMission(Long restaurantId, MissionRequestDTO.MissionDTO request) {

        Mission newMission = MissionConverter.toMission(request);

        restaurantRepository.findById(restaurantId).orElseThrow(() -> new MissionHandler(ErrorStatus.RESTAURANT_NOT_FOUND));

        newMission.setRestaurant(restaurantRepository.findById(restaurantId).get());

        return missionRepository.save(newMission);
    }
}
