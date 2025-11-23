import { HttpException, HttpStatus } from '@nestjs/common';

export class ConflictException extends HttpException {
  constructor(message: string) {
    super(
      {
        statusCode: HttpStatus.CONFLICT,
        message: message || 'Conflicto de recursos',
        error: 'Conflict',
      },
      HttpStatus.CONFLICT,
    );
  }
}
