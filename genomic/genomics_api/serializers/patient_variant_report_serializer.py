from rest_framework import serializers
from genomics_api.models import PatientVariantReport

class PatientVariantReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = PatientVariantReport
        fields = '__all__'
