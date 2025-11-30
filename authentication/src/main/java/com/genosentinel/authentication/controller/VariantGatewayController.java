package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.models.dto.variantDTO.VariantInDTO;
import com.genosentinel.authentication.service.VariantGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Variants", description = "Endpoints para la manipulación de variantes genéticas") // Swagger annotation
@RestController
@RequestMapping("/gateway")
public class VariantGatewayController {
    private final VariantGatewayService variantService;

    public VariantGatewayController(VariantGatewayService variantService){
        this.variantService = variantService;
    }

    @Operation(
            summary = "GetVariantList",
            description = "Retorna la lista completa de variantes"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de lista de variantes exitosa"),
    })
    @GetMapping("/obtener_variantes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getVariantList() {
        return variantService.getVariantList();
    }

    @Operation(
            summary = "GetVariantById",
            description = "Retorna la variante con ID correspondiente"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de variante exitosa"),
            @ApiResponse(responseCode = "404", description = "ID de la variante no encontrado")
    })
    @GetMapping("/obtener_variantes/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getVariantById(@PathVariable("id") Long id) {
        return variantService.getVariantById(id);
    }

    @Operation(
            summary = "CreateVariant",
            description = "Retorna el dto con los datos de la variante creada"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variante creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Petición de la variante mal creada")
    })
    @PostMapping("/crear_variante")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createVariant(@RequestBody VariantInDTO dto) {
        return variantService.createVariant(dto);
    }

    @Operation(
            summary = "UpdateVariant",
            description = "Retorna el dto con los datos de la variante actualizada"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variante actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Petición de variante mal actualizada")
    })
    @PutMapping("/actualizar_variante/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateVariant(@RequestBody VariantInDTO dto, @PathVariable
            ("id") Long id) {
        return variantService.updateVariant(dto, id);
    }

    @Operation(
            summary = "DeleteVariant",
            description = "Elimina variante por ID"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variante eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ID de la variante a eliminar")
    })
    @DeleteMapping("/eliminar_variante/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteVariant(@PathVariable("id") Long id) {
        return variantService.deleteVariant(id);
    }
}
