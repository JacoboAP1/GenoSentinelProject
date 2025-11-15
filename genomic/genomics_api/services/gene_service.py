from genomics_api.models import Gene

class GeneService:

    @staticmethod
    def list_genes():
        return Gene.objects.all()

    @staticmethod
    def create_gene(data):
        return Gene.objects.create(**data)
