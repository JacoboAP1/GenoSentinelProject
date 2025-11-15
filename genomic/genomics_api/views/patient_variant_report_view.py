from rest_framework import viewsets
from genomics_api.serializers.patient_variant_report_serializer import PatientVariantReportSerializer
from genomics_api.services import ReportService
from genomics_api.models.patient_variant_report import PatientVariantReport

class PatientVariantReportViewSet(viewsets.ModelViewSet):
    serializer_class = PatientVariantReportSerializer
    queryset = PatientVariantReport.objects.none()

    def get_queryset(self):
        return ReportService.list_reports()
