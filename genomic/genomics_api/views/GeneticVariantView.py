from rest_framework import viewsets
from genomics_api.models.serializers.GeneticVariantSerializer import GeneticVariantSerializer
from genomics_api.services import VariantService
from genomics_api.models.entities.GeneticVariant import GeneticVariant
from drf_yasg.utils import swagger_auto_schema

class GeneticVariantViewSet(viewsets.ModelViewSet):
    serializer_class = GeneticVariantSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Pero este queryset NO se usa realmente
    # DRF siempre llama a get_queryset() internamente para obtener los datos
    # Usamos .none() solo para satisfacer al router sin cargar datos de la BD
    queryset = GeneticVariant.objects.none()

    # DRF usa siempre estos método para el CRUD
    @swagger_auto_schema(
        operation_summary="Obtener toda la lista de variantes genéticas o por ID",
        operation_description="Retorna la información asociada de todas las variantes o solo la especificada",
        responses={200: GeneticVariantSerializer()}
    )
    def get_queryset(self):
        return VariantService.list_variants()

    @swagger_auto_schema(
        operation_summary="Crear una varinte genética",
        operation_description="Crea una variante genética asociándolo a un gen",
        responses={200: GeneticVariantSerializer()}
    )
    def perform_create(self, serializer):
        VariantService.create_variant(serializer.validated_data)

    @swagger_auto_schema(
        operation_summary="Actualizar una variante",
        operation_description="Retorna la información que se cambió",
        responses={200: GeneticVariantSerializer()}
    )
    def perform_update(self, serializer):
        VariantService.update_variant(self.get_object(), serializer.validated_data)

    @swagger_auto_schema(
        operation_summary="Eliminar una variante por ID",
        operation_description="Retorna la variante eliminada",
        responses={200: GeneticVariantSerializer()}
    )
    def perform_destroy(self, instance):
        VariantService.delete_variant(instance)