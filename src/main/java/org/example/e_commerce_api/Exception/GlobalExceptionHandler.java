package org.example.e_commerce_api.Exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(statusCode).headers(headers).body(
                BaseBodyResponse.bodyFailed(statusCode, ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ){
        log.error(exception.getMessage(), exception);
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation Error");

        return ResponseEntity.status(statusCode).headers(headers)
                .body(BaseBodyResponse.bodyFailed(HttpStatus.BAD_REQUEST, message));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleConstraintViolation(
            ConstraintViolationException exception
    ){
        log.error(exception.getMessage(), exception);
        String message =
                exception.getConstraintViolations().stream().findFirst()
                        .map(ConstraintViolation::getMessage).orElse("Validation error");
        return BaseBodyResponse.failed(HttpStatus.BAD_REQUEST, message);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleBadRequestException
            (
                    BadRequestException exception
            ){
        log.error(exception.getMessage(), exception);
        return BaseBodyResponse.failed(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleIllegalArgumentException
            (
                    IllegalArgumentException exception
            ){
        log.error(exception.getMessage(), exception);
        return BaseBodyResponse.failed(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleResourceNotFoundException
            (
                    ResourceNotFoundException exception
            ){
        log.error(exception.getMessage(), exception);
        return BaseBodyResponse.failed(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleResourceNotFoundException
            (
                    TransactionSystemException exception
            ){
        log.error(exception.getMessage(), exception);
        return BaseBodyResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleResourceNotFoundException
            (
                    IllegalStateException exception
            ){
        log.error(exception.getMessage(), exception);
        return BaseBodyResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleResourceNotFoundException
            (
                    EntityNotFoundException exception,
                    HttpServletRequest request
            ){
        log.error(exception.getMessage(), exception);
        return BaseBodyResponse.failed(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    // Database
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);

        String message = "Database constraint violation";
        String causeMsg = ex.getMostSpecificCause().getMessage().toLowerCase();

        if (causeMsg.contains("duplicate key") || causeMsg.contains("unique constraint")) {
            message = "Resource already exists";
        } else if (causeMsg.contains("foreign key")) {
            message = "Referenced resource does not exist";
        } else if (causeMsg.contains("null value in column")) {
            message = "Required field is missing";
        } else if (causeMsg.contains("check constraint")) {
            message = "Field value is out of allowed range";
        } else if (causeMsg.contains("value too long")) {
            message = "Field value exceeds maximum length";
        }

        return BaseBodyResponse.failed(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleOptimisticLock(
            OptimisticLockingFailureException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return BaseBodyResponse.failed(
                HttpStatus.CONFLICT, "Resource was modified by another request, please retry");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleProductNotFound(
            ProductNotFoundException ex
    ) {

        return BaseBodyResponse.failed(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleCartNotFound(
            CartNotFoundException ex
    ) {

        return BaseBodyResponse.failed(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleCartItemNotFound(
            CartItemNotFoundException ex
    ) {

        return BaseBodyResponse.failed(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleUserNotFound(
            UserNotFoundException ex
    ) {

        return BaseBodyResponse.failed(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleInsufficientStock(
            InsufficientStockException ex
    ) {

        return BaseBodyResponse.failed(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(UnauthorizedCartAccessException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleUnauthorizedAccess(
            UnauthorizedCartAccessException ex
    ) {

        return BaseBodyResponse.failed(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleGeneralException(
            Exception ex
    ) {

        log.error("Unexpected error", ex);

        return BaseBodyResponse.failed(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleDataAccess(
            DataAccessException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return BaseBodyResponse.failed(
                HttpStatus.SERVICE_UNAVAILABLE, "Database is temporarily unavailable");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleOrderNotFound(
            OrderNotFoundException ex
    ) {
        return BaseBodyResponse.failed(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }
    @ExceptionHandler(EmptyOrderException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleEmptyOrder(
            EmptyOrderException ex
    ) {
        return BaseBodyResponse.failed(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }
    @ExceptionHandler(InvalidOrderStatusTransitionException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleOrderStatusTransition(
            InvalidOrderStatusTransitionException ex
    ) {
        return BaseBodyResponse.failed(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }
    @ExceptionHandler(UnauthorizedOrderAccessException.class)
    public ResponseEntity<BaseBodyResponse<Void>> handleUnauthorizedOrder(
            UnauthorizedOrderAccessException ex
    ) {
        return BaseBodyResponse.failed(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );
    }
}
