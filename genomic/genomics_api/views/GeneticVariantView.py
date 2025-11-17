from rest_framework import viewsets
from genomics_api.models.serializers.GeneticVariantSerializer import GeneticVariantSerializer
from genomics_api.services import VariantService
from genomics_api.models.entities.GeneticVariant import GeneticVariant

class GeneticVariantViewSet(viewsets.ModelViewSet):
    serializer_class = GeneticVariantSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Pero este queryset NO se usa realmente.
    # DRF siempre llama a get_queryset() internamente para obtener los datos
    # Usamos .none() solo para satisfacer al router sin cargar datos de la BD
    queryset = GeneticVariant.objects.none()

    # DRF usa siempre estos método para el CRUD
    def get_queryset(self):
        return VariantService.list_variants()

    def perform_create(self, serializer):
        VariantService.create_variant(serializer.validated_data)

    def perform_update(self, serializer):
        VariantService.update_variant(self.get_object(), serializer.validated_data)

    def perform_destroy(self, instance):
        VariantService.delete_variant(instance)