import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from 'typeorm';
import { ClinicalRecord } from '../clinical-records/clinical-record.entity';

/**
 * Entidad que representa un tipo de tumor oncológico
 */
@Entity('TumorType')
export class TumorType {
  @PrimaryGeneratedColumn('increment')
  id: number;

  @Column({ type: 'varchar', length: 100, nullable: false })
  name: string;

  @Column({
    type: 'varchar',
    length: 100,
    nullable: true,
    name: 'system_affected',
  })
  systemAffected: string;

  @OneToMany(() => ClinicalRecord, (record) => record.tumorType)
  clinicalRecords: ClinicalRecord[];
}
