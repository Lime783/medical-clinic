package com.github.Lime783.medical_clinic.patient;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping(params = "email")
    public ResponseEntity<Patient> findPatientByEmail(@RequestParam String email) {
        return patientService.findPatientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        Patient saved = patientService.addPatientToDatabase(patient);
        return ResponseEntity.created(URI.create("/patients/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Patient> update(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.update(email, patient)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{email}/new-password")
    public ResponseEntity<Patient> updatePatient(@PathVariable String email,
                                                 @RequestBody Map<String, String> body) {
        return patientService.changePassword(email, body.get("new-password"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
