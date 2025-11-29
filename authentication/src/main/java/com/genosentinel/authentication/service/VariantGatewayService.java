package com.genosentinel.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genosentinel.authentication.models.dto.variantDTO.VariantInDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VariantGatewayService {
    private ObjectMapper objectMapper; // Para pasar el InDTO a Json
    // Hacia el otro microservicio
    private final RestTemplate restTemplate; // Transportador de respuestas HTTP
    // Y bodies

    public VariantGatewayService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<Object> getVariantList() {
        String djangoUrl = "http://localhost:8000/genomic/variants/";

        // RestTemplate recibe el JSON de Django y lo deserializa a un objeto Java
        // Spring Boot volverá a serializar ese objeto a JSON al enviarlo al cliente
        // Por eso retornamos Object, pero la respuesta final es JSON real
        return restTemplate.exchange(djangoUrl, HttpMethod.GET, null, Object.class);
    }

    public ResponseEntity<Object> getVariantById(Long id) {
        String djangoUrl = "http://localhost:8000/genomic/variants/" + id;

        try {
            return restTemplate.exchange(djangoUrl, HttpMethod.GET, null, Object.class);

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de django con HttpClient
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<Object> createVariant(VariantInDTO dto) {
        String json;
        String djangoUrl = "http://localhost:8000/genomic/variants/";

        try {
            // serializando InDTO a json para que Django capte los campos
            json = objectMapper.writeValueAsString(dto);

            // Se crean los headers para indicarle a Django que el cuerpo
            // del request está en formato JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Se empaqueta el JSON junto con los headers antes de enviarlo a Django
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            // Se envía la petición a Django
            // RestTemplate convierte el JSON de Django en un objeto Java
            // y Spring Boot lo vuelve a JSON al responder al cliente
            return restTemplate.exchange(djangoUrl, HttpMethod.POST, request, Object.class);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON: " + e.getMessage());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de django
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<Object> updateVariant(VariantInDTO dto, Long id) {
        String json;
        String djangoUrl = "http://localhost:8000/genomic/variants/" + id + "/";

        try {
            json = objectMapper.writeValueAsString(dto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(json, headers);

            return restTemplate.exchange(djangoUrl, HttpMethod.PUT, request, Object.class);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON: " + e.getMessage());

        } catch (org.springframework.web.client.RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<Object> deleteVariant(Long id) {
        String djangoUrl = "http://localhost:8000/genomic/variants/" + id + "/";

        try {
            return restTemplate.exchange(djangoUrl, HttpMethod.DELETE, null, Object.class);

        } catch (org.springframework.web.client.RestClientResponseException e) {
            // Si Django devuelve 404, 400, 500, etc, capturamos el JSON
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}
