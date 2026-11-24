package com.github.Lime783.medical_clinic.exceptions;

import lombok.Getter;

@Getter
public class ClinicException extends RuntimeException {
    private final ClinicError error;

    public ClinicException(ClinicError error, String message) {
        super(message);
        this.error = error;
    }
}
