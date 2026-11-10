package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.exceptions.DuplicateException;
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
    public void add(Patient patientToAdd) {
        if (patients.contains(patientToAdd)) {
            throw new DuplicateException("Patient " + patientToAdd.getFirstName() + " " + patientToAdd.getLastName() + " already exists: " + patientToAdd.getId());
        }
        patients.add(patientToAdd);
    }

    @Override
    public void deleteByID(UUID id) {
        if (findPatientByID(id).isEmpty()) {
            throw new IllegalArgumentException("Patient with ID " + id + " does not exist");
        }
        patients.removeIf(patient -> patient.getId().equals(id));
    }

    @Override
    public Optional<Patient> findPatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<Patient> findPatientByID(UUID id) {
        return patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst();
    }

    @Override
    public Patient getPatientByID(UUID id) {
        return findPatientByID(id).orElseThrow();
    }

    @Override
    public List<Patient> getAllPatients() {
        return List.copyOf(patients);
    }
}
