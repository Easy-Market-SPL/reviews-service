package co.edu.javeriana.easymarket.reviewsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_product")
public class OrderProduct {
    @SequenceGenerator(name = "order_product_id_gen", sequenceName = "order_id_order_seq", allocationSize = 1)
    @EmbeddedId
    private OrderProductId id;

    @MapsId("idOrder")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_order", nullable = false)
    private Order idOrder;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}