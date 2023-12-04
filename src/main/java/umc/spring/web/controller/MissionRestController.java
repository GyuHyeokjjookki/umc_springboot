package umc.spring.web.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.service.MissionService.MissionCommandService;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.service.RestaurantService.RestaurantQueryService;
import umc.spring.validation.annotation.ExistMembers;
import umc.spring.validation.annotation.ExistRestaurants;
import umc.spring.validation.annotation.NegativePages;
import umc.spring.web.dto.Mission.MissionRequestDTO;
import umc.spring.web.dto.Mission.MissionResponseDTO;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/restaurants")
public class MissionRestController {

    private final MissionCommandService missionCommandService;
    private final MissionQueryService missionQueryService;
    private final RestaurantQueryService restaurantQueryService;

    @PostMapping("/{restaurantId}/missions")
    public ApiResponse<MissionResponseDTO.AddMissionDTO> addMission(@RequestBody @Valid MissionRequestDTO.MissionDTO request,
                                                                    @ExistRestaurants @PathVariable(name = "restaurantId") Long restaurantId){
        Mission mission = missionCommandService.addMission(restaurantId, request);
        return ApiResponse.onSuccess(MissionConverter.toAddMissionDTO(mission));
    }

    @GetMapping("/{restaurantId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조희 API", description = "특정 가게의 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query string으로 page 번호를 입력해주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "restaurantId", description = "가게의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> getMissionList(@ExistRestaurants @PathVariable(name = "restaurantId") Long restaurantId,
                                                                               @NegativePages @RequestParam(name = "page") Integer page){
        Page<Mission> missionList = missionQueryService.getMissionList(restaurantId, page - 1);
        Optional<Restaurant> restaurant = restaurantQueryService.findRestaurant(restaurantId);
        return ApiResponse.onSuccess(MissionConverter.missionPreViewListDTO(missionList, restaurant));
    }
}
