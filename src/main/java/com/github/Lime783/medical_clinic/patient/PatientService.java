package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.exceptions.DuplicateException;
import com.github.Lime783.medical_clinic.patient.command.CommandAddPatient;
import com.github.Lime783.medical_clinic.patient.command.CommandChangePassword;
import com.github.Lime783.medical_clinic.patient.command.CommandUpdatePatient;
import com.github.Lime783.medical_clinic.patient.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientDto addPatientToDatabase(CommandAddPatient command) {
        Patient patientToAdd = new Patient(UUID.randomUUID(), command.email(), command.password(), command.idCardNo(), command.firstName(), command.lastName(), command.phoneNumber(), command.birthday());
        if (patientRepository.findPatientByEmail(command.email()).isPresent()) {
            throw new DuplicateException("Patient with an email " + command.email() + "already exists: " + patientRepository.getPatientByEmail(command.email()).getId());
        }
        return toDto(patientRepository.add(patientToAdd));
    }

    public void deletePatientFromDatabase(PatientDto patient) {
        patientRepository.deleteById(patient.id());
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Optional<PatientDto> findPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email).map(this::toDto);
    }

    public PatientDto getPatientByEmail(String email) {
        return toDto(patientRepository.getPatientByEmail(email));
    }

    public Optional<PatientDto> findPatientById(UUID id) {
        return patientRepository.findPatientById(id).map(this::toDto);
    }

    public PatientDto getPatientById(UUID id) {
        return toDto(patientRepository.getPatientById(id));
    }

    public List<PatientDto> getAllPatients() {
        return patientRepository.getAllPatients().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<PatientDto> update(String email, CommandUpdatePatient command) {
        return patientRepository.findPatientByEmail(email).map(existing -> {
            existing.changeFirstName(command.firstName());
            existing.changeLastName(command.lastName());
            existing.changePassword(command.password());
            existing.changePhoneNumber(command.phoneNumber());
            existing.changeBirthday(command.birthday());
            return toDto(existing);
        });
    }

    public Optional<PatientDto> changePassword(String email, CommandChangePassword command) {
        return patientRepository.findPatientByEmail(email).map(existing -> {
            existing.changePassword(command.newPassword());
            return toDto(existing);
        });
    }

    private PatientDto toDto(Patient patient) {
        return new PatientDto(patient.getId(), patient.getEmail(), patient.getFirstName(), patient.getLastName(), patient.getPhoneNumber(), patient.getBirthday());
    }
}
