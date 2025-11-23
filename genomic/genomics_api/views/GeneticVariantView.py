from rest_framework import viewsets
from rest_framework.response import Response
from genomics_api.services.GeneticVariantService import VariantService
from genomics_api.models.entities.GeneticVariant import GeneticVariant
from drf_yasg.utils import swagger_auto_schema

class GeneticVariantViewSet(viewsets.ModelViewSet):
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Aunque este queryset no se usa realmente porque sobrescribimos todos los métodos del ViewSet
    # lo dejamos como .all() para evitar errores de inicialización del router
    queryset = GeneticVariant.objects.all()

    # DRF usa siempre estos método para el CRUD
    @swagger_auto_schema(
        operation_summary="Obtener toda la lista de variantes genéticas",
        operation_description="Retorna la información asociada de todas las variantes",
        responses={200: "Success"}
    )
    def list(self, request):
        result = VariantService.list_variants()
        return Response({
            "success": True,
            "data": result
        }) # Debe retornar response para mostrar status de respuesta y json
        # Solo cuando se sobreescriben los métodos de DRF y no se usa serializer

    @swagger_auto_schema(
        operation_summary="Obtener una variante por su ID",
        responses={200: "Success",
                   404: "Not found"}
    )
    def retrieve(self, request, pk=None):
        result = VariantService.get_variant(pk)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Crear una variante",
        operation_description="Retorna la info con la que se creó la variante",
        responses={200: "Success",
                   400: "Bad request"}
    )
    def create(self, request, *args, **kwargs):
        result = VariantService.create_variant(request.data)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Actualizar una variante",
        operation_description="Retorna la información que se cambió",
        responses={200: "Success",
                   400: "Bad request"}
    )
    def update(self, request, *args, **kwargs):
        result = VariantService.update_variant(self.get_object(), request.data)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Eliminar una variante por su ID",
        operation_description="Retorna mensaje de éxito",
        responses={200: "Success",
                   404: "Not found"}
    )
    def destroy(self, request, *args, **kwargs):
        result = VariantService.delete_variant(self.get_object())
        return Response({
            "success": True,
            "data": result
        })