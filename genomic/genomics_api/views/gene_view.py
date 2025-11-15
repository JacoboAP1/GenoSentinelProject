from rest_framework import viewsets
from genomics_api.serializers.gene_serializer import GeneSerializer
from genomics_api.services import GeneService
from genomics_api.models.gene import Gene

class GeneViewSet(viewsets.ModelViewSet):
    serializer_class = GeneSerializer
    queryset = Gene.objects.none() # Necesario para el router

    def get_queryset(self):
        return GeneService.list_genes()
