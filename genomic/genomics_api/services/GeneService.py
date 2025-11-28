from genomics_api.exceptions.GeneExceptions import GeneNotFoundException, GeneDuplicatedException
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
        # filter().first() devuelve el objeto o None si no existe
        g = Gene.objects.filter(pk=id).first()
        if g is None:
            raise GeneNotFoundException("Enter an existing ID")

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

        fields = {"symbol": inDto.symbol, "full_name": inDto.full_name,
                  "function_summary": inDto.function_summary}

        for name, value in fields.items():
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if value is None:
                raise FieldNotFilledException(f"{name} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{name} cannot be blank")

        if Gene.objects.filter(symbol=inDto.symbol).exists():
            raise GeneDuplicatedException("Enter another symbol")

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

        fields = {"symbol": inDto.symbol, "full_name": inDto.full_name,
                  "function_summary": inDto.function_summary}

        for name, value in fields.items():
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if value is None:
                raise FieldNotFilledException(f"{name} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{name} cannot be blank")

        if Gene.objects.filter(symbol=inDto.symbol).exists():
            raise GeneDuplicatedException("Enter another symbol")

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