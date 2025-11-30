package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.models.dto.geneDTO.GeneInDTO;
import com.genosentinel.authentication.service.GeneGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar endpoints Gateway
 * No hace lógica de negocio, solo:
 * Recibir la petición
 * Tomar el body o parámetros
 * Serializar el DTO
 * Enviar la petición al microservicio de Django
 * Devolver la respuesta
 */
@Tag(name = "Gene", description = "Endpoints para la manipulación de genes") // Swagger annotation
@RestController
@RequestMapping("/gateway")
public class GeneGatewayController {
    private final GeneGatewayService geneService;

    public GeneGatewayController(GeneGatewayService geneService){
        this.geneService = geneService;
    }

    @Operation(
            summary = "GetGeneList",
            description = "Retorna la lista completa de genes"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de lista de genes exitosa"),
    }) //Swagger annotation
    @GetMapping("/obtener_genes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getGeneList() {
        return geneService.getGeneList();
    }

    @Operation(
            summary = "GetGeneById",
            description = "Retorna el gen con ID correspondiente"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de gen exitosa"),
            @ApiResponse(responseCode = "404", description = "ID del gen no encontrado")
    }) //Swagger annotation
    @GetMapping("/obtener_genes/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getGeneById(@PathVariable("id") Long id) {
        return geneService.getGeneById(id);
    }

    @Operation(
            summary = "CreateGene",
            description = "Retorna el dto con los datos del gen creado"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gen creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Petición de gene mal creada")
    }) //Swagger annotation
    @PostMapping("/crear_gen")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createGene(@RequestBody GeneInDTO dto) {
        return geneService.createGene(dto);
    }

    @Operation(
            summary = "UpdateGene",
            description = "Retorna el dto con los datos del gen actualizado"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gen actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Petición de gene mal actualizada")
    }) //Swagger annotation
    @PutMapping("/actualizar_gen/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateGene(@RequestBody GeneInDTO dto, @PathVariable
                                                                        ("id") Long id) {
        return geneService.updateGene(dto, id);
    }

    @Operation(
            summary = "DeleteGene",
            description = "Elimina gen por ID"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gen eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ID del gen a eliminar")
    }) //Swagger annotation
    @DeleteMapping("/eliminar_gen/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteGene(@PathVariable("id") Long id) {
        return geneService.deleteGene(id);
    }
}