package co.edu.javeriana.easymarket.reviewsservice.controllers;

import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.CreateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.UpdateReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.out_data.GetReviewAverageDTO;
import co.edu.javeriana.easymarket.reviewsservice.dtos.ReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.services.ReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewsService reviewService;

    ///  GET ALL REVIEWS
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    ///  GET REVIEWS BY USER
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable String idUser) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUser(idUser);
        return ResponseEntity.ok(reviews);
    }

    ///  GET REVIEWS BY PRODUCT
    @GetMapping("/product/{idProduct}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProduct(@PathVariable String idProduct) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(idProduct);
        return ResponseEntity.ok(reviews);
    }

    ///  GET ALL PRODUCTS AVERAGE CALIFICATIONS
    @GetMapping("/average")
    public ResponseEntity<List<GetReviewAverageDTO>> getAllProductsAverageRatings() {
        List<GetReviewAverageDTO> averages = reviewService.getAllProductsAverageRatings();
        return ResponseEntity.ok(averages);
    }

    ///  GET REVIEW AVERAGE BY ID
    @GetMapping("/average/{idProduct}")
    public ResponseEntity<GetReviewAverageDTO> getReviewAverageById(@PathVariable String idProduct) {
        GetReviewAverageDTO average = reviewService.getReviewAverage(idProduct);
        return ResponseEntity.ok(average);
    }

    ///  GET REVIEW BY ID
    @GetMapping("/{idReview}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer idReview) {
        ReviewDTO review = reviewService.getReviewById(idReview);
        return ResponseEntity.ok(review);
    }

    ///  CREATE REVIEW
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody CreateReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    ///  UPDATE REVIEW
    @PutMapping("/{idReview}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Integer idReview, @RequestBody UpdateReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(idReview, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    ///  DELETE REVIEW
    @DeleteMapping("/{idReview}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer idReview) {
        reviewService.deleteReview(idReview);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
