from genomics_api.models.entities.Gene import Gene

class GeneService:

    @staticmethod
    def list_genes():
        return Gene.objects.all()

    @staticmethod
    def get_gene(id):
        return Gene.objects.get(pk=id)

    @staticmethod
    def create_gene(data):
        return Gene.objects.create(**data)

    @staticmethod
    def update_gene(instance, validated_data):
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.save()
        return instance

    @staticmethod
    def delete_gene(instance):
        instance.delete()