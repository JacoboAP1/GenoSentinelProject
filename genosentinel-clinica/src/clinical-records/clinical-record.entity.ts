import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn, CreateDateColumn, UpdateDateColumn } from 'typeorm';
import { Patient } from '../patients/patient.entity';
import { TumorType } from '../tumor-types/tumor-type.entity';

/**
 * Entidad que representa una historia clínica o diagnóstico oncológico
 */
@Entity('clinical_records')
export class ClinicalRecord {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @ManyToOne(() => Patient, (patient) => patient.clinicalRecords, { eager: true })
    @JoinColumn({ name: 'patient_id' })
    patient: Patient;

    @ManyToOne(() => TumorType, (tumorType) => tumorType.clinicalRecords, { eager: true })
    @JoinColumn({ name: 'tumor_type_id' })
    tumorType: TumorType;

    @Column({ type: 'date', nullable: false })
    diagnosisDate: Date;

    @Column({ type: 'varchar', length: 20, nullable: false })
    stage: string; // 'I', 'IIA', 'IIIB', 'IV'

    @Column({ type: 'varchar', length: 500, nullable: true })
    treatmentProtocol: string;

    @Column({ type: 'text', nullable: true })
    observations: string;

    @CreateDateColumn({ type: 'timestamp' })
    createdAt: Date;

    @UpdateDateColumn({ type: 'timestamp' })
    updatedAt: Date;
}