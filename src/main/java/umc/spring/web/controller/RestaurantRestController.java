package umc.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.RestaurantConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Restaurant;
import umc.spring.service.RestaurantService.RestaurantCommandService;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;
import umc.spring.web.dto.RestaurantRequestDTO;
import umc.spring.web.dto.RestaurantResponseDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/restaurants")
public class RestaurantRestController {

    private final RestaurantCommandService restaurantCommandService;

    @PostMapping("/")
    public ApiResponse<RestaurantResponseDTO.AddResultDTO> add(@RequestBody @Valid RestaurantRequestDTO.AddDTO request){
        Restaurant restaurant = restaurantCommandService.addRestaurant(request);
        return ApiResponse.onSuccess(RestaurantConverter.toAddResultDTO(restaurant));
    }
}
