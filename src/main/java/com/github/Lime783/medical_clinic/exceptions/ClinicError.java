package com.github.Lime783.medical_clinic.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ClinicError {
    PATIENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    PATIENT_ALREADY_EXISTS(HttpStatus.CONFLICT),

    INVALID_EMAIL(HttpStatus.UNPROCESSABLE_CONTENT),
    INVALID_PASSWORD(HttpStatus.UNPROCESSABLE_CONTENT),
    INVALID_ID_CARD_NO(HttpStatus.UNPROCESSABLE_CONTENT),
    INVALID_FIRST_NAME(HttpStatus.UNPROCESSABLE_CONTENT),
    INVALID_LAST_NAME(HttpStatus.UNPROCESSABLE_CONTENT),
    INVALID_PHONE_NUMBER(HttpStatus.UNPROCESSABLE_CONTENT),
    INVALID_BIRTHDAY(HttpStatus.UNPROCESSABLE_CONTENT);

    private final HttpStatus status;
}
