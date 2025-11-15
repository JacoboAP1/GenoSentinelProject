import { ApiProperty } from '@nestjs/swagger';

/**
 * DTO OUT de respuesta para tipo de tumor
 */
export class TumorTypeDtoOut {
    @ApiProperty({ example: 1, description: 'ID del tipo de tumor' })
    id: number;

    @ApiProperty({ example: 'Cáncer de Mama', description: 'Nombre del tipo de tumor' })
    name: string;

    @ApiProperty({ example: 'Glándulas Mamarias', description: 'Sistema corporal afectado' })
    systemAffected: string;

    @ApiProperty({ example: 'Tumor maligno del tejido mamario', description: 'Descripción del tumor' })
    description: string;
} 