class ReportsInDTO:
    def __init__(self, patient_id, variant_id, detection_date, allele_frequency):
        self.patient_id = patient_id
        self.variant_id = variant_id
        self.detection_date = detection_date
        self.allele_frequency = allele_frequency
