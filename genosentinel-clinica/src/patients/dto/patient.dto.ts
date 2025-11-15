import { ApiProperty } from '@nestjs/swagger';
import { IsString, IsNotEmpty, IsDateString, IsOptional, IsIn } from 'class-validator';

/**
 * DTO para crear un paciente
 */
export class CreatePatientDto {
    @ApiProperty({ example: 'María', description: 'Nombre del paciente' })
    @IsString()
    @IsNotEmpty({ message: 'El nombre es obligatorio' })
    firstName: string;

    @ApiProperty({ example: 'González', description: 'Apellido del paciente' })
    @IsString()
    @IsNotEmpty({ message: 'El apellido es obligatorio' })
    lastName: string;

    @ApiProperty({ example: '1985-03-15', description: 'Fecha de nacimiento (YYYY-MM-DD)' })
    @IsDateString({}, { message: 'Formato de fecha inválido' })
    @IsNotEmpty({ message: 'La fecha de nacimiento es obligatoria' })
    birthDate: string;

    @ApiProperty({ example: 'Femenino', description: 'Género del paciente', enum: ['Masculino', 'Femenino', 'Otro'] })
    @IsString()
    @IsIn(['Masculino', 'Femenino', 'Otro'], { message: 'Género no válido' })
    @IsNotEmpty({ message: 'El género es obligatorio' })
    gender: string;

    @ApiProperty({ example: 'Activo', description: 'Estado del paciente', enum: ['Activo', 'Seguimiento', 'Inactivo'], required: false })
    @IsString()
    @IsIn(['Activo', 'Seguimiento', 'Inactivo'], { message: 'Estado no válido' })
    @IsOptional()
    status?: string;
}

/**
 * DTO para actualizar un paciente
 */
export class UpdatePatientDto {
    @ApiProperty({ example: 'María Isabel', description: 'Nombre del paciente', required: false })
    @IsString()
    @IsOptional()
    firstName?: string;

    @ApiProperty({ example: 'González Ramírez', description: 'Apellido del paciente', required: false })
    @IsString()
    @IsOptional()
    lastName?: string;

    @ApiProperty({ example: '1985-03-15', description: 'Fecha de nacimiento (YYYY-MM-DD)', required: false })
    @IsDateString({}, { message: 'Formato de fecha inválido' })
    @IsOptional()
    birthDate?: string;

    @ApiProperty({ example: 'Femenino', description: 'Género del paciente', enum: ['Masculino', 'Femenino', 'Otro'], required: false })
    @IsString()
    @IsIn(['Masculino', 'Femenino', 'Otro'], { message: 'Género no válido' })
    @IsOptional()
    gender?: string;

    @ApiProperty({ example: 'Seguimiento', description: 'Estado del paciente', enum: ['Activo', 'Seguimiento', 'Inactivo'], required: false })
    @IsString()
    @IsIn(['Activo', 'Seguimiento', 'Inactivo'], { message: 'Estado no válido' })
    @IsOptional()
    status?: string;
}

/**
 * DTO de respuesta para paciente
 */
export class PatientResponseDto {
    @ApiProperty({ example: 'uuid-123-456', description: 'ID único del paciente' })
    id: string;

    @ApiProperty({ example: 'María', description: 'Nombre del paciente' })
    firstName: string;

    @ApiProperty({ example: 'González', description: 'Apellido del paciente' })
    lastName: string;

    @ApiProperty({ example: '1985-03-15', description: 'Fecha de nacimiento' })
    birthDate: Date;

    @ApiProperty({ example: 'Femenino', description: 'Género del paciente' })
    gender: string;

    @ApiProperty({ example: 'Activo', description: 'Estado del paciente' })
    status: string;

    @ApiProperty({ example: 'María González', description: 'Nombre completo' })
    fullName: string;

    @ApiProperty({ example: 39, description: 'Edad calculada' })
    age: number;

    @ApiProperty({ example: '2024-11-13', description: 'Fecha de creación' })
    createdAt: Date;

    @ApiProperty({ example: '2024-11-13', description: 'Fecha de actualización' })
    updatedAt: Date;
}