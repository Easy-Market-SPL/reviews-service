package co.edu.javeriana.easymarket.reviewsservice.repository;

import co.edu.javeriana.easymarket.reviewsservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}