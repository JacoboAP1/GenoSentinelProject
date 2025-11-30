
package com.genosentinel.authentication.models.dto.patientDTO;

import lombok.Data;

@Data
public class PatientInDTO {
    private String firstName;
    private String lastName;
    private String birthDate; // Formato: "YYYY-MM-DD"
    private String gender;    // "Masculino", "Femenino", "Otro"
    private String status;    // "Activo", "Seguimiento", "Inactivo"
}

