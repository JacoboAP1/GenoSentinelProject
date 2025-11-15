import { Controller, Get, Post, Put, Patch, Delete, Body, Param, Query, HttpCode, HttpStatus } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse, ApiParam, ApiQuery } from '@nestjs/swagger';
import { PatientsService } from './patients.service';
import { CreatePatientDto, UpdatePatientDto, PatientResponseDto } from './dto/patient.dto';

/**
 * Controlador para la gestión de pacientes oncológicos
 */
@ApiTags('Pacientes')
@Controller('patients')
export class PatientsController {
    constructor(private readonly patientsService: PatientsService) {}

    @Post()
    @ApiOperation({ summary: 'Crear nuevo paciente', description: 'Registra un nuevo paciente oncológico en el sistema' })
    @ApiResponse({ status: 201, description: 'Paciente creado exitosamente', type: PatientResponseDto })
    @ApiResponse({ status: 400, description: 'Datos inválidos o faltantes' })
    async create(@Body() createPatientDto: CreatePatientDto): Promise<PatientResponseDto> {
        return this.patientsService.create(createPatientDto);
    }

    @Get()
    @ApiOperation({ summary: 'Obtener todos los pacientes', description: 'Retorna la lista completa de pacientes registrados' })
    @ApiResponse({ status: 200, description: 'Lista de pacientes obtenida exitosamente', type: [PatientResponseDto] })
    async findAll(): Promise<PatientResponseDto[]> {
        return this.patientsService.findAll();
    }

    @Get('search')
    @ApiOperation({ summary: 'Buscar pacientes por nombre', description: 'Busca pacientes cuyo nombre o apellido contenga el texto especificado' })
    @ApiQuery({ name: 'name', description: 'Nombre o apellido a buscar', example: 'María' })
    @ApiResponse({ status: 200, description: 'Búsqueda completada exitosamente', type: [PatientResponseDto] })
    async searchByName(@Query('name') name: string): Promise<PatientResponseDto[]> {
        return this.patientsService.searchByName(name);
    }

    @Get('status/:status')
    @ApiOperation({ summary: 'Obtener pacientes por estado', description: 'Retorna pacientes filtrados por su estado' })
    @ApiParam({ name: 'status', description: 'Estado del paciente', example: 'Activo', enum: ['Activo', 'Seguimiento', 'Inactivo'] })
    @ApiResponse({ status: 200, description: 'Lista de pacientes obtenida exitosamente', type: [PatientResponseDto] })
    async findByStatus(@Param('status') status: string): Promise<PatientResponseDto[]> {
        return this.patientsService.findByStatus(status);
    }

    @Get(':id')
    @ApiOperation({ summary: 'Obtener paciente por ID', description: 'Retorna la información de un paciente específico' })
    @ApiParam({ name: 'id', description: 'ID único del paciente (UUID)', example: 'uuid-123-456' })
    @ApiResponse({ status: 200, description: 'Paciente encontrado', type: PatientResponseDto })
    @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
    async findOne(@Param('id') id: string): Promise<PatientResponseDto> {
        return this.patientsService.findOne(id);
    }

    @Put(':id')
    @ApiOperation({ summary: 'Actualizar paciente', description: 'Actualiza la información de un paciente existente' })
    @ApiParam({ name: 'id', description: 'ID único del paciente (UUID)', example: 'uuid-123-456' })
    @ApiResponse({ status: 200, description: 'Paciente actualizado exitosamente', type: PatientResponseDto })
    @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
    @ApiResponse({ status: 400, description: 'Datos inválidos' })
    async update(@Param('id') id: string, @Body() updatePatientDto: UpdatePatientDto): Promise<PatientResponseDto> {
        return this.patientsService.update(id, updatePatientDto);
    }

    @Patch(':id/deactivate')
    @ApiOperation({ summary: 'Desactivar paciente', description: 'Cambia el estado del paciente a Inactivo' })
    @ApiParam({ name: 'id', description: 'ID único del paciente (UUID)', example: 'uuid-123-456' })
    @ApiResponse({ status: 200, description: 'Paciente desactivado exitosamente', type: PatientResponseDto })
    @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
    async deactivate(@Param('id') id: string): Promise<PatientResponseDto> {
        return this.patientsService.deactivate(id);
    }

    @Delete(':id')
    @HttpCode(HttpStatus.NO_CONTENT)
    @ApiOperation({ summary: 'Eliminar paciente', description: 'Elimina permanentemente un paciente del sistema' })
    @ApiParam({ name: 'id', description: 'ID único del paciente (UUID)', example: 'uuid-123-456' })
    @ApiResponse({ status: 204, description: 'Paciente eliminado exitosamente' })
    @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
    async remove(@Param('id') id: string): Promise<void> {
        return this.patientsService.remove(id);
    }
}