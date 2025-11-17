import requests
from genomics_api.models.entities.PatientVariantReport import PatientVariantReport

CLINIC_MS_URL = "http://localhost:3000/patients/"

class ReportService:

    @staticmethod
    def list_reports():
        reports = PatientVariantReport.objects.all()
        enriched = []

        for r in reports:
            # Obtener info desde microservicio Clínica
            patient_response = requests.get(f"{CLINIC_MS_URL}{r.patient_id}")
            patient_data = None

            if patient_response.status_code == 200:
                patient_data = patient_response.json()

            enriched.append({
                "id": r.id,
                "patient_id": patient_data,
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
        return PatientVariantReport.objects.create(**data)

    @staticmethod
    def update_report(instance, validated_data):
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.save()
        return instance

    @staticmethod
    def delete_report(instance):
        instance.delete()