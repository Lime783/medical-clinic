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

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientDto add(CommandAddPatient command) {
        Patient patientToAdd = patientMapper.toEntity(command);
        if (patientRepository.findByEmail(command.email()).isPresent()) {
            throw new ClinicException(ClinicError.PATIENT_ALREADY_EXISTS, "This patient already exists");
        }
        return patientMapper.toDto(patientRepository.save(patientToAdd));
    }

    public void deleteByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public PatientDto findByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new ClinicException(ClinicError.PATIENT_NOT_FOUND,
                        "Patient not found"));
    }

    public List<PatientDto> findAll() {
        return patientMapper.toDto(patientRepository.findAll());
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
        return patientRepository.findByEmail(email).orElseThrow(() -> new ClinicException(
                ClinicError.PATIENT_NOT_FOUND, "Patient not found"));
    }
}
