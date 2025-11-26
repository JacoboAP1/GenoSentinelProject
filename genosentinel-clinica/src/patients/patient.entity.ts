import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from 'typeorm';
import { ClinicalRecord } from '../clinical-records/clinical-record.entity';

/**
 * Entidad que representa un paciente oncológico
 */
@Entity('Patient')
export class Patient {
    @PrimaryGeneratedColumn('increment')
    id: number;

    @Column({ type: 'varchar', length: 100, nullable: false, name: 'first_name' })
    firstName: string;

    @Column({ type: 'varchar', length: 100, nullable: false, name: 'last_name' })
    lastName: string;

    @Column({ type: 'date', nullable: false, name: 'birth_date' })
    birthDate: Date;

    @Column({ type: 'enum', enum: ['Masculino', 'Femenino', 'Otro'], nullable: false })
    gender: string; // 'Masculino', 'Femenino', 'Otro'

    @Column({ type: 'enum', enum: ['Activo', 'Seguimiento', 'Inactivo'], default: 'Activo' })
    status: string; // 'Activo', 'Seguimiento', 'Inactivo'

    @OneToMany(() => ClinicalRecord, (record) => record.patient)
    clinicalRecords: ClinicalRecord[];
}