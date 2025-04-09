package co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions;

import co.edu.javeriana.easymarket.reviewsservice.exception.BusinessException;

public class BadRequestException extends BusinessException {
    public BadRequestException(String message) {
        super(message);
    }
}