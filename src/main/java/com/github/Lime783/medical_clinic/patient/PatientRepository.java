package com.github.Lime783.medical_clinic.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    void deleteByEmail(String email);

    Optional<Patient> findByEmail(String email);
}
