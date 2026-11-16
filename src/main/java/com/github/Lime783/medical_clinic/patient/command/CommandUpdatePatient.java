package com.github.Lime783.medical_clinic.patient.command;

import java.time.LocalDate;

public record CommandUpdatePatient(String email, String password, String idCardNo, String firstName, String lastName,
                                   String phoneNumber, LocalDate birthday) {
}
