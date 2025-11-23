class VariantInDTO:
    def __init__(self, gene_id, chromosome, position, reference_base, alternate_base, impact):
        self.gene_id = gene_id
        self.chromosome = chromosome
        self.position = position
        self.reference_base = reference_base
        self.alternate_base = alternate_base
        self.impact = impact
