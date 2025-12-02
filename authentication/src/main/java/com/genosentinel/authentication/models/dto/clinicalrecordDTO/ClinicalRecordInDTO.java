package com.genosentinel.authentication.models.dto.clinicalrecordDTO;

import lombok.Data;

@Data
public class ClinicalRecordInDTO {
    private Long patientId;           // UUID del paciente
    private Long tumorTypeId;           // ID del tipo de tumor
    private String diagnosisDate;       // Formato: "YYYY-MM-DD"
    private String stage;               // Estadio del tumor: "I", "IIA", "IIIB", "IV"
    private String treatmentProtocol;   // Protocolo de tratamiento

}