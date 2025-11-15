import { Injectable, NotFoundException, BadRequestException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Patient } from './patient.entity';
import { CreatePatientDto, UpdatePatientDto, PatientResponseDto } from './dto/patient.dto';

/**
 * Servicio para la gestión de pacientes oncológicos
 */
@Injectable()
export class PatientsService {
    constructor(
        @InjectRepository(Patient)
        private readonly patientRepository: Repository<Patient>,
    ) {}

    /**
     * Crea un nuevo paciente
     */
    async create(createPatientDto: CreatePatientDto): Promise<PatientResponseDto> {
        // Validar fecha de nacimiento
        const birthDate = new Date(createPatientDto.birthDate);
        if (birthDate > new Date()) {
            throw new BadRequestException('La fecha de nacimiento no puede ser futura');
        }

        const patient = this.patientRepository.create({
            ...createPatientDto,
            birthDate,
            status: createPatientDto.status || 'Activo',
        });

        const saved = await this.patientRepository.save(patient);
        return this.mapToResponse(saved);
    }

    /**
     * Obtiene todos los pacientes
     */
    async findAll(): Promise<PatientResponseDto[]> {
        const patients = await this.patientRepository.find();
        return patients.map((patient) => this.mapToResponse(patient));
    }

    /**
     * Obtiene un paciente por ID
     */
    async findOne(id: string): Promise<PatientResponseDto> {
        const patient = await this.patientRepository.findOne({ where: { id } });
        if (!patient) {
            throw new NotFoundException(`Paciente con ID ${id} no encontrado`);
        }
        return this.mapToResponse(patient);
    }

    /**
     * Obtiene pacientes por estado
     */
    async findByStatus(status: string): Promise<PatientResponseDto[]> {
        const patients = await this.patientRepository.find({ where: { status } });
        return patients.map((patient) => this.mapToResponse(patient));
    }

    /**
     * Busca pacientes por nombre o apellido
     */
    async searchByName(name: string): Promise<PatientResponseDto[]> {
        const patients = await this.patientRepository
            .createQueryBuilder('patient')
            .where('patient.firstName LIKE :name', { name: `%${name}%` })
            .orWhere('patient.lastName LIKE :name', { name: `%${name}%` })
            .getMany();

        return patients.map((patient) => this.mapToResponse(patient));
    }

    /**
     * Actualiza un paciente
     */
    async update(id: string, updatePatientDto: UpdatePatientDto): Promise<PatientResponseDto> {
        const patient = await this.patientRepository.findOne({ where: { id } });
        if (!patient) {
            throw new NotFoundException(`Paciente con ID ${id} no encontrado`);
        }

        // Validar fecha de nacimiento si se proporciona
        if (updatePatientDto.birthDate) {
            const birthDate = new Date(updatePatientDto.birthDate);
            if (birthDate > new Date()) {
                throw new BadRequestException('La fecha de nacimiento no puede ser futura');
            }
            updatePatientDto.birthDate = birthDate.toISOString().split('T')[0];
        }

        Object.assign(patient, updatePatientDto);
        const updated = await this.patientRepository.save(patient);
        return this.mapToResponse(updated);
    }

    /**
     * Desactiva un paciente (cambia estado a Inactivo)
     */
    async deactivate(id: string): Promise<PatientResponseDto> {
        const patient = await this.patientRepository.findOne({ where: { id } });
        if (!patient) {
            throw new NotFoundException(`Paciente con ID ${id} no encontrado`);
        }

        patient.status = 'Inactivo';
        const updated = await this.patientRepository.save(patient);
        return this.mapToResponse(updated);
    }

    /**
     * Elimina un paciente
     */
    async remove(id: string): Promise<void> {
        const result = await this.patientRepository.delete(id);
        if (result.affected === 0) {
            throw new NotFoundException(`Paciente con ID ${id} no encontrado`);
        }
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private mapToResponse(patient: Patient): PatientResponseDto {
        const birthDate = new Date(patient.birthDate);
        const today = new Date();
        const age = today.getFullYear() - birthDate.getFullYear() -
            (today.getMonth() < birthDate.getMonth() ||
            (today.getMonth() === birthDate.getMonth() && today.getDate() < birthDate.getDate()) ? 1 : 0);

        return {
            id: patient.id,
            firstName: patient.firstName,
            lastName: patient.lastName,
            birthDate: patient.birthDate,
            gender: patient.gender,
            status: patient.status,
            fullName: `${patient.firstName} ${patient.lastName}`,
            age,
            createdAt: patient.createdAt,
            updatedAt: patient.updatedAt,
        };
    }
}