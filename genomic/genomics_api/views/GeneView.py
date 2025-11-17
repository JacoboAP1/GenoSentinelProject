from rest_framework import viewsets
from genomics_api.models.serializers.GeneSerializer import GeneSerializer
from genomics_api.services import GeneService
from genomics_api.models.entities.Gene import Gene

class GeneViewSet(viewsets.ModelViewSet):
    serializer_class = GeneSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Pero este queryset NO se usa realmente.
    # DRF siempre llama a get_queryset() internamente para obtener los datos
    # Usamos .none() solo para satisfacer al router sin cargar datos de la BD
    queryset = Gene.objects.none() # Necesario para el router

    # DRF usa siempre estos método para el CRUD
    def get_queryset(self):
        return GeneService.list_genes()

    def perform_create(self, serializer):
        GeneService.create_gene(serializer.validated_data)

    def perform_update(self, serializer):
        GeneService.update_gene(self.get_object(), serializer.validated_data)

    def perform_destroy(self, instance):
        GeneService.delete_gene(instance)