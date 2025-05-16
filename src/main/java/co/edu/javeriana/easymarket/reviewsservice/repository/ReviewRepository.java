package co.edu.javeriana.easymarket.reviewsservice.repository;

import co.edu.javeriana.easymarket.reviewsservice.models.Product;
import co.edu.javeriana.easymarket.reviewsservice.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
  List<Review> findReviewsByIdUser(String idUser);
  List<Review> findReviewsByProductCode(Product productCode);

  @Query("SELECT r.productCode.code as productId, AVG(r.calification) as averageRating " +
           "FROM Review r GROUP BY r.productCode.code")
  List<Object[]> findAllProductsAverageRatings();
}