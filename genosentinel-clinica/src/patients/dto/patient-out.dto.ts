import { ApiProperty } from '@nestjs/swagger';

/**
 * DTO OUT de respuesta para paciente
 */
export class PatientDtoOut {
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

    @ApiProperty({ example: '2024-11-14T21:00:00.000Z', description: 'Fecha de creación' })
    createdAt: Date;

    @ApiProperty({ example: '2024-11-14T21:00:00.000Z', description: 'Fecha de actualización' })
    updatedAt: Date;
}