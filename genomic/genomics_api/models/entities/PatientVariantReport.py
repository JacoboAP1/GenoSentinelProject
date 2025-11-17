from django.db import models
from genomics_api.models.entities.GeneticVariant import GeneticVariant

class PatientVariantReport(models.Model):
    patient_id = models.IntegerField()  # viene de microservicio de clínica (Nest)
    variant = models.ForeignKey(GeneticVariant, on_delete=models.CASCADE)
    detection_date = models.DateField()
    allele_frequency = models.DecimalField(max_digits=5, decimal_places=2, blank=True, null=True)

    def __str__(self):
        return f"Patient {self.patient_id} - Variant {self.variant_id}"
