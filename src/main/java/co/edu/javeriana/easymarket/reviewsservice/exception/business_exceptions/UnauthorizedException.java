package co.edu.javeriana.easymarket.reviewsservice.exception.business_exceptions;

import co.edu.javeriana.easymarket.reviewsservice.exception.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String message) {
        super(message);
    }
}