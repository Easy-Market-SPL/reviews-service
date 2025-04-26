package co.edu.javeriana.easymarket.reviewsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_gen")
    @SequenceGenerator(name = "order_id_gen", sequenceName = "order_id_order_seq", allocationSize = 1)
    @Column(name = "id_order", nullable = false)
    private Integer id;

    @Column(name = "id_user", nullable = false, length = 36)
    private String idUser;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "total", nullable = false)
    private Float total;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @OneToMany(mappedBy = "idOrder")
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

}