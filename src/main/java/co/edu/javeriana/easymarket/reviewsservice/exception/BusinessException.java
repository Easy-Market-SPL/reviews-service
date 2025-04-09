package co.edu.javeriana.easymarket.reviewsservice.exception;

public abstract class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}