package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.exceptions.ClinicError;
import com.github.Lime783.medical_clinic.exceptions.ClinicException;
import com.github.Lime783.medical_clinic.mapper.PatientMapper;
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
    private final PatientMapper patientMapper;

    public PatientDto addPatientToDatabase(CommandAddPatient command) {
        Patient patientToAdd = patientMapper.toEntity(command);
        if (patientRepository.findPatientByEmail(command.email()).isPresent()) {
            throw new ClinicException(ClinicError.PATIENT_ALREADY_EXISTS, "This patient already exists");
        }
        return patientMapper.toDto(patientRepository.add(patientToAdd));
    }

    public void deletePatientFromDatabase(PatientDto patient) {
        patientRepository.deleteById(patient.id());
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Optional<PatientDto> findPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email).map(patientMapper::toDto);
    }

    public PatientDto getPatientByEmail(String email) {
        return patientMapper.toDto(patientRepository.getPatientByEmail(email));
    }

    public Optional<PatientDto> findPatientById(UUID id) {
        return patientRepository.findPatientById(id).map(patientMapper::toDto);
    }

    public PatientDto getPatientById(UUID id) {
        return patientMapper.toDto(patientRepository.getPatientById(id));
    }

    public List<PatientDto> getAllPatients() {
        return patientRepository.getAllPatients().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientDto update(String email, CommandUpdatePatient command) {
        Patient existing = getPatientModel(email);
        existing.changeFirstName(command.firstName());
        existing.changeLastName(command.lastName());
        existing.changePassword(command.password());
        existing.changePhoneNumber(command.phoneNumber());
        existing.changeBirthday(command.birthday());
        return patientMapper.toDto(existing);
    }

    public PatientDto changePassword(String email, CommandChangePassword command) {
        Patient existing = getPatientModel(email);
        existing.changePassword(command.newPassword());
        return patientMapper.toDto(existing);
    }

    private Patient getPatientModel(String email) {
        return patientRepository.getPatientByEmail(email);
    }
}
