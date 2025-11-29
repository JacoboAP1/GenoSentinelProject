from rest_framework.views import exception_handler
from rest_framework.response import Response
from rest_framework import status

from genomics_api.exceptions.FieldNotFilledException import (
    FieldNotFilledException,
)
from genomics_api.exceptions.GeneExceptions import GeneNotFoundException, GeneDuplicatedException
from genomics_api.exceptions.GeneticVariantExceptions import (
    InvalidImpactValueException, VariantNotFoundException, ChromosomeDuplicatedException,
)
from genomics_api.exceptions.ReportExceptions import PatientNotFoundException, ReportNotFoundException, \
    ReportDuplicatedException


def custom_exception_handler(exc, context):
    # Primero se deja que DRF maneje sus propias excepciones
    response = exception_handler(exc, context)

    # Si es una de las excepciones personalizadas
    if isinstance(exc, FieldNotFilledException):
        return Response(
            {"success": False, "error": "Some field not filled", "message": str(exc)},
            status=status.HTTP_400_BAD_REQUEST
        )

    if isinstance(exc, InvalidImpactValueException):
        return Response(
            {"success": False, "error": "Invalid impact value", "message": str(exc)},
            status=status.HTTP_400_BAD_REQUEST
        )

    if isinstance(exc, PatientNotFoundException):
        return Response(
            {"success": False, "error": "Patient ID does not exist", "message": str(exc)},
            status=status.HTTP_404_NOT_FOUND
        )

    if isinstance(exc, GeneNotFoundException):
        return Response(
            {"success": False, "error": "Gene ID not found", "message": str(exc)},
            status=status.HTTP_404_NOT_FOUND
        )

    if isinstance(exc, GeneDuplicatedException):
        return Response(
            {"success": False, "error": "Gene symbol already exists", "message": str(exc)},
            status=status.HTTP_400_BAD_REQUEST
        )

    if isinstance(exc, VariantNotFoundException):
        return Response(
            {"success": False, "error": "Variant ID not found", "message": str(exc)},
            status=status.HTTP_404_NOT_FOUND
        )

    if isinstance(exc, ReportNotFoundException):
        return Response(
            {"success": False, "error": "Report ID not found", "message": str(exc)},
            status=status.HTTP_404_NOT_FOUND
        )

    if isinstance(exc, ChromosomeDuplicatedException):
        return Response(
            {"success": False, "error": "Chromosome already exists", "message": str(exc)},
            status=status.HTTP_400_BAD_REQUEST
        )

    if isinstance(exc, ReportDuplicatedException):
        return Response(
            {"success": False, "error": "Report already exists with that date", "message": str(exc)},
            status=status.HTTP_400_BAD_REQUEST
        )

    # Si ninguna coincidió pero DRF devolvió una respuesta por defecto
    if response is not None:
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
