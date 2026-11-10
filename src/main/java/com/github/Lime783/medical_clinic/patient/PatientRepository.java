package com.github.Lime783.medical_clinic.patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository {
    void add(Patient patient);

    void deleteByID(UUID id);

    Optional<Patient> findPatientByEmail(String email);

    Optional<Patient> findPatientByID(UUID id);

    Patient getPatientByID(UUID id);

    List<Patient> getAllPatients();
}
