from genomics_api.models.dtos.GeneticVariantDtos.VariantInDTO import VariantInDTO
from genomics_api.models.dtos.GeneticVariantDtos.VariantOutDTO import VariantOutDTO
from genomics_api.models.entities.GeneticVariant import GeneticVariant
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException
from genomics_api.exceptions.GeneticVariantExceptions import InvalidImpactValueException


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
        v = GeneticVariant.objects.get(pk=id)

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

        required_fields = [inDto.gene_id, inDto.chromosome, inDto.position, inDto.reference_base,
                           inDto.alternate_base, inDto.impact]

        for field in required_fields:
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not field:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(field, str) and field.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

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

        required_fields = [inDto.gene_id, inDto.chromosome, inDto.position, inDto.reference_base,
                           inDto.alternate_base, inDto.impact]

        for field in required_fields:
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not field:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(field, str) and field.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

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