from rest_framework import serializers
from genomics_api.models.entities.GeneticVariant import GeneticVariant

class VariantSerializer(serializers.ModelSerializer):
    class Meta:
        # Solo se deja vacío para satisfacer el router
        # Pero se usan los DTO en su lugar
        model = GeneticVariant
        fields = "__all__"
