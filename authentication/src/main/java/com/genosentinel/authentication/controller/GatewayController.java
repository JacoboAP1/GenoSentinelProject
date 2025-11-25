package com.genosentinel.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    // URL de los microservicios destino
    private final String CLINIC_BASE_URL = "http://localhost:3000/genosentinel/clinica";   // Nest
    private final String GENOMICS_BASE_URL = "http://localhost:8000/genomic"; // Django

    @RequestMapping("/clinica/**")
    public ResponseEntity<?> redirectClinic(HttpServletRequest request, @RequestBody(required = false) String body) {

        String path = request.getRequestURI().replace("/clinica", "");
        String url = CLINIC_BASE_URL + path;

        return forwardRequest(request, body, url);
    }

    @RequestMapping("/genomic/**")
    public ResponseEntity<?> redirectGenomics(HttpServletRequest request, @RequestBody(required = false) String body) {

        String path = request.getRequestURI().replace("/genomic", "");
        String url = GENOMICS_BASE_URL + path;

        return forwardRequest(request, body, url);
    }

    private ResponseEntity<?> forwardRequest(HttpServletRequest req, String body, String url) {

        HttpMethod method = HttpMethod.valueOf(req.getMethod());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Forward JWT
        String auth = req.getHeader("Authorization");
        if (auth != null)
            headers.set("Authorization", auth);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url, method, entity, String.class);
    }
}
