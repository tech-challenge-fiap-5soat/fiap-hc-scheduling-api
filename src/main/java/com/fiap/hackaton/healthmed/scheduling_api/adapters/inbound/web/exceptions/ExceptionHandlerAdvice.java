package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        var httpStatus = BAD_REQUEST;
        ProblemDetail problemDetail = exceptionDataToProblemDetail(httpStatus, exception.getMessage());

        for (FieldError fieldError : exception.getFieldErrors()) {
            problemDetail.setProperty(fieldError.getField(), requireNonNull(fieldError.getDefaultMessage()));
        }

        return status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException exception) {
        var httpStatus = BAD_REQUEST;
        ProblemDetail problemDetail = exceptionDataToProblemDetail(httpStatus, exception.getMessage());

        return status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(BusinessException exception) {
        var httpStatus = NOT_ACCEPTABLE;
        ProblemDetail problemDetail = exceptionDataToProblemDetail(httpStatus, exception.getMessage());

        return status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(SchedulingAppointmentLockedException.class)
    public ResponseEntity<ProblemDetail> handleSchedulingAppointmentLockedException(SchedulingAppointmentLockedException exception) {
        var httpStatus = TOO_MANY_REQUESTS;
        ProblemDetail problemDetail = exceptionDataToProblemDetail(httpStatus, exception.getMessage());

        return status(httpStatus).body(problemDetail);
    }



    private ProblemDetail exceptionDataToProblemDetail(HttpStatus status, String detail) {
        ProblemDetail problemDetail = forStatusAndDetail(status, detail);
        problemDetail.setProperty("timeStamp", now());

        return problemDetail;
    }

}