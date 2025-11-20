from rest_framework import viewsets
from rest_framework.response import Response
from genomics_api.models.serializers import PatientVariantReportSerializer
from genomics_api.services.PatientVariantReportService import ReportService
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport
from drf_yasg.utils import swagger_auto_schema

class PatientVariantReportViewSet(viewsets.ModelViewSet):
    serializer_class = PatientVariantReportSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Pero este queryset NO se usa realmente
    # DRF siempre llama a get_queryset() internamente para obtener los datos
    # Usamos .none() solo para satisfacer al router sin cargar datos de la BD
    queryset = PatientVariantReport.objects.none()

    # Acá no se usa query set para listar porque se implementa
    # Una lógica de negocio distina en el servicio
    # Ya que hay entidades provenientes de otro microservicio

    # GET /reports/
    @swagger_auto_schema(
        operation_summary="Obtener toda la lista de reportes de pacientes o por su ID",
        operation_description="Retorna la información asociada de todas los reportes o el reporte especificado",
        responses={200: PatientVariantReportSerializer(),
                   404: PatientVariantReportSerializer()}
    )
    def list(self, request, *args, **kwargs):
        data = ReportService.list_reports()
        return Response(data)

    # POST /reports/
    @swagger_auto_schema(
        operation_summary="Crear un reporte de un paciente",
        operation_description="Retorna la info con la que se guardó la creación (DTO)",
        responses={200: PatientVariantReportSerializer(),
                   400: PatientVariantReportSerializer()}
    )
    def perform_create(self, serializer):
        ReportService.create_report(serializer.validated_data)

    @swagger_auto_schema(
        operation_summary="Actualizar un reporte",
        operation_description="Retorna la información que se cambió",
        responses={200: PatientVariantReportSerializer(),
                   400: PatientVariantReportSerializer()}
    )
    def perform_update(self, serializer):
        ReportService.update_report(self.get_object(), serializer.validated_data)

    @swagger_auto_schema(
        operation_summary="Eliminar un reporte por su ID",
        operation_description="Retorna el reporte eliminado",
        responses={200: PatientVariantReportSerializer(),
                   404: PatientVariantReportSerializer()}
    )
    def perform_destroy(self, instance):
        ReportService.delete_report(instance)