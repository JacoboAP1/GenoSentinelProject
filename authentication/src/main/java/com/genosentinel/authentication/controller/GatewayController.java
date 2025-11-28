package com.genosentinel.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genosentinel.authentication.models.dto.geneDTO.GeneInDTO;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Controlador para manejar endpoints Gateway
 * No hace lógica de negocio, solo:
 * Recibir la petición
 * Tomar el body o parámetros
 * Serializar el DTO
 * Enviar la petición al microservicio de Django
 * Devolver la respuesta
 */
@RestController
@RequestMapping("/gateway")
public class GatewayController {
    private ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public GatewayController(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/obtener_genes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getGeneList() {
        String djangoUrl = "http://localhost:8000/genomic/gene/";

        ResponseEntity<String> response = restTemplate.getForEntity(djangoUrl, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/obtener_genes/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getGeneById(@PathVariable("id") Long id) {
        String djangoUrl = "http://localhost:8000/genomic/gene/" + id;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(djangoUrl, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de django con HttpClient
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @PostMapping("/crear_gen")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createGene(@RequestBody GeneInDTO dto) {
        String json;
        String djangoUrl = "http://localhost:8000/genomic/gene/";

        try {
            // serializando json para que Django capte los campos del InDTO
            json = objectMapper.writeValueAsString(dto);

            // Se crean los headers para indicarle a Django que el cuerpo
            // del request está en formato JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Se empaqueta el JSON junto con los headers antes de enviarlo a Django
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(djangoUrl, request, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON: " + e.getMessage());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de django
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}