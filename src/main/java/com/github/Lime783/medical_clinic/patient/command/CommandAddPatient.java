package com.github.Lime783.medical_clinic.patient.command;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CommandAddPatient(
        @NotBlank(message = "email cannot be blank or null")
        @Email(message = "email must resemble an actual email")
        String email,

        @NotBlank(message = "Password cannot be blank or null")
        @Size(min = 6, message = "Password must contain at least 6 characters")
        String password,

        @NotBlank(message = "ID card number cannot be blank or null")
        @Size(min = 9, max = 9, message = "ID card number must contain 9 digits")
        String idCardNo,

        @NotBlank(message = "First name cannot be blank or null")
        String firstName,

        @NotBlank(message = "Last name cannot be blank or null")
        String lastName,

        @NotBlank(message = "Phone number cannot be blank or null")
        @Size(min = 6, message = "Phone number must contain at least 6 digits")
        String phoneNumber,

        @NotNull
        @PastOrPresent(message = "Birthday cannot be in the future")
        LocalDate birthday) {
}