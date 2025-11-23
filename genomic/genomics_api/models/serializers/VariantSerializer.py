from rest_framework import serializers
from genomics_api.models.entities.GeneticVariant import GeneticVariant

class VariantSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = "__all__"
