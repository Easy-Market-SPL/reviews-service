package co.edu.javeriana.easymarket.reviewsservice.mappers;

import co.edu.javeriana.easymarket.reviewsservice.dtos.ReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.UpdateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.models.Review;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class ReviewMapper {
    private final ModelMapper modelMapper;

    public ReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReviewDTO reviewToreviewDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }

    public Review reviewDTOToReview(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    /// Method for only update needed fields
    public void updateReviewFromDTO(UpdateReviewDTO reviewDTO, Review existingReview) {
        existingReview.setCalification(reviewDTO.calification());
        if (reviewDTO.commentary().isPresent()) {
            existingReview.setCommentary(reviewDTO.commentary().get());
        }
    }
}
