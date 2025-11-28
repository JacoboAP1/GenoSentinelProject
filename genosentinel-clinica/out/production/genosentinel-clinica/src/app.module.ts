import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule } from '@nestjs/config';
import { PatientsModule } from './patients/patients.module';
import { TumorTypesModule } from './tumor-types/tumor-types.module';
import { ClinicalRecordsModule } from './clinical-records/clinical-records.module';

@Module({
    imports: [
        // Configuración de variables de entorno
        ConfigModule.forRoot({
            isGlobal: true,
        }),

        // Configuración de TypeORM con MySQL
        TypeOrmModule.forRoot({
            type: 'mysql',
            host: process.env.DB_HOST || 'localhost',
            port: parseInt(process.env.DB_PORT) || 3306,
            username: process.env.DB_USERNAME || 'root',
            password: process.env.DB_PASSWORD || 'admin123',
            database: process.env.DB_DATABASE || 'genosentinel',
            entities: [__dirname + '/**/*.entity{.ts,.js}'],
            synchronize: false, // Solo en desarrollo - en producción usar migraciones
            logging: false,
        }),

        // Módulos de funcionalidades
        PatientsModule,
        TumorTypesModule,
        ClinicalRecordsModule,
    ],
})
export class AppModule {}