package co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions;
import co.edu.javeriana.easymarket.reviewsservice.exception.BusinessException;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}