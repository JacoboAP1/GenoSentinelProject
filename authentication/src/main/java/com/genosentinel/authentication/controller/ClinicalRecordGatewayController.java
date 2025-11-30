package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.models.dto.clinicalrecordDTO.ClinicalRecordInDTO;
import com.genosentinel.authentication.service.ClinicalRecordGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar endpoints Gateway de Historias Clínicas
 * No hace lógica de negocio, solo:
 * - Recibir la petición
 * - Tomar el body o parámetros
 * - Serializar el DTO
 * - Enviar la petición al microservicio de NestJS
 * - Devolver la respuesta
 */
@Tag(name = "Clinical Records", description = "Endpoints para la gestión de historias clínicas y diagnósticos oncológicos")
@RestController
@RequestMapping("/gateway")
public class ClinicalRecordGatewayController {
    private final ClinicalRecordGatewayService clinicalRecordService;

    public ClinicalRecordGatewayController(ClinicalRecordGatewayService clinicalRecordService) {
        this.clinicalRecordService = clinicalRecordService;
    }

    @Operation(
            summary = "Obtener todas las historias clínicas",
            description = "Retorna la lista completa de historias clínicas registradas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de historias clínicas obtenida exitosamente")
    })
    @GetMapping("/obtener_historias_clinicas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getClinicalRecordList() {
        return clinicalRecordService.getClinicalRecordList();
    }

    @Operation(
            summary = "Obtener historia clínica por ID",
            description = "Retorna la información de una historia clínica específica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historia clínica encontrada"),
            @ApiResponse(responseCode = "404", description = "Historia clínica no encontrada")
    })
    @GetMapping("/obtener_historias_clinicas/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getClinicalRecordById(@PathVariable("id") String id) {
        return clinicalRecordService.getClinicalRecordById(id);
    }

    @Operation(
            summary = "Obtener historias clínicas por paciente",
            description = "Retorna todas las historias clínicas de un paciente específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historias clínicas obtenidas exitosamente")
    })
    @GetMapping("/obtener_historias_por_paciente/{patientId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getClinicalRecordsByPatient(@PathVariable("patientId") String patientId) {
        return clinicalRecordService.getClinicalRecordsByPatient(patientId);
    }

    @Operation(
            summary = "Obtener historias clínicas por tipo de tumor",
            description = "Retorna todas las historias clínicas asociadas a un tipo de tumor específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historias clínicas obtenidas exitosamente")
    })
    @GetMapping("/obtener_historias_por_tumor/{tumorTypeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getClinicalRecordsByTumorType(@PathVariable("tumorTypeId") Long tumorTypeId) {
        return clinicalRecordService.getClinicalRecordsByTumorType(tumorTypeId);
    }

    @Operation(
            summary = "Crear nueva historia clínica",
            description = "Registra un nuevo diagnóstico oncológico con su tratamiento asociado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Historia clínica creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "404", description = "Paciente o tipo de tumor no encontrado")
    })
    @PostMapping("/crear_historia_clinica")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createClinicalRecord(@RequestBody ClinicalRecordInDTO dto) {
        return clinicalRecordService.createClinicalRecord(dto);
    }

    @Operation(
            summary = "Actualizar historia clínica",
            description = "Actualiza la información de una historia clínica existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historia clínica actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Historia clínica, paciente o tipo de tumor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/actualizar_historia_clinica/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateClinicalRecord(@RequestBody ClinicalRecordInDTO dto, @PathVariable("id") String id) {
        return clinicalRecordService.updateClinicalRecord(dto, id);
    }

    @Operation(
            summary = "Eliminar historia clínica",
            description = "Elimina permanentemente una historia clínica del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Historia clínica eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Historia clínica no encontrada")
    })
    @DeleteMapping("/eliminar_historia_clinica/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteClinicalRecord(@PathVariable("id") String id) {
        return clinicalRecordService.deleteClinicalRecord(id);
    }
}