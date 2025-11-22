from rest_framework import serializers
from genomics_api.models.entities.GeneticVariant import GeneticVariant

class GeneticVariantSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = [
            'id',
            'gene',
            'chromosome',
            'position',
            'reference_base',
            'alternate_base',
            'impact'
        ]