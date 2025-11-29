package com.genosentinel.authentication.models.dto.variantDTO;

import lombok.Data;

@Data
public class VariantInDTO {
    private Long gene_id;
    private String chromosome;
    private Long position;
    private String reference_base;
    private String alternate_base;
    private Impact impact;

    public enum Impact {
        Missense,
        Frameshift,
        Nonsense,
        Silent,
        Splice,
        Other
    }

}
