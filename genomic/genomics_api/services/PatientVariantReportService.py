from genomics_api.exceptions.GeneticVariantExceptions import VariantNotFoundException
from genomics_api.exceptions.ReportExceptions import PatientNotFoundException, ReportNotFoundException, \
    ReportDuplicatedException
from genomics_api.models import GeneticVariant
from genomics_api.models.dtos.ReportsDtos.ReportsInDTO import ReportsInDTO
from genomics_api.models.dtos.ReportsDtos.ReportsOutDTO import ReportsOutDTO
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException
from genomics_api.gateway.ClinicServiceClient import ClinicServiceClient

class ReportService:

    @staticmethod
    def list_reports():
        reports = PatientVariantReport.objects.all()
        response = []

        for r in reports:
            dto = ReportsOutDTO(
                patient_id= r.patient_id,
                variant_id= r.variant_id,
                detection_date= r.detection_date
            )
            response.append(dto.to_dict())

        return response

    @staticmethod
    def get_report(id):
        # filter().first() devuelve el objeto o None si no existe
        r = PatientVariantReport.objects.filter(pk=id).first()
        if r is None:
            raise ReportNotFoundException("Enter an existing report_id")

        dto = ReportsOutDTO(
            patient_id= r.patient_id,
            variant_id= r.variant_id,
            detection_date= r.detection_date
        )

        return dto.to_dict()

    @staticmethod
    def create_report(data):
        inDto = ReportsInDTO (
            patient_id= data.get("patient_id"),
            variant_id= data.get("variant_id"),
            detection_date= data.get("detection_date"),
            allele_frequency= data.get("allele_frequency")
        )

        client = ClinicServiceClient.get_patient_by_id(inDto.patient_id)

        if not client:
            raise PatientNotFoundException("Enter an existing ID")

        if GeneticVariant.objects.filter(pk=inDto.variant_id).first() is None:
            raise VariantNotFoundException("Enter an existing variant_id")

        fields = {
            "patient_id": inDto.patient_id,
            "variant_id": inDto.variant_id,
            "detection_date": inDto.detection_date,
            "allele_frequency": inDto.allele_frequency,
        }

        # Ciclo que verifica que cada atributo no sea nulo y que no esté vacío
        for name, value in fields.items():
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if value is None:
                raise FieldNotFilledException(f"{name} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{name} cannot be blank")

        if PatientVariantReport.objects.filter(detection_date=inDto.detection_date).exists():
            raise ReportDuplicatedException("Enter another date")

        report = PatientVariantReport.objects.create(
            patient_id = inDto.patient_id,
            variant_id = inDto.variant_id,
            detection_date = inDto.detection_date,
            allele_frequency = inDto.allele_frequency
        )

        outDto = ReportsOutDTO(
            patient_id= report.patient_id,
            variant_id= report.variant_id,
            detection_date= report.detection_date
        )

        return outDto.to_dict()

    @staticmethod
    def update_report(instance, data):
        inDto = ReportsInDTO (
            patient_id= data.get("patient_id"),
            variant_id= data.get("variant_id"),
            detection_date= data.get("detection_date"),
            allele_frequency= data.get("allele_frequency")
        )

        client = ClinicServiceClient.get_patient_by_id(inDto.patient_id)

        if not client:
            raise PatientNotFoundException("Enter an existing ID")

        if GeneticVariant.objects.filter(pk=inDto.variant_id).first() is None:
            raise VariantNotFoundException("Enter an existing variant_id")

        fields = {
            "patient_id": inDto.patient_id,
            "variant_id": inDto.variant_id,
            "detection_date": inDto.detection_date,
            "allele_frequency": inDto.allele_frequency,
        }

        # Ciclo que verifica que cada atributo no sea nulo y que no esté vacío
        for name, value in fields.items():
            # Primero valida que exista (sirve tanto para strings como para objetos)
            if value is None:
                raise FieldNotFilledException(f"{name} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{name} cannot be blank")

        if PatientVariantReport.objects.filter(detection_date=inDto.detection_date).exists():
            raise ReportDuplicatedException("Enter another date")

        instance.patient_id = inDto.patient_id
        instance.variant_id = inDto.variant_id
        instance.detection_date = inDto.detection_date
        instance.allele_frequency = inDto.allele_frequency

        instance.save()

        outDto = ReportsOutDTO (
            patient_id=instance.patient_id,
            variant_id=instance.variant_id,
            detection_date=instance.detection_date
        )

        return outDto.to_dict()

    @staticmethod
    def delete_report(instance):
        instance.delete()
        return {
            "message": "Report deleted successfully"
        }