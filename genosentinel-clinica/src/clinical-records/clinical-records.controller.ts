import {
  Controller,
  Get,
  Post,
  Put,
  Delete,
  Body,
  Param,
  HttpCode,
  HttpStatus,
} from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse, ApiParam } from '@nestjs/swagger';
import { ClinicalRecordsService } from './clinical-records.service';
import {
  CreateClinicalRecordDtoIn,
  UpdateClinicalRecordDtoIn,
} from './dto/clinical-record-in.dto';
import { ClinicalRecordDtoOut } from './dto/clinical-record-out.dto';

/**
 * Controlador para la gestión de historias clínicas
 */
@ApiTags('Historias Clínicas')
@Controller('clinical-records')
export class ClinicalRecordsController {
  constructor(
    private readonly clinicalRecordsService: ClinicalRecordsService,
  ) {}

  @Post()
  @ApiOperation({
    summary: 'Crear nueva historia clínica',
    description:
      'Registra un nuevo diagnóstico oncológico con su tratamiento asociado',
  })
  @ApiResponse({
    status: 201,
    description: 'Historia clínica creada exitosamente',
    type: ClinicalRecordDtoOut,
  })
  @ApiResponse({ status: 400, description: 'Datos inválidos o faltantes' })
  @ApiResponse({
    status: 404,
    description: 'Paciente o tipo de tumor no encontrado',
  })
  async create(
    @Body() createDto: CreateClinicalRecordDtoIn,
  ): Promise<ClinicalRecordDtoOut> {
    return this.clinicalRecordsService.create(createDto);
  }

  @Get()
  @ApiOperation({
    summary: 'Obtener todas las historias clínicas',
    description: 'Retorna la lista completa de historias clínicas registradas',
  })
  @ApiResponse({
    status: 200,
    description: 'Lista de historias clínicas obtenida exitosamente',
    type: [ClinicalRecordDtoOut],
  })
  async findAll(): Promise<ClinicalRecordDtoOut[]> {
    return this.clinicalRecordsService.findAll();
  }

  @Get(':id')
  @ApiOperation({
    summary: 'Obtener historia clínica por ID',
    description: 'Retorna la información de una historia clínica específica',
  })
  @ApiParam({
    name: 'id',
    description: 'ID de la historia clínica',
    example: 1,
  })
  @ApiResponse({
    status: 200,
    description: 'Historia clínica encontrada',
    type: ClinicalRecordDtoOut,
  })
  @ApiResponse({ status: 404, description: 'Historia clínica no encontrada' })
  async findOne(@Param('id') id: number): Promise<ClinicalRecordDtoOut> {
    return this.clinicalRecordsService.findOne(id);
  }

  @Get('patient/:patientId')
  @ApiOperation({
    summary: 'Obtener historias clínicas por paciente',
    description:
      'Retorna todas las historias clínicas de un paciente específico',
  })
  @ApiParam({ name: 'patientId', description: 'ID del paciente', example: 1 })
  @ApiResponse({
    status: 200,
    description: 'Historias clínicas obtenidas exitosamente',
    type: [ClinicalRecordDtoOut],
  })
  async findByPatient(
    @Param('patientId') patientId: number,
  ): Promise<ClinicalRecordDtoOut[]> {
    return this.clinicalRecordsService.findByPatient(patientId);
  }

  @Get('tumor-type/:tumorTypeId')
  @ApiOperation({
    summary: 'Obtener historias clínicas por tipo de tumor',
    description:
      'Retorna todas las historias clínicas asociadas a un tipo de tumor específico',
  })
  @ApiParam({
    name: 'tumorTypeId',
    description: 'ID del tipo de tumor',
    example: 1,
  })
  @ApiResponse({
    status: 200,
    description: 'Historias clínicas obtenidas exitosamente',
    type: [ClinicalRecordDtoOut],
  })
  async findByTumorType(
    @Param('tumorTypeId') tumorTypeId: number,
  ): Promise<ClinicalRecordDtoOut[]> {
    return this.clinicalRecordsService.findByTumorType(tumorTypeId);
  }

  @Put(':id')
  @ApiOperation({
    summary: 'Actualizar historia clínica',
    description: 'Actualiza la información de una historia clínica existente',
  })
  @ApiParam({
    name: 'id',
    description: 'ID de la historia clínica',
    example: 1,
  })
  @ApiResponse({
    status: 200,
    description: 'Historia clínica actualizada exitosamente',
    type: ClinicalRecordDtoOut,
  })
  @ApiResponse({
    status: 404,
    description: 'Historia clínica, paciente o tipo de tumor no encontrado',
  })
  @ApiResponse({ status: 400, description: 'Datos inválidos' })
  async update(
    @Param('id') id: number,
    @Body() updateDto: UpdateClinicalRecordDtoIn,
  ): Promise<ClinicalRecordDtoOut> {
    return this.clinicalRecordsService.update(id, updateDto);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  @ApiOperation({
    summary: 'Eliminar historia clínica',
    description: 'Elimina permanentemente una historia clínica del sistema',
  })
  @ApiParam({
    name: 'id',
    description: 'ID de la historia clínica',
    example: 1,
  })
  @ApiResponse({
    status: 204,
    description: 'Historia clínica eliminada exitosamente',
  })
  @ApiResponse({ status: 404, description: 'Historia clínica no encontrada' })
  async remove(@Param('id') id: number): Promise<void> {
    return this.clinicalRecordsService.remove(id);
  }
}
