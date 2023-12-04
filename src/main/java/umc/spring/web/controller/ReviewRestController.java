package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.service.RestaurantService.RestaurantQueryService;
import umc.spring.service.ReviewService.ReviewCommandService;
import umc.spring.service.ReviewService.ReviewQueryService;
import umc.spring.validation.annotation.ExistMembers;
import umc.spring.validation.annotation.ExistRestaurants;
import umc.spring.validation.annotation.NegativePages;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/restaurants")
public class ReviewRestController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;
    private final RestaurantQueryService restaurantQueryService;

    @PostMapping("/{restaurantId}/reviews")
    public ApiResponse<ReviewResponseDTO.CreateReviewResultDTO> createReview(@RequestBody @Valid ReviewRequestDTO.ReviewDTO request,
                                                                             @ExistRestaurants @PathVariable(name = "restaurantId") Long restaurantId,
                                                                             @ExistMembers @RequestParam(name = "memberId") Long memberId){
        Review review = reviewCommandService.createReview(memberId, restaurantId, request);
        return ApiResponse.onSuccess(ReviewConverter.toCreateReviewResultDTO(review));
    }

    @GetMapping("/{restaurantId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조희 API", description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query string으로 page 번호를 입력해주세요.")
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
    public ApiResponse<ReviewResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistRestaurants @PathVariable(name = "restaurantId") Long restaurantId,
                                                                             @NegativePages @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = reviewQueryService.getReviewList(restaurantId, page - 1);
        return ApiResponse.onSuccess(ReviewConverter.reviewPreViewListDTO(reviewList));
    }

    @GetMapping("/{restaurantId}/me/reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "내가 작성한 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query string으로 page 번호를 입력해주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "restaurantId", description = "가게의 아이디, path variable 입니다."),
            @Parameter(name = "memberId", description = "회원의 아이디, query string 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<ReviewResponseDTO.MyReviewPreViewListDTO> getMyReviewList(@ExistRestaurants @PathVariable(name = "restaurantId") Long restaurantId,
                                                                                 @ExistMembers @RequestParam(name = "memberId") Long memberId,
                                                                                 @NegativePages @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = reviewQueryService.getMyReviewList(restaurantId, memberId, page - 1);
        Optional<Restaurant> restaurant = restaurantQueryService.findRestaurant(restaurantId);
        return ApiResponse.onSuccess(ReviewConverter.myReviewPreViewListDTO(reviewList, restaurant));
    }
}
