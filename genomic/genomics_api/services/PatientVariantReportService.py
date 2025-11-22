import requests
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport
from genomics_api.exceptions.FieldNotFilledException import FieldNotFilledException

CLINIC_MS_URL = "http://localhost:3000/patients/"

class ReportService:

    @staticmethod
    def list_reports():
        reports = PatientVariantReport.objects.all()
        enriched = []

        for r in reports:
            # Llamado al microservicio
            patient_response = requests.get(f"{CLINIC_MS_URL}{r.patient_id}")
            patient_id = None

            if patient_response.status_code == 200:
                patient_json = patient_response.json()
                patient_id = patient_json.get("id")

            enriched.append({
                "id": r.id,
                "patient_id": patient_id,    
                "variant_id": r.variant_id,
                "detection_date": r.detection_date,
                "allele_frequency": r.allele_frequency
            })

        return enriched

    @staticmethod
    def get_report(id):
        return PatientVariantReport.objects.get(pk=id)

    @staticmethod
    def create_report(data):
        fields = [
            'patient_id',
            'variant',
            'detection_date',
            'allele_frequency',
        ]

        for field in fields:
            value = data.get(field)

            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not value:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

        return PatientVariantReport.objects.create(**data)

    @staticmethod
    def update_report(instance, validated_data):
        fields = [
            'patient_id',
            'variant',
            'detection_date',
            'allele_frequency',
        ]

        for field in fields:
            value = validated_data.get(field)

            # Primero valida que exista (sirve tanto para strings como para objetos)
            if not value:
                raise FieldNotFilledException(f"{field} is required")

            # Si es string, valida el vacío
            if isinstance(value, str) and value.strip() == "":
                raise FieldNotFilledException(f"{field} cannot be blank")

        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.save()
        return instance

    @staticmethod
    def delete_report(instance):
        instance.delete()