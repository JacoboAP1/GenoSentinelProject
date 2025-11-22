import { HttpException, HttpStatus } from '@nestjs/common';

export class DatabaseException extends HttpException {
  constructor(message: string, originalError?: any) {
    super(
      {
        statusCode: HttpStatus.INTERNAL_SERVER_ERROR,
        message: message || 'Error en la base de datos',
        error: 'Database Error',
        details: originalError?.message || undefined,
      },
      HttpStatus.INTERNAL_SERVER_ERROR,
    );
  }
}
