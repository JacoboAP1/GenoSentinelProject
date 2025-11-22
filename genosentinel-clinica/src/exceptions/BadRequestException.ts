import { HttpException, HttpStatus } from '@nestjs/common';

export class BadRequestException extends HttpException {
  constructor(message: string) {
    super(
      {
        statusCode: HttpStatus.BAD_REQUEST,
        message: message || 'Solicitud inválida',
        error: 'Bad Request',
      },
      HttpStatus.BAD_REQUEST,
    );
  }
}
