from genomics_api.exceptions.GeneExceptions import GeneNotFoundException, GeneDuplicatedException
from genomics_api.models import Gene
from genomics_api.models.dtos.GeneticVariantDtos.VariantInDTO import VariantInDTO
from genomics_api.models.dtos.GeneticVariantDtos.VariantOutDTO import VariantOutDTO
from genomics_api.models.entities.GeneticVariant import GeneticVariant
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException
from genomics_api.exceptions.GeneticVariantExceptions import InvalidImpactValueException, VariantNotFoundException, \
    ChromosomeDuplicatedException


class VariantService:

    @staticmethod
    def list_variants():
        variants = GeneticVariant.objects.all()
        response = []

        for v in variants:
            dto = VariantOutDTO(
                gene_id= v.gene_id,
                chromosome= v.chromosome,
                impact= v.impact
            )
            response.append(dto.to_dict())

        return response

    @staticmethod
    def get_variant(id):
        # filter().first() devuelve el objeto o None si no existe
        v = GeneticVariant.objects.filter(pk=id).first()

        if v is None:
            raise VariantNotFoundException("Enter an existing ID")

        dto = VariantOutDTO(
            gene_id= v.gene_id,
            chromosome= v.chromosome,
            impact= v.impact
        )

        return dto.to_dict()

    @staticmethod
    def create_variant(data):
        inDto = VariantInDTO (
            gene_id = data.get("gene_id"),
            chromosome = data.get("chromosome"),
            position = data.get("position"),
            reference_base = data.get("reference_base"),
            alternate_base = data.get("alternate_base"),
            impact = data.get("impact")
        )

        fields = {"gene_id": inDto.gene_id, "chromosome": inDto.chromosome,
            "position": inDto.position, "reference_base": inDto.reference_base, "alternate_base": inDto.alternate_base,
            "impact": inDto.impact}

        for name, value in fields.items():
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if value is None:
                raise FieldNotFilledException(f"{name} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{name} cannot be blank")

        if Gene.objects.filter(pk=inDto.gene_id).first() is None:
            raise GeneNotFoundException("Enter an existing gene_id")

        if GeneticVariant.objects.filter(chromosome=inDto.chromosome).exists():
            raise ChromosomeDuplicatedException("Enter another chromosome")

        IMPACT_VALUES = ["Missense", "Frameshift", "Nonsense", "Silent",
                         "Splice", "Other"]

        if inDto.impact not in IMPACT_VALUES:
            raise InvalidImpactValueException(
                f"impact must be one of: {IMPACT_VALUES}"
            )

        variant = GeneticVariant.objects.create(
            gene_id = inDto.gene_id,
            chromosome = inDto.chromosome,
            position = inDto.position,
            reference_base = inDto.reference_base,
            alternate_base = inDto.alternate_base,
            impact = inDto.impact
        )

        outDto = VariantOutDTO (
            gene_id = variant.gene_id,
            chromosome = variant.chromosome,
            impact = variant.impact
        )

        return outDto.to_dict()

    @staticmethod
    def update_variant(instance, data):
        inDto = VariantInDTO (
            gene_id = data.get("gene_id"),
            chromosome = data.get("chromosome"),
            position = data.get("position"),
            reference_base = data.get("reference_base"),
            alternate_base = data.get("alternate_base"),
            impact = data.get("impact")
        )

        fields = {"gene_id": inDto.gene_id, "chromosome": inDto.chromosome,
                  "position": inDto.position, "reference_base": inDto.reference_base, "alternate_base": inDto.alternate_base,
                  "impact": inDto.impact}

        for name, value in fields.items():
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if value is None:
                raise FieldNotFilledException(f"{name} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{name} cannot be blank")

        if Gene.objects.filter(pk=inDto.gene_id).first() is None:
            raise GeneNotFoundException("Enter an existing gene_id")

        if GeneticVariant.objects.filter(chromosome=inDto.chromosome).exists():
            raise ChromosomeDuplicatedException("Enter another chromosome")

        IMPACT_VALUES = ["Missense", "Frameshift", "Nonsense", "Silent",
                         "Splice", "Other"]

        if inDto.impact not in IMPACT_VALUES:
            raise InvalidImpactValueException(
                f"impact must be one of: {IMPACT_VALUES}"
            )

        instance.gene_id = inDto.gene_id
        instance.chromosome = inDto.chromosome
        instance.position = inDto.position
        instance.reference_base = inDto.reference_base
        instance.alternate_base = inDto.alternate_base
        instance.impact = inDto.impact

        instance.save()

        outDto = VariantOutDTO (
            gene_id= instance.gene_id,
            chromosome= instance.chromosome,
            impact= instance.impact
        )

        return outDto.to_dict()

    @staticmethod
    def delete_variant(instance):
        instance.delete()
        return {
            "message": "Variant deleted successfully"
        }