package com.github.Lime783.medical_clinic.patient;

import com.github.Lime783.medical_clinic.patient.command.CommandAddPatient;
import com.github.Lime783.medical_clinic.patient.command.CommandChangePassword;
import com.github.Lime783.medical_clinic.patient.command.CommandUpdatePatient;
import com.github.Lime783.medical_clinic.patient.dto.PatientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Patients", description = "Patients catalog: browse, add, change and remove patients")
@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private PatientService patientService;

    @Operation(summary = "List all patients")
    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.findAll();
    }

    @Operation(summary = "Get patient by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "No patient with this email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(params = "email")
    public PatientDto findByEmail(@RequestParam String email) {
        return patientService.findByEmail(email);
    }

    @Operation(summary = "Add a patient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient added"),
            @ApiResponse(responseCode = "400", description = "Invalid fields or body is not JSON",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "A patient with this email already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<PatientDto> savePatient(@Valid @RequestBody CommandAddPatient command) {
        PatientDto saved = patientService.add(command);
        return ResponseEntity.created(URI.create("/patients/" + saved.id())).body(saved);
    }

    @Operation(summary = "Remove a patient by email")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Patient removed or never existed"),
            @ApiResponse(responseCode = "400", description = "Not an email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable @Email String email) {
        patientService.deleteByEmail(email);
    }

    @Operation(summary = "Change patient info without changing id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient info changed"),
            @ApiResponse(responseCode = "400", description = "Invalid fields or body is not JSON",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No patient with this email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email,
                             @Valid @RequestBody CommandUpdatePatient command) {
        return patientService.update(email, command);
    }

    @Operation(summary = "Change patient's password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed"),
            @ApiResponse(responseCode = "400", description = "Invalid fields or body is not JSON",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No patient with this email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            })
    @PatchMapping("/{email}/new-password")
    public PatientDto changePassword(@PathVariable String email,
                                     @Valid @RequestBody CommandChangePassword command) {
        return patientService.changePassword(email, command);
    }
}