import { ApiProperty } from '@nestjs/swagger';
import { IsString, IsNotEmpty, IsOptional } from 'class-validator';

/**
 * DTO para crear un tipo de tumor
 */
export class CreateTumorTypeDto {
  @ApiProperty({
    example: 'Cáncer de Mama',
    description: 'Nombre del tipo de tumor',
  })
  @IsString()
  @IsNotEmpty({ message: 'El nombre del tumor es obligatorio' })
  name: string;

  @ApiProperty({
    example: 'Glándulas Mamarias',
    description: 'Sistema corporal afectado',
  })
  @IsString()
  @IsNotEmpty({ message: 'El sistema afectado es obligatorio' })
  systemAffected: string;

  @ApiProperty({
    example: 'Tumor maligno del tejido mamario',
    description: 'Descripción del tumor',
    required: false,
  })
  @IsString()
  @IsOptional()
  description?: string;
}

/**
 * DTO para actualizar un tipo de tumor
 */
export class UpdateTumorTypeDto {
  @ApiProperty({
    example: 'Cáncer de Mama Triple Negativo',
    description: 'Nombre del tipo de tumor',
    required: false,
  })
  @IsString()
  @IsOptional()
  name?: string;

  @ApiProperty({
    example: 'Glándulas Mamarias',
    description: 'Sistema corporal afectado',
    required: false,
  })
  @IsString()
  @IsOptional()
  systemAffected?: string;

  @ApiProperty({
    example: 'Subtipo agresivo de cáncer de mama',
    description: 'Descripción del tumor',
    required: false,
  })
  @IsString()
  @IsOptional()
  description?: string;
}
