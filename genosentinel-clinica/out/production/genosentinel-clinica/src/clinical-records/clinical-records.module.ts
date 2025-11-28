import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ClinicalRecord } from './clinical-record.entity';
import { Patient } from '../patients/patient.entity';
import { TumorType } from '../tumor-types/tumor-type.entity';
import { ClinicalRecordsController } from './clinical-records.controller';
import { ClinicalRecordsService } from './clinical-records.service';
import { PatientsModule } from '../patients/patients.module';
import { TumorTypesModule } from '../tumor-types/tumor-types.module';

@Module({
    imports: [
        TypeOrmModule.forFeature([ClinicalRecord, Patient, TumorType]),
        PatientsModule, // Importar el módulo de pacientes
        TumorTypesModule, // Importar el módulo de tipos de tumor
    ],
    controllers: [ClinicalRecordsController],
    providers: [ClinicalRecordsService],
    exports: [ClinicalRecordsService],
})
export class ClinicalRecordsModule {}