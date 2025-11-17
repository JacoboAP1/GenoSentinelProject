from rest_framework import viewsets
from rest_framework.response import Response
from genomics_api.models.serializers import PatientVariantReportSerializer
from genomics_api.services import ReportService
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport

class PatientVariantReportViewSet(viewsets.ModelViewSet):
    serializer_class = PatientVariantReportSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Pero este queryset NO se usa realmente.
    # DRF siempre llama a get_queryset() internamente para obtener los datos
    # Usamos .none() solo para satisfacer al router sin cargar datos de la BD
    queryset = PatientVariantReport.objects.none()

    # Acá no se usa query set para listar porque se implementa
    # Una lógica de negocio distina en el servicio
    # Ya que hay entidades provenientes de otro microservicio

    # GET /reports/
    def list(self, request, *args, **kwargs):
        data = ReportService.list_reports()
        return Response(data)

    # POST /reports/
    def perform_create(self, serializer):
        ReportService.create_report(serializer.validated_data)

    def perform_update(self, serializer):
        ReportService.update_report(self.get_object(), serializer.validated_data)

    def perform_destroy(self, instance):
        ReportService.delete_report(instance)