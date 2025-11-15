import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn, OneToMany } from 'typeorm';
import { ClinicalRecord } from '../clinical-records/clinical-record.entity';

/**
 * Entidad que representa un paciente oncológico
 */
@Entity('patients')
export class Patient {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column({ type: 'varchar', length: 100, nullable: false })
    firstName: string;

    @Column({ type: 'varchar', length: 100, nullable: false })
    lastName: string;

    @Column({ type: 'date', nullable: false })
    birthDate: Date;

    @Column({ type: 'varchar', length: 20, nullable: false })
    gender: string; // 'Masculino', 'Femenino', 'Otro'

    @Column({ type: 'varchar', length: 50, default: 'Activo' })
    status: string; // 'Activo', 'Seguimiento', 'Inactivo'

    @CreateDateColumn({ type: 'timestamp' })
    createdAt: Date;

    @UpdateDateColumn({ type: 'timestamp' })
    updatedAt: Date;

    @OneToMany(() => ClinicalRecord, (record) => record.patient)
    clinicalRecords: ClinicalRecord[];
}