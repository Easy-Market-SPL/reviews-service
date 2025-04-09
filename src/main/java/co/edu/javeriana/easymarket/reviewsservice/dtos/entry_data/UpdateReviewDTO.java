package co.edu.javeriana.easymarket.reviewsservice.dtos.entry_data;

import co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions.BadRequestException;
import co.edu.javeriana.easymarket.reviewsservice.exception.error_messages.LogicErrorMessages;

import java.util.Optional;

public record UpdateReviewDTO(Optional<String> commentary, Float calification){
    public UpdateReviewDTO {
        if (calification < 0 || calification > 5) {
            throw new BadRequestException(LogicErrorMessages.ReviewErrorMessages.invalidCalification());
        }
    }
}
