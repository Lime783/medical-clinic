package com.github.Lime783.medical_clinic.patient.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommandChangePassword(
        @JsonProperty("new-password") String newPassword) {
}
