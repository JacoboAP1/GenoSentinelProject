import {
  Controller,
  Get,
  Post,
  Put,
  Delete,
  Body,
  Param,
  Query,
  HttpCode,
  HttpStatus,
} from '@nestjs/common';
import {
  ApiTags,
  ApiOperation,
  ApiResponse,
  ApiParam,
  ApiQuery,
} from '@nestjs/swagger';
import { TumorTypesService } from './tumor-types.service';
import {
  CreateTumorTypeDto,
  UpdateTumorTypeDto,
} from './dto/tumor-type-in.dto';
import { TumorTypeDtoOut } from './dto/tumor-type-out.dto';

/**
 * Controlador para la gestión del catálogo de tipos de tumor
 */
@ApiTags('Tipos de Tumor')
@Controller('tumor-types')
export class TumorTypesController {
  constructor(private readonly tumorTypesService: TumorTypesService) {}

  @Post()
  @ApiOperation({
    summary: 'Crear nuevo tipo de tumor',
    description: 'Registra un nuevo tipo de tumor en el catálogo oncológico',
  })
  @ApiResponse({
    status: 201,
    description: 'Tipo de tumor creado exitosamente',
    type: TumorTypeDtoOut,
  })
  @ApiResponse({ status: 400, description: 'Datos inválidos o faltantes' })
  @ApiResponse({
    status: 409,
    description: 'Ya existe un tipo de tumor con ese nombre',
  })
  async create(
    @Body() createTumorTypeDto: CreateTumorTypeDto,
  ): Promise<TumorTypeDtoOut> {
    return this.tumorTypesService.create(createTumorTypeDto);
  }

  @Get()
  @ApiOperation({
    summary: 'Obtener todos los tipos de tumor',
    description: 'Retorna el catálogo completo de tipos de tumor registrados',
  })
  @ApiResponse({
    status: 200,
    description: 'Catálogo obtenido exitosamente',
    type: [TumorTypeDtoOut],
  })
  async findAll(): Promise<TumorTypeDtoOut[]> {
    return this.tumorTypesService.findAll();
  }

  @Get('search')
  @ApiOperation({
    summary: 'Buscar tipos de tumor por sistema',
    description:
      'Busca tipos de tumor que afecten un sistema específico del cuerpo',
  })
  @ApiQuery({
    name: 'system',
    description: 'Sistema corporal a buscar',
    example: 'Respiratorio',
  })
  @ApiResponse({
    status: 200,
    description: 'Búsqueda completada exitosamente',
    type: [TumorTypeDtoOut],
  })
  async searchBySystem(
    @Query('system') system: string,
  ): Promise<TumorTypeDtoOut[]> {
    return this.tumorTypesService.searchBySystem(system);
  }

  @Get(':id')
  @ApiOperation({
    summary: 'Obtener tipo de tumor por ID',
    description: 'Retorna la información de un tipo de tumor específico',
  })
  @ApiParam({ name: 'id', description: 'ID del tipo de tumor', example: 1 })
  @ApiResponse({
    status: 200,
    description: 'Tipo de tumor encontrado',
    type: TumorTypeDtoOut,
  })
  @ApiResponse({ status: 404, description: 'Tipo de tumor no encontrado' })
  async findOne(@Param('id') id: number): Promise<TumorTypeDtoOut> {
    return this.tumorTypesService.findOne(id);
  }

  @Put(':id')
  @ApiOperation({
    summary: 'Actualizar tipo de tumor',
    description: 'Actualiza la información de un tipo de tumor existente',
  })
  @ApiParam({ name: 'id', description: 'ID del tipo de tumor', example: 1 })
  @ApiResponse({
    status: 200,
    description: 'Tipo de tumor actualizado exitosamente',
    type: TumorTypeDtoOut,
  })
  @ApiResponse({ status: 404, description: 'Tipo de tumor no encontrado' })
  @ApiResponse({ status: 400, description: 'Datos inválidos' })
  @ApiResponse({
    status: 409,
    description: 'Ya existe un tipo de tumor con ese nombre',
  })
  async update(
    @Param('id') id: number,
    @Body() updateTumorTypeDto: UpdateTumorTypeDto,
  ): Promise<TumorTypeDtoOut> {
    return this.tumorTypesService.update(id, updateTumorTypeDto);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  @ApiOperation({
    summary: 'Eliminar tipo de tumor',
    description: 'Elimina permanentemente un tipo de tumor del catálogo',
  })
  @ApiParam({ name: 'id', description: 'ID del tipo de tumor', example: 1 })
  @ApiResponse({
    status: 204,
    description: 'Tipo de tumor eliminado exitosamente',
  })
  @ApiResponse({ status: 404, description: 'Tipo de tumor no encontrado' })
  async remove(@Param('id') id: number): Promise<void> {
    return this.tumorTypesService.remove(id);
  }
}
