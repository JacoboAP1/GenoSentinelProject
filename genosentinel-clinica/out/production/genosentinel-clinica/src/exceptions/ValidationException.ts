import { HttpException, HttpStatus } from '@nestjs/common';

export class ValidationException extends HttpException {
  constructor(message: string, errors?: Record<string, any>) {
    super(
      {
        statusCode: HttpStatus.BAD_REQUEST,
        message: message || 'Error de validación',
        error: 'Validation Error',
        errors: errors || {},
      },
      HttpStatus.BAD_REQUEST,
    );
  }
}
