from django.db import models
from genomics_api.models.entities.GeneticVariant import GeneticVariant

class PatientVariantReport(models.Model):
    patient_id = models.IntegerField() # Viene del microservicio de clínica
    variant = models.ForeignKey(GeneticVariant, on_delete=models.CASCADE, db_column='variant_id')
    detection_date = models.DateField()
    allele_frequency = models.DecimalField(max_digits=5, decimal_places=2, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'genomics_api_gene_patientvariantreport'
