from rest_framework import serializers
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport

class ReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = PatientVariantReport
        fields = "__all__"
