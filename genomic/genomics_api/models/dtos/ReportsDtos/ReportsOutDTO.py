class ReportsOutDTO:
    def __init__(self, patient_id, variant_id, detection_date):
        # Solo mostrar datos necesarios, no todos
        self.patient_id = patient_id
        self.variant_id = variant_id
        self.detection_date = detection_date

    def to_dict(self):
        return {
            "patient_id": self.patient_id,
            "variant_id": self.variant_id,
            "detection_date": self.detection_date
        }
