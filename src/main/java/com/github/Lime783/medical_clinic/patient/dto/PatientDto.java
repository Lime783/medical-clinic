package com.github.Lime783.medical_clinic.patient.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PatientDto(UUID id, String email, String firstName, String lastName, String phoneNumber,
                         LocalDate birthday) {
}
