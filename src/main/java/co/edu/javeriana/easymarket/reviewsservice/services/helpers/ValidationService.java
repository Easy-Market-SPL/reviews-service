package co.edu.javeriana.easymarket.reviewsservice.services.helpers;

import co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions.BadRequestException;
import co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationService {
    public <T> void validateNotExists(Optional<T> entity, String errorMessage) {
        if (entity.isPresent()) {
            throw new BadRequestException(errorMessage);
        }
    }

    public <T> void validateExists(Optional<T> entity, String errorMessage) {
        entity.orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }
}