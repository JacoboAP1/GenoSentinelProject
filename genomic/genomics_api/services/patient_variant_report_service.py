from genomics_api.models import PatientVariantReport

class ReportService:

    @staticmethod
    def list_reports():
        return PatientVariantReport.objects.all()

    @staticmethod
    def create_report(data):
        return PatientVariantReport.objects.create(**data)
