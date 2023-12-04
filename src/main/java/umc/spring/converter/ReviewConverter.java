package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static ReviewResponseDTO.CreateReviewResultDTO toCreateReviewResultDTO(Review review){
        return ReviewResponseDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Review toReview(ReviewRequestDTO.ReviewDTO request){
        return Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .build();
    }

    public static ReviewResponseDTO.ReviewPreViewDTO reviewPreViewDTO(Review review){
        return ReviewResponseDTO.ReviewPreViewDTO.builder()
                .ownerNickName(review.getMember().getName())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt().toLocalDate())
                .content(review.getContent())
                .build();
    }

    public static ReviewResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO(Page<Review> reviewList){

        List<ReviewResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(ReviewConverter::reviewPreViewDTO).collect(Collectors.toList());

        return ReviewResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst((reviewList.isFirst()))
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }

    public static ReviewResponseDTO.MyReviewPreViewDTO myReviewPreViewDTO(Member member, Review review){
        return ReviewResponseDTO.MyReviewPreViewDTO.builder()
                .nickName(member.getName())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt().toLocalDate())
                .content(review.getContent())
                .build();
    }

    public static ReviewResponseDTO.MyReviewPreViewListDTO myReviewPreViewListDTO(Page<Review> myReviewList, Optional<Restaurant> restaurant){

        List<ReviewResponseDTO.MyReviewPreViewDTO> myReviewPreViewDTOList = myReviewList.stream()
                .map(review -> myReviewPreViewDTO(review.getMember(), review)).collect(Collectors.toList());

        return ReviewResponseDTO.MyReviewPreViewListDTO.builder()
                .restaurantName(restaurant.get().getName())
                .isLast(myReviewList.isLast())
                .isFirst(myReviewList.isFirst())
                .totalPage(myReviewList.getTotalPages())
                .totalElements(myReviewList.getTotalElements())
                .listSize(myReviewPreViewDTOList.size())
                .myReviewList(myReviewPreViewDTOList)
                .build();
    }
}
