from rest_framework import viewsets
from genomics_api.models.serializers.GeneSerializer import GeneSerializer
from genomics_api.services import GeneService
from genomics_api.models.entities.Gene import Gene
from drf_yasg.utils import swagger_auto_schema

class GeneViewSet(viewsets.ModelViewSet):
    serializer_class = GeneSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Pero este queryset NO se usa realmente
    # DRF siempre llama a get_queryset() internamente para obtener los datos
    # Usamos .none() solo para satisfacer al router sin cargar datos de la BD
    queryset = Gene.objects.none() # Necesario para el router

    # DRF usa siempre estos método para el CRUD
    @swagger_auto_schema(
        operation_summary="Obtener toda la lista de genes o por su ID",
        operation_description="Retorna la información asociada de todos los genes o solo el gen especificado",
        responses={200: GeneSerializer()}
    )
    def get_queryset(self):
        return GeneService.list_genes()

    @swagger_auto_schema(
        operation_summary="Crear un gen",
        operation_description="Retorna la info con la que se creó el gen",
        responses={200: GeneSerializer()}
    )
    def perform_create(self, serializer):
        GeneService.create_gene(serializer.validated_data)

    @swagger_auto_schema(
        operation_summary="Actualizar un gen",
        operation_description="Retorna la información que se cambió",
        responses={200: GeneSerializer()}
    )
    def perform_update(self, serializer):
        GeneService.update_gene(self.get_object(), serializer.validated_data)

    @swagger_auto_schema(
        operation_summary="Eliminar un gen por su ID",
        operation_description="Retorna el gen eliminado",
        responses={200: GeneSerializer()}
    )
    def perform_destroy(self, instance):
        GeneService.delete_gene(instance)