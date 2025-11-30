package com.genosentinel.authentication.models.dto.reportDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportInDTO {
    private Long patient_id;
    private Long variant_id;
    private LocalDate detection_date;
    private Double allele_frequency;
}
