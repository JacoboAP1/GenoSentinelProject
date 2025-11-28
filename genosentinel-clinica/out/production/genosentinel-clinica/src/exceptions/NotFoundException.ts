import { HttpException, HttpStatus } from '@nestjs/common';

export class NotFoundException extends HttpException {
  constructor(message: string) {
    super(
      {
        statusCode: HttpStatus.NOT_FOUND,
        message: message || 'El recurso no fue encontrado',
        error: 'Not Found',
      },
      HttpStatus.NOT_FOUND,
    );
  }
}
