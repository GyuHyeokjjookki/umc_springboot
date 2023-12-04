package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.RestaurantRepository;
import umc.spring.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService{

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    public Page<Review> getReviewList(Long restaurantId, Integer page) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        Page<Review> restaurantPage = reviewRepository.findAllByRestaurant(restaurant, PageRequest.of(page, 10));
        return restaurantPage;
    }

    @Override
    public Page<Review> getMyReviewList(Long restaurantId, Long memberId, Integer page) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        Member member = memberRepository.findById(memberId).get();

        Page<Review> reviewPage = reviewRepository.findAllByRestaurantAndMember(restaurant, member, PageRequest.of(page, 10));
        return reviewPage;
    }
}
