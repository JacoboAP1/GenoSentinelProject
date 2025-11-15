from django.db import models
from .genetic_variant import GeneticVariant

class PatientVariantReport(models.Model):
    patient_id = models.IntegerField() # Viene del microservicio de clínica
    variant = models.ForeignKey(GeneticVariant, on_delete=models.CASCADE)
    detection_date = models.DateField()
    allele_frequency = models.DecimalField(max_digits=5, decimal_places=2, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'patientvariantreport'
