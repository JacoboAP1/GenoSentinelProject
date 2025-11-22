from rest_framework.views import exception_handler
from rest_framework.response import Response
from rest_framework import status

from genomics_api.exceptions.FieldNotFilledException import (
    FieldNotFilledException,
)
from genomics_api.exceptions.GeneticVariantExceptions import (
    InvalidImpactValueException,
)

def custom_exception_handler(exc, context):
    # Primero se deja que DRF maneje sus propias excepciones
    response = exception_handler(exc, context)

    # Si es una de las excepciones personalizadas:
    if isinstance(exc, FieldNotFilledException):
        return Response(
            {"success": False, "error": "Some field not filled", "message": str(exc)},
            status=status.HTTP_400_BAD_REQUEST
        )

    if isinstance(exc, InvalidImpactValueException):
        return Response(
            {"success": False, "error": "Invalid impact value", "message": str(exc)},
            status=status.HTTP_206_PARTIAL_CONTENT
        )

    # Si ninguna coincidió pero DRF devolvió una respuesta por defecto
    if response is not None:
        # Empaquetamos igual que el DTO de salida
        response.data = {
            "success": False,
            "error": "DRF_ERROR",
            "details": response.data
        }
        return response

    # Caso extremo: excepción inesperada 500
    return Response(
        {"success": False, "error": "INTERNAL_ERROR", "message": str(exc)},
        status=status.HTTP_500_INTERNAL_SERVER_ERROR
    )
