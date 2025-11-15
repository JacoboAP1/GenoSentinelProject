import { NestFactory } from '@nestjs/core';
import { ValidationPipe } from '@nestjs/common';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { AppModule } from './app.module';

async function bootstrap() {
    const app = await NestFactory.create(AppModule);

    // Configurar prefijo global de rutas
    app.setGlobalPrefix('genosentinel/clinica');

    // Habilitar CORS
    app.enableCors();

    // Validación global de DTOs
    app.useGlobalPipes(
        new ValidationPipe({
            whitelist: true, // Elimina propiedades no definidas en el DTO
            forbidNonWhitelisted: true, // Lanza error si hay propiedades no permitidas
            transform: true, // Transforma tipos automáticamente
        }),
    );

    // Configuración de Swagger
    const config = new DocumentBuilder()
        .setTitle('GenoSentinel - API Microservicio Clínica')
        .setDescription(
            'API REST para la gestión de información clínica de pacientes oncológicos en el sistema GenoSentinel. ' +
            'Este microservicio maneja pacientes, tipos de tumor e historias clínicas.',
        )
        .setVersion('1.0.0')
        .addTag('Pacientes', 'Endpoints para la gestión de pacientes oncológicos')
        .addTag('Tipos de Tumor', 'Endpoints para la gestión del catálogo de tumores oncológicos')
        .addTag('Historias Clínicas', 'Endpoints para la gestión de diagnósticos y tratamientos oncológicos')
        .setContact(
            'Breaze Labs - Equipo de Desarrollo',
            '',
            'soporte@breaze-labs.com',
        )
        .setLicense('Apache 2.0', 'https://www.apache.org/licenses/LICENSE-2.0.html')
        .addServer('http://localhost:3000/genosentinel/clinica', 'Servidor local de desarrollo')
        .build();

    const document = SwaggerModule.createDocument(app, config);
    SwaggerModule.setup('api-docs', app, document);

    const port = process.env.PORT || 8080;
    await app.listen(port);

    console.log(` Aplicación corriendo en: http://localhost:${port}`);
    console.log(` Documentación Swagger: http://localhost:${port}/api-docs`);
}

bootstrap();