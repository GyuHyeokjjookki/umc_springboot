package umc.spring.service.ReviewService;

import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;

public interface ReviewCommandService {

    public Review createReview(Long memberId, Long restaurantId, ReviewRequestDTO.ReviewDTO request);
}
