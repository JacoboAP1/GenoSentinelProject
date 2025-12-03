package com.genosentinel.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genosentinel.authentication.models.dto.tumortypeDTO.TumorTypeInDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
public class TumorTypeGatewayService {
    private ObjectMapper objectMapper; // Para pasar el InDTO a Json hacia el otro microservicio
    private final RestTemplate restTemplate; // Transportador de respuestas HTTP y bodies

    public TumorTypeGatewayService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Obtener todos los tipos de tumor
     */
    public ResponseEntity<Object> getTumorTypeList() {
        String nestUrl = "http://clinic-service:3000/genosentinel/clinica/tumor-types";
        try {
            return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);
        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Obtener tipo de tumor por ID
     */
    public ResponseEntity<Object> getTumorTypeById(Long id) {
        String nestUrl = "http://clinic-service:3000/genosentinel/clinica/tumor-types/" + id;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);

        } catch (RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de NestJS
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Buscar tipos de tumor por sistema afectado
     */
    public ResponseEntity<Object> searchTumorTypesBySystem(String system) {
        String nestUrl = "http://clinic-service:3000/genosentinel/clinica/tumor-types/search?system=" + system;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);

        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Crear un nuevo tipo de tumor
     */
    public ResponseEntity<Object> createTumorType(TumorTypeInDTO dto) {
        String json;
        String nestUrl = "http://clinic-service:3000/genosentinel/clinica/tumor-types";

        try {
            // Serializando InDTO a json para que NestJS capte los campos
            json = objectMapper.writeValueAsString(dto);

            // Se crean los headers para indicarle a NestJS que el cuerpo está en formato JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Se empaqueta el JSON junto con los headers antes de enviarlo a NestJS
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            // Se envía la petición a NestJS
            return restTemplate.exchange(nestUrl, HttpMethod.POST, request, Object.class);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON: " + e.getMessage());

        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Actualizar un tipo de tumor
     */
    public ResponseEntity<Object> updateTumorType(TumorTypeInDTO dto, Long id) {
        String json;
        String nestUrl = "http://clinic-service:3000/genosentinel/clinica/tumor-types/" + id;

        try {
            json = objectMapper.writeValueAsString(dto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(json, headers);

            return restTemplate.exchange(nestUrl, HttpMethod.PUT, request, Object.class);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON: " + e.getMessage());

        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Eliminar un tipo de tumor
     */
    public ResponseEntity<Object> deleteTumorType(Long id) {
        String nestUrl = "http://clinic-service:3000/genosentinel/clinica/tumor-types/" + id;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.DELETE, null, Object.class);

        } catch (RestClientResponseException e) {
            // Si NestJS devuelve 404, 400, 500, etc, capturamos el JSON
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}