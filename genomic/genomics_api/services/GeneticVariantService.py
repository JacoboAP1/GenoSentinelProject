from genomics_api.models.entities.GeneticVariant import GeneticVariant

class VariantService:

    @staticmethod
    def list_variants():
        return GeneticVariant.objects.all()

    @staticmethod
    def get_variant(id):
        return GeneticVariant.objects.get(pk=id)

    @staticmethod
    def create_variant(data):
        return GeneticVariant.objects.create(**data)

    @staticmethod
    def update_variant(instance, validated_data):
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.save()
        return instance

    @staticmethod
    def delete_variant(instance):
        instance.delete()