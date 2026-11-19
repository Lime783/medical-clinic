package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.patient.command.CommandAddPatient;
import com.github.Lime783.medical_clinic.patient.command.CommandChangePassword;
import com.github.Lime783.medical_clinic.patient.command.CommandUpdatePatient;
import com.github.Lime783.medical_clinic.patient.dto.PatientDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private PatientService patientService;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping(params = "email")
    public PatientDto findPatientByEmail(@RequestParam String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody CommandAddPatient command) {
        PatientDto saved = patientService.addPatientToDatabase(command);
        return ResponseEntity.created(URI.create("/patients/" + saved.id())).body(saved);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDto update(@PathVariable String email,
                             @Valid @RequestBody CommandUpdatePatient command) {
        return patientService.update(email, command);
    }

    @PatchMapping("/{email}/new-password")
    public PatientDto changePassword(@PathVariable String email,
                                     @Valid @RequestBody CommandChangePassword command) {
        return patientService.changePassword(email, command);
    }
}