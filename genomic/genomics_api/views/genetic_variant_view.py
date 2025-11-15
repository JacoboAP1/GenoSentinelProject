from rest_framework import viewsets
from genomics_api.serializers.genetic_variant_serializer import GeneticVariantSerializer
from genomics_api.services import VariantService
from genomics_api.models.genetic_variant import GeneticVariant

class GeneticVariantViewSet(viewsets.ModelViewSet):
    serializer_class = GeneticVariantSerializer
    queryset = GeneticVariant.objects.none()

    def get_queryset(self):
        return VariantService.list_variants()
