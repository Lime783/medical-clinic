package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.exceptions.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public void addPatientToDatabase(Patient patient) {
        if (patientRepository.findPatientByEmail(patient.getEmail()).isPresent()) {
            throw new DuplicateException("Patient with an email " + patient.getEmail() + "already exists: " + patient.getId());
        }
        patientRepository.add(patient);
    }

    public void deletePatientFromDatabase(Patient patient) {
        patientRepository.deleteByID(patient.getId());
    }

    public void findPatientByEmail(String email) {
        patientRepository.findPatientByEmail(email);
    }

    public void findPatientByID(UUID id) {
        patientRepository.findPatientByID(id);
    }

    public Patient getPatientById(UUID id) {
        return patientRepository.getPatientByID(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }
}
