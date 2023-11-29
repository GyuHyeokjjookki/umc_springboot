package umc.spring.converter;


import umc.spring.domain.Region;
import umc.spring.domain.mapping.RestaurantRegion;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRegionConverter {

    public static List<RestaurantRegion> toRestaurantRegionList(List<Region> regionList){

        return regionList.stream()
                .map(region ->
                        RestaurantRegion.builder()
                                .region(region)
                                .build()
                ).collect(Collectors.toList());
    }
}
