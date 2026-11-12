package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.exceptions.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient addPatientToDatabase(Patient patient) {
        if (patientRepository.findPatientByEmail(patient.getEmail()).isPresent()) {
            throw new DuplicateException("Patient with an email " + patient.getEmail() + "already exists: " + patient.getId());
        }
        return patientRepository.add(patient);
    }

    public void deletePatientFromDatabase(Patient patient) {
        patientRepository.deleteById(patient.getId());
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Optional<Patient> findPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public Optional<Patient> findPatientById(UUID id) {
        return patientRepository.findPatientById(id);
    }

    public Patient getPatientById(UUID id) {
        return patientRepository.getPatientById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    public Optional<Patient> update(String email, Patient changes) {
        return patientRepository.findPatientByEmail(email).map(existing -> {
            existing.changeFirstName(changes.getFirstName());
            existing.changeLastName(changes.getLastName());
            existing.changePassword(changes.getPassword());
            existing.changePhoneNumber(changes.getPhoneNumber());
            existing.changeBirthday(changes.getBirthday());
            return existing;
        });
    }

    public Optional<Patient> changePassword(String email, String newPassword){
        return patientRepository.findPatientByEmail(email).map(existing -> {
            existing.changePassword(newPassword);
            return existing;
        });
    }
}
