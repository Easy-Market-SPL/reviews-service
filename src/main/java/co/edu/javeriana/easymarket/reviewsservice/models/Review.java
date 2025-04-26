package co.edu.javeriana.easymarket.reviewsservice.models;

import co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data.CreateReviewDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_gen")
    @SequenceGenerator(name = "review_id_gen", sequenceName = "review_id_review_seq", allocationSize = 1)
    @Column(name = "id_review", nullable = false)
    private Integer id;

    @Column(name = "calification", nullable = false)
    private Float calification;

    @Column(name = "commentary", length = 250)
    private String commentary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_code", nullable = false)
    private Product productCode;

    @Column(name = "id_user", nullable = false, length = 36)
    private String idUser;

    @ColumnDefault("false")
    @Column(name = "purchased_review")
    private Boolean purchasedReview;

    ///  Create Review from CreateReviewDTO
    public Review (CreateReviewDTO createReviewDTO, Product product) {
        this.setCalification(createReviewDTO.calification());
        this.setProductCode(product);
        this.setIdUser(createReviewDTO.idUser());

        // Set the comment if present, otherwise set a default value
        if (createReviewDTO.commentary().isPresent()) {
            this.setCommentary(createReviewDTO.commentary().get());
        }
    }

}