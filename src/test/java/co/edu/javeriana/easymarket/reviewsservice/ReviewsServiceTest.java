package co.edu.javeriana.easymarket.reviewsservice;

import co.edu.javeriana.easymarket.reviewsservice.dtos.ReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.CreateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.UpdateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.out_data.GetReviewAverageDTO;
import co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions.BadRequestException;
import co.edu.javeriana.easymarket.reviewsservice.models.Product;
import co.edu.javeriana.easymarket.reviewsservice.models.Review;
import co.edu.javeriana.easymarket.reviewsservice.models.User;
import co.edu.javeriana.easymarket.reviewsservice.repository.ProductRepository;
import co.edu.javeriana.easymarket.reviewsservice.repository.ReviewRepository;
import co.edu.javeriana.easymarket.reviewsservice.repository.UserRepository;
import co.edu.javeriana.easymarket.reviewsservice.services.ReviewsService;
import co.edu.javeriana.easymarket.reviewsservice.services.helpers.ValidationService;
import co.edu.javeriana.easymarket.reviewsservice.mappers.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewsServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ReviewsService reviewsService;

    private Review review;
    private Product product;

    @BeforeEach
    void setUp() {
        // Set up mock data for the test cases
        product = new Product();
        product.setCode("A12345");
        product.setName("Test Product");

        review = new Review(new CreateReviewDTO("A12345", "8e73a3da-41cc-455b-b8b0-2e1ae6296df9", 4.5f, Optional.of("Good product")), product);

        // Mock methods
        when(reviewMapper.reviewToreviewDTO(any(Review.class))).thenReturn(new ReviewDTO(1, 4.5f, "Good product", "productId", "userId", true));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
    }

    @Test
    void testCreateReview() {
        CreateReviewDTO createReviewDTO = new CreateReviewDTO("productId", "userId", 4.5f, Optional.of("Good product"));
        when(userRepository.findById("userId")).thenReturn(Optional.of(new User()));
        when(productRepository.findById("productId")).thenReturn(Optional.of(product));

        // Call service method to create review
        ReviewDTO result = reviewsService.createReview(createReviewDTO);

        // Verifying the behavior with success messages
        assertNotNull(result, "The ReviewDTO should not be null after creating a review");
        assertEquals(4.5f, result.getCalification(), "The calification should be 4.5f when creating a review");
        assertEquals("Good product", result.getCommentary(), "The commentary should be 'Good product' when creating a review");
        assertEquals("productId", result.getProductCode(), "The product code should be 'productId'");
        assertEquals("userId", result.getIdUser(), "The user ID should be 'userId'");

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testGetReviewsByUser() {
        when(userRepository.findById("userId")).thenReturn(Optional.of(new User()));
        when(reviewRepository.findReviewsByIdUser("userId")).thenReturn(List.of(review));

        // Calling the service method
        List<ReviewDTO> reviews = reviewsService.getReviewsByUser("userId");

        // Verifying the behavior with success messages
        assertNotNull(reviews, "The list of reviews should not be null");
        assertFalse(reviews.isEmpty(), "The list of reviews should not be empty");
        assertEquals(1, reviews.size(), "The list of reviews should contain one review");
        assertEquals(4.5f, reviews.getFirst().getCalification(), "The calification of the first review should be 4.5f");
        assertEquals("productId", reviews.getFirst().getProductCode(), "The product code of the first review should be 'productId'");
    }

    @Test
    void testGetReviewById() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Calling the service method
        ReviewDTO reviewDTO = reviewsService.getReviewById(1);

        // Verifying the behavior with success messages
        assertNotNull(reviewDTO, "The ReviewDTO should not be null");
        assertEquals(4.5f, reviewDTO.getCalification(), "The calification should be 4.5f");
        assertEquals("Good product", reviewDTO.getCommentary(), "The commentary should be 'Good product'");
        assertEquals("productId", reviewDTO.getProductCode(), "The product code should be 'productId'");
        assertEquals("userId", reviewDTO.getIdUser(), "The user ID should be 'userId'");
    }

    @Test
    void testUpdateReviewThrowsExceptionWhenInvalidCalification() {
        assertThrows(BadRequestException.class, () -> new UpdateReviewDTO(Optional.of("Updated review Example"), 6.0f), "The calification should not be greater than 5");
    }

    @Test
    void testUpdateReviewThrowsExceptionWhenInvalidNegativeCalification() {
        assertThrows(BadRequestException.class, () -> new UpdateReviewDTO(Optional.of("Updated review Example"), -1.0f), "The calification should not be greater than 5");
    }

    @Test
    void testUpdateReview() {
        UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO(Optional.of("Updated review"), 4.8f);
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Configuring the mock for the mapper
        when(reviewMapper.reviewToreviewDTO(any(Review.class)))
                .thenReturn(new ReviewDTO(1, 4.8f, "Updated review", "productId", "userId", true));

        ReviewDTO updatedReview = reviewsService.updateReview(1, updateReviewDTO);

        // Verifying the behavior with success messages
        assertNotNull(updatedReview, "The ReviewDTO should not be null after the update");
        assertEquals(4.8f, updatedReview.getCalification(), "The calification should be 4.8f after the update");
    }

    @Test
    void testDeleteReview() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Calling the service method to delete the review
        reviewsService.deleteReview(1);

        // Verifying that delete method was called once
        verify(reviewRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetReviewAverage() {
        when(productRepository.findById("productId")).thenReturn(Optional.of(product));
        when(reviewRepository.findReviewsByProductCode(product)).thenReturn(List.of(review));

        // Calling the service method
        GetReviewAverageDTO averageDTO = reviewsService.getReviewAverage("productId");

        // Verifying the behavior with success messages
        assertNotNull(averageDTO, "The average review DTO should not be null");
        assertEquals(4.5f, averageDTO.average(), "The average calification should be 4.5f");
    }
}
