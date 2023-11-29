package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.RegionHandler;
import umc.spring.apiPayload.exception.handler.ReviewHandler;
import umc.spring.converter.RestaurantConverter;
import umc.spring.converter.RestaurantRegionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.RestaurantRegion;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.RestaurantRepository;
import umc.spring.repository.ReviewRepository;
import umc.spring.web.dto.RestaurantRequestDTO;
import umc.spring.web.dto.ReviewRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public Review createReview(Long memberId, Long restaurantId, ReviewRequestDTO.ReviewDTO request) {

        Review newReview = ReviewConverter.toReview(request);

        memberRepository.findById(memberId).orElseThrow(() -> new ReviewHandler(ErrorStatus.MEMBER_NOT_FOUND));
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new ReviewHandler(ErrorStatus.RESTAURANT_NOT_FOUND));

        newReview.setMember(memberRepository.findById(memberId).get());
        newReview.setRestaurant(restaurantRepository.findById(restaurantId).get());

        return reviewRepository.save(newReview);
    }
}
