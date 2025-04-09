package co.edu.javeriana.easymarket.reviewsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "code", nullable = false, length = 200)
    private String code;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "img_url", length = 700)
    private String imgUrl;

    @OneToMany(mappedBy = "productCode")
    private Set<Review> reviews = new LinkedHashSet<>();

}