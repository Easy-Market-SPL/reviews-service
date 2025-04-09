package co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data;

import co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions.BadRequestException;
import co.edu.javeriana.easymarket.reviewsservice.exception.error_messages.LogicErrorMessages;

import java.util.Optional;

public record CreateReviewDTO(String idProduct, String idUser, Float calification, Optional<String> commentary) {
    public CreateReviewDTO {
        if (idProduct == null || idProduct.isBlank()) {
            throw new BadRequestException(LogicErrorMessages.ReviewErrorMessages.invalidDataArgument(idProduct));
        }
        if (idUser == null || idUser.isBlank()) {
            throw new BadRequestException(LogicErrorMessages.ReviewErrorMessages.invalidDataArgument(idProduct));
        }
        if (calification == null || calification < 0 || calification > 5) {
            throw new BadRequestException(LogicErrorMessages.ReviewErrorMessages.invalidCalification());
        }
    }
}
