package co.edu.javeriana.easymarket.reviewsservice.services;

import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.CreateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.UpdateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.out_data.GetReviewAverageDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.ReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions.BadRequestException;
import co.edu.javeriana.easymarket.reviewsservice.exception.error_messages.LogicErrorMessages;
import co.edu.javeriana.easymarket.reviewsservice.mappers.ReviewMapper;
import co.edu.javeriana.easymarket.reviewsservice.models.Order;
import co.edu.javeriana.easymarket.reviewsservice.models.Product;
import co.edu.javeriana.easymarket.reviewsservice.models.Review;
import co.edu.javeriana.easymarket.reviewsservice.repository.*;
import co.edu.javeriana.easymarket.reviewsservice.services.helpers.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewsService {
    // Repositories:
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // Mappers:
    private final ReviewMapper reviewMapper;

    // Validation Service:
    private final ValidationService validationService;
    private final OrderRepository orderRepository;

    ///  GET ALL REVIEWS
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::reviewToreviewDTO)
                .collect(Collectors.toList());
    }

    ///  CREATE REVIEW
    public ReviewDTO createReview(CreateReviewDTO reviewDTO) {
        // Validate user and product existence
        validationService.validateExists(userRepository.findById(reviewDTO.idUser()), LogicErrorMessages.ReviewErrorMessages.failedToFindUserMessage(reviewDTO.idUser()));
        validationService.validateExists(productRepository.findById(reviewDTO.idProduct()), LogicErrorMessages.ReviewErrorMessages.failedToFindProductMessage(reviewDTO.idProduct()));

        // Take the product from the database and set it in the review
        Product product = productRepository.findById(reviewDTO.idProduct()).orElseThrow();

        Review review = new Review(reviewDTO, product);
        // Check if the user has purchased the product on the OrderProductRepository taking for account the product code and the user id of the order
        List<Order> userOrders = orderRepository.findOrdersByIdUser(reviewDTO.idUser());

        // See if the user has purchased the product
        try {
            boolean hasPurchased = userOrders.stream()
                    .anyMatch(order -> order.getOrderProducts().stream()
                            .anyMatch(orderProduct -> orderProduct.getId().getProductCode().equals(reviewDTO.idProduct())));

            review.setPurchasedReview(hasPurchased);
            Review savedReview = reviewRepository.save(review);
            return reviewMapper.reviewToreviewDTO(savedReview);
        } catch (Exception e) {
            throw new BadRequestException("Unable to check if the user has purchased the product: " + e.getMessage());
        }
    }

    ///  GET REVIEWS BY USER
    public List<ReviewDTO> getReviewsByUser(String idUser) {
        // Validate user existence
        validationService.validateExists(userRepository.findById(idUser), LogicErrorMessages.ReviewErrorMessages.failedToFindUserMessage(idUser));

        // Get reviews by user
        List<Review> reviews = reviewRepository.findReviewsByIdUser(idUser);
        return reviews.stream()
                .map(reviewMapper::reviewToreviewDTO)
                .collect(Collectors.toList());
    }

    ///  GET REVIEWS BY PRODUCT ID
    public List<ReviewDTO> getReviewsByProductId(String idProduct) {
        // Validate product existence
        validationService.validateExists(productRepository.findById(idProduct), LogicErrorMessages.ReviewErrorMessages.failedToFindProductMessage(idProduct));

        // Get the product from the product repository
        Product product = productRepository.findById(idProduct).orElseThrow();

        // Get reviews by product
        List<Review> reviews = reviewRepository.findReviewsByProductCode(product);
        return reviews.stream()
                .map(reviewMapper::reviewToreviewDTO)
                .collect(Collectors.toList());
    }

    ///  GET REVIEW BY ID
    public ReviewDTO getReviewById(Integer idReview) {
        // Validate review existence
        validationService.validateExists(reviewRepository.findById(idReview), LogicErrorMessages.ReviewErrorMessages.getReviewNotFoundMessage(String.valueOf(idReview)));

        // Get the review by ID
        Review review = reviewRepository.findById(idReview).orElseThrow();
        return reviewMapper.reviewToreviewDTO(review);
    }

    /// GET ALL AVERAGE CALIFICATIONS
    public List<GetReviewAverageDTO> getAllProductsAverageRatings() {
    List<Object[]> averageRatings = reviewRepository.findAllProductsAverageRatings();
    return averageRatings.stream()
        .map(result -> new GetReviewAverageDTO(
            (String) result[0],  // productId
            ((Double) result[1]).floatValue()  // averageRating
        ))
        .collect(Collectors.toList());
    }

    ///  GET AVERAGE CALIFICATION BY PRODUCT ID
    public GetReviewAverageDTO getReviewAverage(String idProduct){
        List<Review> reviews = reviewRepository.findReviewsByProductCode(productRepository.findById(idProduct).orElseThrow());
        double average = reviews.stream()
                .mapToDouble(Review::getCalification)
                .average()
                .orElse(0.0);
        return new GetReviewAverageDTO(idProduct, (float) average);
    }

    ///  UPDATE REVIEW
    public ReviewDTO updateReview(Integer idReview, UpdateReviewDTO updateReviewDTO) {
        validationService.validateExists(reviewRepository.findById(idReview), LogicErrorMessages.ReviewErrorMessages.getReviewNotFoundMessage(String.valueOf(idReview)));
        Review review = reviewRepository.findById(idReview).orElseThrow();

        // Update the review fields
        review.setCalification(updateReviewDTO.calification());
        if (updateReviewDTO.commentary().isPresent()) {
            review.setCommentary(updateReviewDTO.commentary().get());
        }

        // Save the updated review
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.reviewToreviewDTO(updatedReview);
    }

    ///  DELETE REVIEW
    public void deleteReview(Integer idReview) {
        // Validate review existence
        validationService.validateExists(reviewRepository.findById(idReview), LogicErrorMessages.ReviewErrorMessages.getReviewNotFoundMessage(String.valueOf(idReview)));

        // Delete the review
        reviewRepository.deleteById(idReview);
    }
}
