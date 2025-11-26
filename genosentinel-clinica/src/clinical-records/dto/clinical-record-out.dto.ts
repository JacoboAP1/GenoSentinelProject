import { ApiProperty } from '@nestjs/swagger';
import { PatientDtoOut } from '../../patients/dto/patient-out.dto';
import { TumorTypeDtoOut } from '../../tumor-types/dto/tumor-type-out.dto';

/**
 * DTO OUT de respuesta para historia clínica
 */
export class ClinicalRecordDtoOut {
    @ApiProperty({ example: 1, description: 'ID de la historia clínica' })
    id: number;

    @ApiProperty({ type: () => PatientDtoOut, description: 'Información del paciente' })
    patient: PatientDtoOut;

    @ApiProperty({ type: () => TumorTypeDtoOut, description: 'Información del tipo de tumor' })
    tumorType: TumorTypeDtoOut;

    @ApiProperty({ example: '2024-01-15', description: 'Fecha de diagnóstico' })
    diagnosisDate: Date;

    @ApiProperty({ example: 'IIA', description: 'Estadio del tumor' })
    stage: string;

    @ApiProperty({ example: 'Quimioterapia AC + Radioterapia', description: 'Protocolo de tratamiento' })
    treatmentProtocol: string;
}