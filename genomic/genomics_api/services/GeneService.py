from genomics_api.models.dtos.GeneDtos.GeneInDTO import GeneInDTO
from genomics_api.models.entities.Gene import Gene
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException
from genomics_api.models.dtos.GeneDtos.GeneOutDTO import GeneOutDTO

class GeneService:

    @staticmethod
    def list_genes():
        genes = Gene.objects.all()
        response = []

        for g in genes:
            dto = GeneOutDTO(
                symbol=g.symbol,
                full_name=g.full_name
            )
            response.append(dto.to_dict())

        return response

    @staticmethod
    def get_gene(id):
        g = Gene.objects.get(pk=id)

        dto = GeneOutDTO(
            symbol=g.symbol,
            full_name=g.full_name
        )

        return dto.to_dict()

    @staticmethod
    def create_gene(data):
        inDto = GeneInDTO (
            symbol= data.get("symbol"),
            full_name= data.get("full_name"),
            function_summary= data.get("function_summary")
        )

        if not inDto.symbol or inDto.symbol.strip() == "":
            raise FieldNotFilledException("symbol is required")

        if not inDto.full_name or inDto.full_name.strip() == "":
            raise FieldNotFilledException("full_name is required")

        if not inDto.function_summary or inDto.function_summary.strip() == "":
            raise FieldNotFilledException("function_summary is required")

        gene = Gene.objects.create(
            symbol= inDto.symbol,
            full_name= inDto.full_name,
            function_summary= inDto.function_summary
        )

        outDto = GeneOutDTO (
            symbol= gene.symbol,
            full_name= gene.full_name
        )

        return outDto.to_dict()

    @staticmethod
    def update_gene(instance, data):
        inDto = GeneInDTO (
            symbol= data.get("symbol"),
            full_name= data.get("full_name"),
            function_summary= data.get("function_summary")
        )

        if not inDto.symbol or inDto.symbol.strip() == "":
            raise FieldNotFilledException("symbol is required")

        if not inDto.full_name or inDto.full_name.strip() == "":
            raise FieldNotFilledException("full_name is required")

        if not inDto.function_summary or inDto.function_summary.strip() == "":
            raise FieldNotFilledException("function_summary is required")

        instance.symbol = inDto.symbol
        instance.full_name = inDto.full_name
        instance.function_summary = inDto.function_summary

        instance.save()

        outDto = GeneOutDTO (
            symbol= instance.symbol,
            full_name= instance.full_name
        )

        return outDto.to_dict()

    @staticmethod
    def delete_gene(instance):
        instance.delete()
        return {
            "message": "Gene deleted successfully"
        }