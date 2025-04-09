package co.edu.javeriana.easymarket.reviewsservice.repository;

import co.edu.javeriana.easymarket.reviewsservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}