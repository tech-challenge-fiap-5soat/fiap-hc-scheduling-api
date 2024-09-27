package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerAdviceTest {

    @InjectMocks
    private ExceptionHandlerAdvice exceptionHandlerAdvice;

    @Test
    void handleMethodArgumentNotValidException_shouldReturnBadRequest_whenExceptionThrown() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(exception.getFieldErrors()).thenReturn(List.of(fieldError));
        when(exception.getMessage()).thenReturn("Validation failed");

        ResponseEntity<ProblemDetail> response = exceptionHandlerAdvice.handleMethodArgumentNotValidException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed", response.getBody().getDetail());
        assertEquals("defaultMessage", response.getBody().getProperties().get("field"));
    }

    @Test
    void handleConstraintViolationException_shouldReturnBadRequest_whenExceptionThrown() {
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        when(exception.getMessage()).thenReturn("Constraint violation");

        ResponseEntity<ProblemDetail> response = exceptionHandlerAdvice.handleConstraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Constraint violation", response.getBody().getDetail());
    }

    @Test
    void handleBusinessException_shouldReturnNotAcceptable_whenExceptionThrown() {
        BusinessException exception = mock(BusinessException.class);
        when(exception.getMessage()).thenReturn("Business error");

        ResponseEntity<ProblemDetail> response = exceptionHandlerAdvice.handleBusinessException(exception);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("Business error", response.getBody().getDetail());
    }

    @Test
    void handleSchedulingAppointmentLockedException_shouldReturnTooManyRequests_whenExceptionThrown() {
        SchedulingAppointmentLockedException exception = mock(SchedulingAppointmentLockedException.class);
        when(exception.getMessage()).thenReturn("Too many requests");

        ResponseEntity<ProblemDetail> response = exceptionHandlerAdvice.handleSchedulingAppointmentLockedException(exception);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
        assertEquals("Too many requests", response.getBody().getDetail());
    }

}