from genomics_api.models.dtos.ReportsDtos.ReportsInDTO import ReportsInDTO
from genomics_api.models.dtos.ReportsDtos.ReportsOutDTO import ReportsOutDTO
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException

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
        r = PatientVariantReport.objects.get(pk=id)

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

        fields = [inDto.patient_id, inDto.variant_id, inDto.detection_date, inDto.allele_frequency]

        for field in fields:

            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not field:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(field, str) and field.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

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

        fields = [inDto.patient_id, inDto.variant_id, inDto.detection_date, inDto.allele_frequency]

        for field in fields:

            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not field:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(field, str) and field.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

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