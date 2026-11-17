package com.github.Lime783.medical_clinic.mapper;

import com.github.Lime783.medical_clinic.patient.Patient;
import com.github.Lime783.medical_clinic.patient.command.CommandAddPatient;
import com.github.Lime783.medical_clinic.patient.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PatientMapper {
    PatientDto toDto(Patient patient);

    Patient toEntity(CommandAddPatient command);
}
