import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from 'typeorm';
import { ClinicalRecord } from '../clinical-records/clinical-record.entity';

/**
 * Entidad que representa un tipo de tumor oncológico
 */
@Entity('tumor_types')
export class TumorType {
    @PrimaryGeneratedColumn('increment')
    id: number;

    @Column({ type: 'varchar', length: 150, unique: true, nullable: false })
    name: string;

    @Column({ type: 'varchar', length: 100, nullable: false })
    systemAffected: string;

    @Column({ type: 'varchar', length: 500, nullable: true })
    description: string;

    @OneToMany(() => ClinicalRecord, (record) => record.tumorType)
    clinicalRecords: ClinicalRecord[];
}