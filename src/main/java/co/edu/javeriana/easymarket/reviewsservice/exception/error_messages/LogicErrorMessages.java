package co.edu.javeriana.easymarket.reviewsservice.exception.error_messages;

public class LogicErrorMessages {
    private LogicErrorMessages() {
        // Prevent instantiation
    }

    public static final class ReviewErrorMessages{
        private ReviewErrorMessages() {
            // Prevent instantiation
        }

        // Static Methods
        public static String getReviewNotFoundMessage(String reviewId) {
            return String.format("Review with ID %s not found", reviewId);
        }

        public static String failedToUpdateReviewMessage(String reviewId) {
            return String.format("Failed to update review with ID %s", reviewId);
        }

        public static String failedToDeleteReviewMessage(String reviewId) {
            return String.format("Failed to delete review with ID %s", reviewId);
        }

        public static String failedToFindUserMessage(String userId) {
            return String.format("Failed to create review, the user with ID %s doesn't exists", userId);
        }

        public static String failedToFindProductMessage(String productId) {
            return String.format("Failed to create review, the product with ID %s doesn't exists", productId);
        }

        public static String invalidDataArgument(String argumentName){
            return String.format("Invalid data provided. %s cannot be null or blank", argumentName);
        }

        public static String invalidCalification (){
            return "Invalid review qualification provided. Calification must be between 0 and 5";
        }
    }
}
