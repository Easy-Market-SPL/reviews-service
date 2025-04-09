package co.edu.javeriana.easymarket.reviewsservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    Integer id;
    Float calification;
    String commentary;
    String productCode;
    String idUser;
}