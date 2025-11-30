package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.models.dto.tumortypeDTO.TumorTypeInDTO;
import com.genosentinel.authentication.service.TumorTypeGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar endpoints Gateway de Tipos de Tumor
 * No hace lógica de negocio, solo:
 * - Recibir la petición
 * - Tomar el body o parámetros
 * - Serializar el DTO
 * - Enviar la petición al microservicio de NestJS
 * - Devolver la respuesta
 */
@Tag(name = "Tumor Types", description = "Endpoints para la gestión del catálogo de tumores oncológicos")
@RestController
@RequestMapping("/gateway")
public class TumorTypeGatewayController {
    private final TumorTypeGatewayService tumorTypeService;

    public TumorTypeGatewayController(TumorTypeGatewayService tumorTypeService) {
        this.tumorTypeService = tumorTypeService;
    }

    @Operation(
            summary = "Obtener todos los tipos de tumor",
            description = "Retorna el catálogo completo de tipos de tumor registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catálogo obtenido exitosamente")
    })
    @GetMapping("/obtener_tipos_tumor")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getTumorTypeList() {
        return tumorTypeService.getTumorTypeList();
    }

    @Operation(
            summary = "Obtener tipo de tumor por ID",
            description = "Retorna la información de un tipo de tumor específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de tumor encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado")
    })
    @GetMapping("/obtener_tipos_tumor/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getTumorTypeById(@PathVariable("id") Long id) {
        return tumorTypeService.getTumorTypeById(id);
    }

    @Operation(
            summary = "Buscar tipos de tumor por sistema",
            description = "Busca tipos de tumor que afecten un sistema específico del cuerpo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda completada exitosamente")
    })
    @GetMapping("/buscar_tipos_tumor")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> searchTumorTypesBySystem(@RequestParam("system") String system) {
        return tumorTypeService.searchTumorTypesBySystem(system);
    }

    @Operation(
            summary = "Crear nuevo tipo de tumor",
            description = "Registra un nuevo tipo de tumor en el catálogo oncológico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de tumor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "409", description = "Ya existe un tipo de tumor con ese nombre")
    })
    @PostMapping("/crear_tipo_tumor")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createTumorType(@RequestBody TumorTypeInDTO dto) {
        return tumorTypeService.createTumorType(dto);
    }

    @Operation(
            summary = "Actualizar tipo de tumor",
            description = "Actualiza la información de un tipo de tumor existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de tumor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un tipo de tumor con ese nombre")
    })
    @PutMapping("/actualizar_tipo_tumor/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateTumorType(@RequestBody TumorTypeInDTO dto, @PathVariable("id") Long id) {
        return tumorTypeService.updateTumorType(dto, id);
    }

    @Operation(
            summary = "Eliminar tipo de tumor",
            description = "Elimina permanentemente un tipo de tumor del catálogo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de tumor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado")
    })
    @DeleteMapping("/eliminar_tipo_tumor/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteTumorType(@PathVariable("id") Long id) {
        return tumorTypeService.deleteTumorType(id);
    }
}