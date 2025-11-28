import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { TumorType } from './tumor-type.entity';
import { TumorTypesController } from './tumor-types.controller';
import { TumorTypesService } from './tumor-types.service';

@Module({
    imports: [TypeOrmModule.forFeature([TumorType])],
    controllers: [TumorTypesController],
    providers: [TumorTypesService],
    exports: [TumorTypesService], // Exportar para uso en otros módulos
})
export class TumorTypesModule {}