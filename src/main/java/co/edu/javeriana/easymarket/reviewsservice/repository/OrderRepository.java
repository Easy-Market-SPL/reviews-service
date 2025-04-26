package co.edu.javeriana.easymarket.reviewsservice.repository;

import co.edu.javeriana.easymarket.reviewsservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findOrdersByIdUser(String idUser);
}