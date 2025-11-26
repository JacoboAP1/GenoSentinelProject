import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from 'typeorm';
import { Patient } from '../patients/patient.entity';
import { TumorType } from '../tumor-types/tumor-type.entity';

/**
 * Entidad que representa una historia clínica o diagnóstico oncológico
 */
@Entity('ClinicalRecord')
export class ClinicalRecord {
    @PrimaryGeneratedColumn('increment')
    id: number;

    @ManyToOne(() => Patient, (patient) => patient.clinicalRecords, { eager: true })
    @JoinColumn({ name: 'patient_id' })
    patient: Patient;

    @ManyToOne(() => TumorType, (tumorType) => tumorType.clinicalRecords, { eager: true })
    @JoinColumn({ name: 'tumor_type_id' })
    tumorType: TumorType;

    @Column({ type: 'date', nullable: false, name: 'diagnosis_date' })
    diagnosisDate: Date;

    @Column({ type: 'varchar', length: 20, nullable: true, name: 'stage' })
    stage: string; // 'I', 'IIA', 'IIIB', 'IV'

    @Column({ type: 'text', nullable: true, name: 'treatment_protocol' })
    treatmentProtocol: string;
}