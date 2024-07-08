package com.example.patika.advice;


import com.example.patika.exception.PaymentFailedException;
import com.example.patika.exception.PurchaseNotFoundException;
import com.example.patika.utils.results.ErrorDataResult;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleFeignException(FeignException feignException) {
        log.error("Feign client error! || " + feignException.getMessage());
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>("Feign client error!");
        return errorDataResult;
    }

    @ExceptionHandler(PaymentFailedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handlePaymentFailedException(PaymentFailedException paymentFailedException) {
        log.error("Payment error! || " + paymentFailedException.getMessage());
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>("Payment error!");
        return errorDataResult;
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handlePurchaseNotFoundException(PurchaseNotFoundException purchaseNotFoundException) {
        log.error("Purchase not found! || " + purchaseNotFoundException.getMessage());
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>("Purchase not found!");
        return errorDataResult;
    }


}
