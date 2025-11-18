from rest_framework import serializers
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport

class PatientVariantReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = PatientVariantReport
        fields = [
            'id',
            'patient_id',
            'variant',
            'detection_date',
            'allele_frequency',
        ]
