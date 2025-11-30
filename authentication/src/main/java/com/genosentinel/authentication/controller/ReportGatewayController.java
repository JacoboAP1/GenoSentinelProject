package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.models.dto.reportDTO.ReportInDTO;
import com.genosentinel.authentication.service.ReportGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reports", description = "Endpoints para la manipulación de reportes")
@RestController
@RequestMapping("/gateway")
public class ReportGatewayController {
    private final ReportGatewayService reportService;

    public ReportGatewayController(ReportGatewayService reportService){
        this.reportService = reportService;
    }

    @Operation(
            summary = "GetReportsList",
            description = "Retorna la lista completa de reportes"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de lista de reportes exitosa"),
    }) //Swagger annotation
    @GetMapping("/obtener_reportes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getReportList() {
        return reportService.getReportList();
    }

    @Operation(
            summary = "GetReportById",
            description = "Retorna el reporte con ID correspondiente"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de reporte exitosa"),
            @ApiResponse(responseCode = "404", description = "ID del reporte no encontrado")
    })
    @GetMapping("/obtener_reportes/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getReportById(@PathVariable("id") Long id) {
        return reportService.getReportById(id);
    }

    @Operation(
            summary = "CreateReport",
            description = "Retorna el dto con los datos del reporte creado"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Petición del reporte mal creada")
    })
    @PostMapping("/crear_reporte")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createReport(@RequestBody ReportInDTO dto) {
        return reportService.createReport(dto);
    }

    @Operation(
            summary = "UpdateReport",
            description = "Retorna el dto con los datos del reporte actualizado"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Petición de reporte mal actualizada")
    })
    @PutMapping("/actualizar_reporte/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateReport(@RequestBody ReportInDTO dto, @PathVariable
            ("id") Long id) {
        return reportService.updateReport(dto, id);
    }

    @Operation(
            summary = "DeleteReport",
            description = "Elimina reporte por ID"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ID del reporte a eliminar")
    })
    @DeleteMapping("/eliminar_reporte/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteReport(@PathVariable("id") Long id) {
        return reportService.deleteReport(id);
    }
}
