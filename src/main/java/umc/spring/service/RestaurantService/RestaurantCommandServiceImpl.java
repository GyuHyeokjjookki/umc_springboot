package umc.spring.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.RegionHandler;
import umc.spring.converter.RestaurantConverter;
import umc.spring.converter.RestaurantRegionConverter;
import umc.spring.domain.Region;
import umc.spring.domain.Restaurant;
import umc.spring.domain.mapping.RestaurantRegion;
import umc.spring.repository.RegionRepository;
import umc.spring.repository.RestaurantRepository;
import umc.spring.web.dto.RestaurantRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantCommandServiceImpl implements RestaurantCommandService{

    private final RestaurantRepository restaurantRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public Restaurant addRestaurant(RestaurantRequestDTO.AddDTO request) {

        Restaurant newRestaurant = RestaurantConverter.toRestaurant(request);

        List<Region> regionList = request.getLocatedRegion().stream()
                .map(region ->{
                    return regionRepository.findById(region).orElseThrow(() -> new RegionHandler(ErrorStatus.REGION_NOT_FOUND));
                }).collect(Collectors.toList());

        List<RestaurantRegion> restaurantRegionList = RestaurantRegionConverter.toRestaurantRegionList(regionList);

        restaurantRegionList.forEach(restaurantRegion -> {restaurantRegion.setRestaurant(newRestaurant);});

        return restaurantRepository.save(newRestaurant);
    }
}
