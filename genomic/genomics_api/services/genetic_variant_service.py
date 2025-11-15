from genomics_api.models.genetic_variant import GeneticVariant

class VariantService:

    @staticmethod
    def list_variants():
        return GeneticVariant.objects.all()

    @staticmethod
    def create_variant(data):
        return GeneticVariant.objects.create(**data)
