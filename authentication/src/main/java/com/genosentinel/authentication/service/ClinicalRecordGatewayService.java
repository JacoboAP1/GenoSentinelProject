package com.genosentinel.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genosentinel.authentication.models.dto.clinicalrecordDTO.ClinicalRecordInDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClinicalRecordGatewayService {
    private ObjectMapper objectMapper; // Para pasar el InDTO a Json hacia el otro microservicio
    private final RestTemplate restTemplate; // Transportador de respuestas HTTP y bodies

    public ClinicalRecordGatewayService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Obtener todas las historias clínicas
     */
    public ResponseEntity<Object> getClinicalRecordList() {
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records";

        // RestTemplate recibe el JSON de NestJS y lo deserializa a un objeto Java
        // Spring Boot volverá a serializar ese objeto a JSON al enviarlo al cliente
        return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);
    }

    /**
     * Obtener historia clínica por ID
     */
    public ResponseEntity<Object> getClinicalRecordById(String id) {
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records/" + id;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);

        } catch (RestClientResponseException e) {
            // Aquí se captura cualquier excepción y trae el cuerpo de NestJS
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Obtener historias clínicas por paciente
     */
    public ResponseEntity<Object> getClinicalRecordsByPatient(String patientId) {
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records/patient/" + patientId;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);

        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Obtener historias clínicas por tipo de tumor
     */
    public ResponseEntity<Object> getClinicalRecordsByTumorType(Long tumorTypeId) {
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records/tumor-type/" + tumorTypeId;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.GET, null, Object.class);

        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    /**
     * Crear una nueva historia clínica
     */
    public ResponseEntity<Object> createClinicalRecord(ClinicalRecordInDTO dto) {
        String json;
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records";

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
     * Actualizar una historia clínica
     */
    public ResponseEntity<Object> updateClinicalRecord(ClinicalRecordInDTO dto, String id) {
        String json;
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records/" + id;

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
     * Eliminar una historia clínica
     */
    public ResponseEntity<Object> deleteClinicalRecord(String id) {
        String nestUrl = "http://localhost:3000/genosentinel/clinica/clinical-records/" + id;

        try {
            return restTemplate.exchange(nestUrl, HttpMethod.DELETE, null, Object.class);

        } catch (RestClientResponseException e) {
            // Si NestJS devuelve 404, 400, 500, etc, capturamos el JSON
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}