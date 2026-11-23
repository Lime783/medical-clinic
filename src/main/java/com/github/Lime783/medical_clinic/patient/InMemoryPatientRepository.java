package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.exceptions.ClinicError;
import com.github.Lime783.medical_clinic.exceptions.ClinicException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryPatientRepository implements PatientRepository {
    private final List<Patient> patients;

    public InMemoryPatientRepository() {
        this.patients = new ArrayList<>();
    }

    @Override
    public Patient add(Patient patientToAdd) {
        patients.add(patientToAdd);
        return patientToAdd;
    }

    @Override
    public void deleteById(UUID id) {
        patients.removeIf(patient -> patient.getId().equals(id));
    }

    @Override
    public void deleteByEmail(String email) {
        patients.removeIf(patient -> patient.getEmail().equals(email));
    }

    @Override
    public Optional<Patient> findPatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Patient getPatientByEmail(String email) {
        return findPatientByEmail(email).orElseThrow(() -> new ClinicException(ClinicError.PATIENT_NOT_FOUND,
                "Patient not found"));
    }

    @Override
    public Optional<Patient> findPatientById(UUID id) {
        return patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst();
    }

    @Override
    public Patient getPatientById(UUID id) {
        return findPatientById(id).orElseThrow(() -> new ClinicException(ClinicError.PATIENT_NOT_FOUND,
                "Patient with an id: " + id + " not found"));
    }

    @Override
    public List<Patient> getAllPatients() {
        return List.copyOf(patients);
    }
}
