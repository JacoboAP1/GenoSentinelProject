from genomics_api.models.entities.GeneticVariant import GeneticVariant
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException
from genomics_api.exceptions.GeneticVariantExceptions import InvalidImpactValueException

class VariantService:

    @staticmethod
    def list_variants():
        return GeneticVariant.objects.all()

    @staticmethod
    def get_variant(id):
        return GeneticVariant.objects.get(pk=id)

    @staticmethod
    def create_variant(data):
        required_fields = ["gene", "chromosome", "position", "reference_base",
                           "alternate_base", "impact"]
        IMPACT_VALUES = [
            "Missense",
            "Frameshift",
            "Nonsense",
            "Silent",
            "Splice",
            "Other"
        ]

        for field in required_fields:
            value = data.get(field)

            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not value:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

        impact = data.get("impact")
        if impact not in IMPACT_VALUES:
            raise InvalidImpactValueException(
                f"impact must be one of: {IMPACT_VALUES}"
            )

        return GeneticVariant.objects.create(**data)

    @staticmethod
    def update_variant(instance, validated_data):
        required_fields = ["gene", "chromosome", "position", "reference_base",
                           "alternate_base", "impact"]

        IMPACT_VALUES = [
            "Missense",
            "Frameshift",
            "Nonsense",
            "Silent",
            "Splice",
            "Other"
        ]

        for field in required_fields:
            value = validated_data.get(field)

            if not value:
                raise FieldNotFilledException(f"{field} is required")

            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

        impact = validated_data.get("impact")
        if impact not in IMPACT_VALUES:
            raise InvalidImpactValueException(
                f"impact must be one of: {IMPACT_VALUES}"
            )

        for attr, value in validated_data.items():
            setattr(instance, attr, value)

        instance.save()
        return instance

    @staticmethod
    def delete_variant(instance):
        instance.delete()