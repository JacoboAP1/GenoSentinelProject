from rest_framework import serializers
from genomics_api.models.entities.Gene import Gene

class GeneSerializer(serializers.ModelSerializer):
    class Meta:
        model = Gene
        fields = [
            'id',
            'symbol',
            'full_name',
            'function_summary'
        ]