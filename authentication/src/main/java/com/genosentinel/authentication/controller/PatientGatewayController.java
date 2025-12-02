package com.genosentinel.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.genosentinel.authentication.service.PatientGatewayService;
import com.genosentinel.authentication.models.dto.patientDTO.PatientInDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Patient", description = "Endpoints para la manipulación de pacientes")
@RestController
@RequestMapping("/gateway")
public class PatientGatewayController {
    private final PatientGatewayService patientService;

    public PatientGatewayController(PatientGatewayService patientService){
	this.patientService = patientService;
    }

    @Operation(
	    summary = "GetPatientList",
	    description = "Retorna la lista completa de pacientes"
    )
    @ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Devolución de lista de pacientes exitosa"),
    })
    @GetMapping("/obtener_pacientes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getPatientList() {
    return patientService.getPatientList();
    }

    @Operation(
	    summary = "GetPatientById",
	    description = "Retorna el paciente con ID correspondiente"
    )
    @ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Devolución de paciente exitosa"),
	    @ApiResponse(responseCode = "404", description = "ID del paciente no encontrado")
    })
    @GetMapping("/obtener_pacientes/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getPatientById(@PathVariable("id") Long id) {
        return patientService.getPatientById(id);
        }

        @Operation(
            summary = "CreatePatient",
            description = "Retorna el dto con los datos del paciente creado"
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Petición de paciente mal creada")
        })
        @PostMapping("/crear_paciente")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Object> createPatient(@RequestBody PatientInDTO dto) {
            return patientService.createPatient(dto);
        }

        @Operation(
            summary = "UpdatePatient",
            description = "Retorna el dto con los datos del paciente actualizado"
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Petición de paciente mal actualizada")
        })
        @PutMapping("/actualizar_paciente/{id}")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Object> updatePatient(@RequestBody PatientInDTO dto, @PathVariable("id") Long id) {
        return patientService.updatePatient(dto, id);
        }

        @Operation(
            summary = "DeletePatient",
            description = "Elimina paciente por ID"
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ID del paciente a eliminar")
        })
        @DeleteMapping("/eliminar_paciente/{id}")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Object> deletePatient(@PathVariable("id") Long id) {
        return patientService.deletePatient(id);
        }

        @Operation(
            summary = "SearchPatientsByName",
            description = "Busca pacientes cuyo nombre o apellido contenga el texto especificado"
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda de pacientes exitosa"),
        })
        @GetMapping("/obtener_pacientes/search")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Object> searchPatientsByName(@RequestParam("name") String name) {
        return patientService.searchPatientsByName(name);
        }

        @Operation(
            summary = "GetPatientsByStatus",
            description = "Retorna pacientes filtrados por su estado"
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pacientes por estado obtenida"),
        })
        @GetMapping("/obtener_pacientes/status/{status}")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Object> getPatientsByStatus(@PathVariable("status") String status) {
        return patientService.findPatientsByStatus(status);
        }

        @Operation(
            summary = "DeactivatePatient",
            description = "Cambia el estado del paciente a Inactivo"
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente desactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
        })
        @PatchMapping("/desactivar_paciente/{id}")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Object> deactivatePatient(@PathVariable("id") Long id) {
        return patientService.deactivatePatient(id);
        }
}
