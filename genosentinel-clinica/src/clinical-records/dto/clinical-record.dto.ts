import { ApiProperty } from '@nestjs/swagger';
import { IsString, IsNotEmpty, IsDateString, IsOptional, IsUUID, IsNumber } from 'class-validator';
import { PatientResponseDto } from '../../patients/dto/patient.dto';
import { TumorTypeResponseDto } from '../../tumor-types/dto/tumor-type.dto';

/**
 * DTO para crear una historia clínica
 */
export class CreateClinicalRecordDto {
    @ApiProperty({ example: 'uuid-patient-123', description: 'ID del paciente' })
    @IsUUID('4', { message: 'ID de paciente inválido' })
    @IsNotEmpty({ message: 'El ID del paciente es obligatorio' })
    patientId: string;

    @ApiProperty({ example: 1, description: 'ID del tipo de tumor' })
    @IsNumber({}, { message: 'ID de tipo de tumor inválido' })
    @IsNotEmpty({ message: 'El ID del tipo de tumor es obligatorio' })
    tumorTypeId: number;

    @ApiProperty({ example: '2024-01-15', description: 'Fecha de diagnóstico (YYYY-MM-DD)' })
    @IsDateString({}, { message: 'Formato de fecha inválido' })
    @IsNotEmpty({ message: 'La fecha de diagnóstico es obligatoria' })
    diagnosisDate: string;

    @ApiProperty({ example: 'IIA', description: 'Estadio del tumor', enum: ['I', 'IA', 'IB', 'IIA', 'IIB', 'IIIA', 'IIIB', 'IIIC', 'IV'] })
    @IsString()
    @IsNotEmpty({ message: 'El estadio del tumor es obligatorio' })
    stage: string;

    @ApiProperty({ example: 'Quimioterapia AC + Radioterapia', description: 'Protocolo de tratamiento', required: false })
    @IsString()
    @IsOptional()
    treatmentProtocol?: string;

    @ApiProperty({ example: 'Paciente respondiendo bien al tratamiento', description: 'Observaciones clínicas', required: false })
    @IsString()
    @IsOptional()
    observations?: string;
}

/**
 * DTO para actualizar una historia clínica
 */
export class UpdateClinicalRecordDto {
    @ApiProperty({ example: 'uuid-patient-123', description: 'ID del paciente', required: false })
    @IsUUID('4', { message: 'ID de paciente inválido' })
    @IsOptional()
    patientId?: string;

    @ApiProperty({ example: 1, description: 'ID del tipo de tumor', required: false })
    @IsNumber({}, { message: 'ID de tipo de tumor inválido' })
    @IsOptional()
    tumorTypeId?: number;

    @ApiProperty({ example: '2024-01-15', description: 'Fecha de diagnóstico (YYYY-MM-DD)', required: false })
    @IsDateString({}, { message: 'Formato de fecha inválido' })
    @IsOptional()
    diagnosisDate?: string;

    @ApiProperty({ example: 'IIB', description: 'Estadio del tumor', required: false })
    @IsString()
    @IsOptional()
    stage?: string;

    @ApiProperty({ example: 'Quimioterapia AC + Radioterapia + Hormonoterapia', description: 'Protocolo de tratamiento', required: false })
    @IsString()
    @IsOptional()
    treatmentProtocol?: string;

    @ApiProperty({ example: 'Respuesta completa al tratamiento', description: 'Observaciones clínicas', required: false })
    @IsString()
    @IsOptional()
    observations?: string;
}

/**
 * DTO de respuesta para historia clínica
 */
export class ClinicalRecordResponseDto {
    @ApiProperty({ example: 'uuid-record-123', description: 'ID de la historia clínica' })
    id: string;

    @ApiProperty({ type: () => PatientResponseDto, description: 'Información del paciente' })
    patient: PatientResponseDto;

    @ApiProperty({ type: () => TumorTypeResponseDto, description: 'Información del tipo de tumor' })
    tumorType: TumorTypeResponseDto;

    @ApiProperty({ example: '2024-01-15', description: 'Fecha de diagnóstico' })
    diagnosisDate: Date;

    @ApiProperty({ example: 'IIA', description: 'Estadio del tumor' })
    stage: string;

    @ApiProperty({ example: 'Quimioterapia AC + Radioterapia', description: 'Protocolo de tratamiento' })
    treatmentProtocol: string;

    @ApiProperty({ example: 'Paciente respondiendo bien', description: 'Observaciones clínicas' })
    observations: string;

    @ApiProperty({ example: '2024-11-13', description: 'Fecha de creación del registro' })
    createdAt: Date;

    @ApiProperty({ example: '2024-11-13', description: 'Fecha de última actualización' })
    updatedAt: Date;
}