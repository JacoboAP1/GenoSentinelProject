import { Injectable, NotFoundException, ConflictException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { TumorType } from './tumor-type.entity';
import { CreateTumorTypeDto, UpdateTumorTypeDto } from './dto/tumor-type-in.dto';
import { TumorTypeDtoOut } from './dto/tumor-type-out.dto';

/**
 * Servicio para la gestión del catálogo de tipos de tumor
 */
@Injectable()
export class TumorTypesService {
    constructor(
        @InjectRepository(TumorType)
        private readonly tumorTypeRepository: Repository<TumorType>,
    ) {}

    /**
     * Crea un nuevo tipo de tumor
     */
    async create(createTumorTypeDto: CreateTumorTypeDto): Promise<TumorTypeDtoOut> {
        // Verificar si ya existe un tumor con el mismo nombre
        const existing = await this.tumorTypeRepository.findOne({
            where: { name: createTumorTypeDto.name },
        });

        if (existing) {
            throw new ConflictException(`Ya existe un tipo de tumor con el nombre "${createTumorTypeDto.name}"`);
        }

        const tumorType = this.tumorTypeRepository.create(createTumorTypeDto);
        const saved = await this.tumorTypeRepository.save(tumorType);
        return this.mapToResponse(saved);
    }

    /**
     * Obtiene todos los tipos de tumor
     */
    async findAll(): Promise<TumorTypeDtoOut[]> {
        const tumorTypes = await this.tumorTypeRepository.find();
        return tumorTypes.map((type) => this.mapToResponse(type));
    }

    /**
     * Obtiene un tipo de tumor por ID
     */
    async findOne(id: number): Promise<TumorTypeDtoOut> {
        const tumorType = await this.tumorTypeRepository.findOne({ where: { id } });
        if (!tumorType) {
            throw new NotFoundException(`Tipo de tumor con ID ${id} no encontrado`);
        }
        return this.mapToResponse(tumorType);
    }

    /**
     * Busca tipos de tumor por sistema afectado
     */
    async searchBySystem(system: string): Promise<TumorTypeDtoOut[]> {
        const tumorTypes = await this.tumorTypeRepository
            .createQueryBuilder('tumorType')
            .where('tumorType.systemAffected LIKE :system', { system: `%${system}%` })
            .getMany();

        return tumorTypes.map((type) => this.mapToResponse(type));
    }

    /**
     * Actualiza un tipo de tumor
     */
    async update(id: number, updateTumorTypeDto: UpdateTumorTypeDto): Promise<TumorTypeDtoOut> {
        const tumorType = await this.tumorTypeRepository.findOne({ where: { id } });
        if (!tumorType) {
            throw new NotFoundException(`Tipo de tumor con ID ${id} no encontrado`);
        }

        // Verificar duplicados si se actualiza el nombre
        if (updateTumorTypeDto.name && updateTumorTypeDto.name !== tumorType.name) {
            const existing = await this.tumorTypeRepository.findOne({
                where: { name: updateTumorTypeDto.name },
            });
            if (existing) {
                throw new ConflictException(`Ya existe un tipo de tumor con el nombre "${updateTumorTypeDto.name}"`);
            }
        }

        Object.assign(tumorType, updateTumorTypeDto);
        const updated = await this.tumorTypeRepository.save(tumorType);
        return this.mapToResponse(updated);
    }

    /**
     * Elimina un tipo de tumor
     */
    async remove(id: number): Promise<void> {
        const result = await this.tumorTypeRepository.delete(id);
        if (result.affected === 0) {
            throw new NotFoundException(`Tipo de tumor con ID ${id} no encontrado`);
        }
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private mapToResponse(tumorType: TumorType): TumorTypeDtoOut {
        return {
            id: tumorType.id,
            name: tumorType.name,
            systemAffected: tumorType.systemAffected,
        };
    }
}