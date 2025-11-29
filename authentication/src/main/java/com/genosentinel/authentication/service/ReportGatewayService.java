package com.genosentinel.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genosentinel.authentication.models.dto.reportDTO.ReportInDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportGatewayService {
    private ObjectMapper objectMapper; // Para pasar el InDTO a Json
    // Hacia el otro microservicio
    private final RestTemplate restTemplate; // Transportador de respuestas HTTP
    // Y bodies

    public ReportGatewayService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getReportList() {
        String djangoUrl = "http://localhost:8000/genomic/reports/";

        ResponseEntity<String> response = restTemplate.getForEntity(djangoUrl, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public ResponseEntity<String> getReportById(Long id) {
        String djangoUrl = "http://localhost:8000/genomic/reports/" + id;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(djangoUrl, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de django con HttpClient
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> createReport(ReportInDTO dto) {
        String json;
        String djangoUrl = "http://localhost:8000/genomic/reports/";

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

    public ResponseEntity<String> updateReport(ReportInDTO dto, Long id) {
        String json;
        String djangoUrl = "http://localhost:8000/genomic/reports/" + id + "/";

        try {
            json = objectMapper.writeValueAsString(dto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(json, headers);

            // Hacer PUT con exchange
            ResponseEntity<String> response = restTemplate.exchange(djangoUrl, HttpMethod.PUT,
                    request,
                    String.class
            );

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON: " + e.getMessage());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> deleteReport(Long id) {
        String djangoUrl = "http://localhost:8000/genomic/reports/" + id + "/";

        try {
            // Hacemos DELETE usando exchange porque delete() no devuelve respuesta
            ResponseEntity<String> response = restTemplate.exchange(
                    djangoUrl,
                    HttpMethod.DELETE,
                    null,
                    String.class
            );

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Si Django devuelve 404, 400, 500, etc, capturamos el JSON
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}
