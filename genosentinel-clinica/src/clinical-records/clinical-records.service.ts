import { Injectable, NotFoundException, BadRequestException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ClinicalRecord } from './clinical-record.entity';
import { Patient } from '../patients/patient.entity';
import { TumorType } from '../tumor-types/tumor-type.entity';
import { CreateClinicalRecordDtoIn, UpdateClinicalRecordDtoIn } from './dto/clinical-record-in.dto';
import { ClinicalRecordDtoOut } from './dto/clinical-record-out.dto';
import { PatientsService } from '../patients/patients.service';
import { TumorTypesService } from '../tumor-types/tumor-types.service';

/**
 * Servicio para la gestión de historias clínicas
 */
@Injectable()
export class ClinicalRecordsService {
    constructor(
        @InjectRepository(ClinicalRecord)
        private readonly clinicalRecordRepository: Repository<ClinicalRecord>,
        @InjectRepository(Patient)
        private readonly patientRepository: Repository<Patient>,
        @InjectRepository(TumorType)
        private readonly tumorTypeRepository: Repository<TumorType>,
        private readonly patientsService: PatientsService,
        private readonly tumorTypesService: TumorTypesService,
    ) {}

    /**
     * Crea una nueva historia clínica
     */
    async create(createDto: CreateClinicalRecordDtoIn): Promise<ClinicalRecordDtoOut> {
        // Validar fecha de diagnóstico
        const diagnosisDate = new Date(createDto.diagnosisDate);
        if (diagnosisDate > new Date()) {
            throw new BadRequestException('La fecha de diagnóstico no puede ser futura');
        }

        // Verificar que el paciente existe
        const patient = await this.patientRepository.findOne({
            where: { id: createDto.patientId },
        });
        if (!patient) {
            throw new NotFoundException(`Paciente con ID ${createDto.patientId} no encontrado`);
        }

        // Verificar que el tipo de tumor existe
        const tumorType = await this.tumorTypeRepository.findOne({
            where: { id: createDto.tumorTypeId },
        });
        if (!tumorType) {
            throw new NotFoundException(`Tipo de tumor con ID ${createDto.tumorTypeId} no encontrado`);
        }

        const clinicalRecord = this.clinicalRecordRepository.create({
            patient,
            tumorType,
            diagnosisDate,
            stage: createDto.stage,
            treatmentProtocol: createDto.treatmentProtocol,
            observations: createDto.observations,
        });

        const saved = await this.clinicalRecordRepository.save(clinicalRecord);
        return this.mapToResponse(saved);
    }

    /**
     * Obtiene todas las historias clínicas
     */
    async findAll(): Promise<ClinicalRecordDtoOut[]> {
        const records = await this.clinicalRecordRepository.find({
            relations: ['patient', 'tumorType'],
        });
        return Promise.all(records.map((record) => this.mapToResponse(record)));
    }

    /**
     * Obtiene una historia clínica por ID
     */
    async findOne(id: string): Promise<ClinicalRecordDtoOut> {
        const record = await this.clinicalRecordRepository.findOne({
            where: { id },
            relations: ['patient', 'tumorType'],
        });

        if (!record) {
            throw new NotFoundException(`Historia clínica con ID ${id} no encontrada`);
        }

        return this.mapToResponse(record);
    }

    /**
     * Obtiene historias clínicas por paciente
     */
    async findByPatient(patientId: string): Promise<ClinicalRecordDtoOut[]> {
        const records = await this.clinicalRecordRepository.find({
            where: { patient: { id: patientId } },
            relations: ['patient', 'tumorType'],
        });

        return Promise.all(records.map((record) => this.mapToResponse(record)));
    }

    /**
     * Obtiene historias clínicas por tipo de tumor
     */
    async findByTumorType(tumorTypeId: number): Promise<ClinicalRecordDtoOut[]> {
        const records = await this.clinicalRecordRepository.find({
            where: { tumorType: { id: tumorTypeId } },
            relations: ['patient', 'tumorType'],
        });

        return Promise.all(records.map((record) => this.mapToResponse(record)));
    }

    /**
     * Actualiza una historia clínica
     */
    async update(id: string, updateDto: UpdateClinicalRecordDtoIn): Promise<ClinicalRecordDtoOut> {
        const record = await this.clinicalRecordRepository.findOne({
            where: { id },
            relations: ['patient', 'tumorType'],
        });

        if (!record) {
            throw new NotFoundException(`Historia clínica con ID ${id} no encontrada`);
        }

        // Validar fecha de diagnóstico si se proporciona
        if (updateDto.diagnosisDate) {
            const diagnosisDate = new Date(updateDto.diagnosisDate);
            if (diagnosisDate > new Date()) {
                throw new BadRequestException('La fecha de diagnóstico no puede ser futura');
            }
            record.diagnosisDate = diagnosisDate;
        }

        // Actualizar paciente si se proporciona
        if (updateDto.patientId) {
            const patient = await this.patientRepository.findOne({
                where: { id: updateDto.patientId },
            });
            if (!patient) {
                throw new NotFoundException(`Paciente con ID ${updateDto.patientId} no encontrado`);
            }
            record.patient = patient;
        }

        // Actualizar tipo de tumor si se proporciona
        if (updateDto.tumorTypeId) {
            const tumorType = await this.tumorTypeRepository.findOne({
                where: { id: updateDto.tumorTypeId },
            });
            if (!tumorType) {
                throw new NotFoundException(`Tipo de tumor con ID ${updateDto.tumorTypeId} no encontrado`);
            }
            record.tumorType = tumorType;
        }

        // Actualizar otros campos
        if (updateDto.stage) record.stage = updateDto.stage;
        if (updateDto.treatmentProtocol !== undefined) record.treatmentProtocol = updateDto.treatmentProtocol;
        if (updateDto.observations !== undefined) record.observations = updateDto.observations;

        const updated = await this.clinicalRecordRepository.save(record);
        return this.mapToResponse(updated);
    }

    /**
     * Elimina una historia clínica
     */
    async remove(id: string): Promise<void> {
        const result = await this.clinicalRecordRepository.delete(id);
        if (result.affected === 0) {
            throw new NotFoundException(`Historia clínica con ID ${id} no encontrada`);
        }
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private async mapToResponse(record: ClinicalRecord): Promise<ClinicalRecordDtoOut> {
        const patient = await this.patientsService.findOne(record.patient.id);
        const tumorType = await this.tumorTypesService.findOne(record.tumorType.id);

        return {
            id: record.id,
            patient,
            tumorType,
            diagnosisDate: record.diagnosisDate,
            stage: record.stage,
            treatmentProtocol: record.treatmentProtocol,
            observations: record.observations,
            createdAt: record.createdAt,
            updatedAt: record.updatedAt,
        };
    }
}