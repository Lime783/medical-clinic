package com.github.Lime783.medical_clinic.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // piętro 1: każdy błąd domenowy; status z kodu, a kod jako dodatkowe pole body
    @ExceptionHandler(ClinicException.class)
    public ProblemDetail handleBookstore(ClinicException exception) {
        log.warn("blad domenowy {}: {}", exception.getError(), exception.getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(exception.getError().getStatus(),
                exception.getMessage());
        problem.setProperty("code", exception.getError().name());
        return problem;
    }

    // piętro 2: walidacja z lekcji 9; Spring zbudował już ProblemDetail, dokładamy listę pól.
    // Do logu idą pola razem z odrzuconymi wartościami: w księgarni to ISBN, tytuł i cena,
    // żadne dane osobowe, a z wartością łatwiej odtworzyć problem
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        List<FieldError> fieldErrors = exception.getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField)
                        .thenComparing(FieldError::getDefaultMessage))
                .toList();
        // W poleceniu "Dane z odrzuconych requestów, w tym hasło i adres e-mail pacjenta, nie trafiają do logu aplikacji" więc wywaliłem je
        log.warn("blad klienta {}: {}", status.value(), exception.getClass().getSimpleName());
        ProblemDetail problem = exception.getBody();
        problem.setProperty("errors", fieldErrors.stream()
                .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
                .toList());
        // super, a nie nasza wersja z piętra 3: wiersz walidacji jest już w logu
        return super.handleExceptionInternal(exception, problem, headers, status, request);
    }

    // piętro 3: przez tę metodę przechodzi każdy wyjątek Spring MVC; do logu sam typ wyjątku,
    // bo komunikat bywa wielowierszowym opisem (np. cały błąd parsowania JSON-a)
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
                                                             HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn("blad klienta {}: {}", statusCode.value(), exception.getClass().getSimpleName());
        return super.handleExceptionInternal(exception, body, headers, statusCode, request);
    }

    // piętro 4: siatka; wszystko, czego nie przewidzieliśmy, to nasz błąd. Klient dostaje sam
    // komunikat ogólny, szczegóły z getMessage() i stack trace idą tylko do logu
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknown(Exception exception) {
        log.error("nieznany blad", exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }
}

