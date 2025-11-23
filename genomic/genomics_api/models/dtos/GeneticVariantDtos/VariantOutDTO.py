class VariantOutDTO:
    def __init__(self, gene_id, chromosome, impact):
        self.gene_id = gene_id
        self.chromosome = chromosome
        self.impact = impact

    def to_dict(self):
        return {
            "gene_id": self.gene_id,
            "chromosome": self.chromosome,
            "impact": self.impact
        }
