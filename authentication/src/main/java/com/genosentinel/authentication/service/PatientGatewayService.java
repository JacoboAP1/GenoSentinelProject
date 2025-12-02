package com.genosentinel.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genosentinel.authentication.models.dto.patientDTO.PatientInDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class PatientGatewayService {
	private ObjectMapper objectMapper;
	private final RestTemplate restTemplate;

	public PatientGatewayService(ObjectMapper objectMapper){
		this.objectMapper = objectMapper;
		this.restTemplate = new RestTemplate();
	}

	public ResponseEntity<Object> getPatientList() {
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients";
		return restTemplate.exchange(djangoUrl, HttpMethod.GET, null, Object.class);
	}

	public ResponseEntity<Object> getPatientById(Long id) {
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/" + id;
		try {
			return restTemplate.exchange(djangoUrl, HttpMethod.GET, null, Object.class);
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
	}

	public ResponseEntity<Object> createPatient(PatientInDTO dto) {
		String json;
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/";
		try {
			json = objectMapper.writeValueAsString(dto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<>(json, headers);
			return restTemplate.exchange(djangoUrl, HttpMethod.POST, request, Object.class);
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializando JSON: " + e.getMessage());
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
	}

	public ResponseEntity<Object> updatePatient(PatientInDTO dto, Long id) {
		String json;
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/" + id + "/";
		try {
			json = objectMapper.writeValueAsString(dto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<>(json, headers);
			return restTemplate.exchange(djangoUrl, HttpMethod.PUT, request, Object.class);
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializando JSON: " + e.getMessage());
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
	}

	public ResponseEntity<Object> deletePatient(Long id) {
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/" + id + "/";
		try {
			return restTemplate.exchange(djangoUrl, HttpMethod.DELETE, null, Object.class);
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
	}

	public ResponseEntity<Object> searchPatientsByName(String name) {
		String encoded = URLEncoder.encode(name == null ? "" : name, StandardCharsets.UTF_8);
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/search?name=" + encoded;
		return restTemplate.exchange(djangoUrl, HttpMethod.GET, null, Object.class);
	}

	public ResponseEntity<Object> findPatientsByStatus(String status) {
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/status/" + status;
		try {
			return restTemplate.exchange(djangoUrl, HttpMethod.GET, null, Object.class);
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
	}

	public ResponseEntity<Object> deactivatePatient(Long id) {
		String djangoUrl = "http://localhost:3000/genosentinel/clinica/patients/" + id + "/deactivate/";
		try {
			return restTemplate.exchange(djangoUrl, HttpMethod.PATCH, null, Object.class);
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
	}
}
