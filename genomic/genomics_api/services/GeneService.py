from genomics_api.models.entities.Gene import Gene
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException

class GeneService:

    @staticmethod
    def list_genes():
        return Gene.objects.all()

    @staticmethod
    def get_gene(id):
        return Gene.objects.get(pk=id)

    @staticmethod
    def create_gene(data):
        symbol = data.get("symbol")
        full_name = data.get("full_name")
        function_summary = data.get("function_summary")

        if not symbol or symbol.strip() == "":
            raise FieldNotFilledException("symbol is required")

        if not full_name or full_name.strip() == "":
            raise FieldNotFilledException("full_name is required")

        if not function_summary or function_summary.strip() == "":
            raise FieldNotFilledException("function_summary is required")

        return Gene.objects.create(**data)

    @staticmethod
    def update_gene(instance, validated_data):
        symbol = validated_data.get("symbol")
        full_name = validated_data.get("full_name")
        function_summary = validated_data.get("function_summary")

        if not symbol or symbol.strip() == "":
            raise FieldNotFilledException("symbol is required")

        if not full_name or full_name.strip() == "":
            raise FieldNotFilledException("full_name is required")

        if not function_summary or function_summary.strip() == "":
            raise FieldNotFilledException("function_summary is required")

        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.save()
        return instance

    @staticmethod
    def delete_gene(instance):
        instance.delete()