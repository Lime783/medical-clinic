package com.github.Lime783.medical_clinic.patient.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommandChangePassword(
        @JsonProperty("new-password")
        @NotBlank(message = "Password cannot be blank or null")
        @Size(min = 3, message = "Password must contain at least 3 characters")

        String newPassword) {
}
