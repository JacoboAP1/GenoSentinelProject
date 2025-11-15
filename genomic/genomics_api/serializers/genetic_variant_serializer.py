from rest_framework import serializers
from genomics_api.models.genetic_variant import GeneticVariant

class GeneticVariantSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = '__all__'
