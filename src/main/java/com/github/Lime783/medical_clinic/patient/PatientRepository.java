package com.github.Lime783.medical_clinic.patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository {
    Patient add(Patient patient);

    void deleteById(UUID id);

    void deleteByEmail(String email);

    Optional<Patient> findPatientByEmail(String email);

    Patient getPatientByEmail(String email);

    Optional<Patient> findPatientById(UUID id);

    Patient getPatientById(UUID id);

    List<Patient> getAllPatients();
}
