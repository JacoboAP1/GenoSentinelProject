import { HttpException, HttpStatus } from '@nestjs/common';

export class InternalServerException extends HttpException {
  constructor(message: string, originalError?: any) {
    super(
      {
        statusCode: HttpStatus.INTERNAL_SERVER_ERROR,
        message: message || 'Error interno del servidor',
        error: 'Internal Server Error',
        details: originalError?.message || undefined,
      },
      HttpStatus.INTERNAL_SERVER_ERROR,
    );
  }
}
