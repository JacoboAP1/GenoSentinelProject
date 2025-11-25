from rest_framework import viewsets
from rest_framework.response import Response
from genomics_api.models.serializers.ReportSerializer import ReportSerializer
from genomics_api.services.PatientVariantReportService import ReportService
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport
from drf_yasg.utils import swagger_auto_schema

class PatientVariantReportViewSet(viewsets.ModelViewSet):
    serializer_class = ReportSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Aunque este queryset no se usa realmente porque sobrescribimos todos los métodos del ViewSet
    # lo dejamos como .all() para evitar errores de inicialización del router
    queryset = PatientVariantReport.objects.all()

    @swagger_auto_schema(
        operation_summary="Obtener toda la lista de reportes de pacientes",
        operation_description="Retorna la información asociada de la lista de reportes",
        responses={200: "Success"}
    )
    def list(self, request):
        result = ReportService.list_reports()
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Obtener una reporte de paciente por ID",
        operation_description="Retorna la información asociada del reporte especificado",
        responses={200: "Success",
                   404: "Not found"}
    )
    def retrieve(self, request, pk=None):
        result = ReportService.get_report(pk)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Crear un reporte de paciente",
        operation_description="Retorna la info con la que se creó el reporte",
        responses={200: "Success",
                   400: "Bad request"}
    )
    def create(self, request, *args, **kwargs):
        result = ReportService.create_report(request.data)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Actualizar un reporte de paciente",
        operation_description="Retorna la información que se cambió",
        responses={200: "Success",
                   400: "Bad request"}
    )
    def update(self, request, *args, **kwargs):
        result = ReportService.update_report(self.get_object(), request.data)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Eliminar un reporte de paciente por su ID",
        operation_description="Retorna mensaje de éxito",
        responses={200: "Success",
                   404: "Not found"}
    )
    def destroy(self, request, *args, **kwargs):
        result = ReportService.delete_report(self.get_object())
        return Response({
            "success": True,
            "data": result
        })