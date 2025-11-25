from rest_framework import viewsets
from rest_framework.response import Response
from genomics_api.models.serializers.GeneSerializer import GeneSerializer
from genomics_api.services import GeneService
from genomics_api.models.entities.Gene import Gene
from drf_yasg.utils import swagger_auto_schema

class GeneViewSet(viewsets.ModelViewSet):
    serializer_class = GeneSerializer
    # Importante:
    # El router necesita que exista un atributo "queryset"
    # Aunque este queryset no se usa realmente porque sobrescribimos todos los métodos del ViewSet
    # lo dejamos como .all() para evitar errores de inicialización del router
    queryset = Gene.objects.all() # Necesario para el router

    # DRF usa siempre estos método para el CRUD
    @swagger_auto_schema(
        operation_summary="Obtener toda la lista de genes",
        operation_description="Retorna la información asociada de todos los genes",
        responses={200: "Success"}
    )
    def list(self, request):
        result = GeneService.list_genes()
        return Response({
            "success": True,
            "data": result
        }) # Debe retornar response para mostrar status de respuesta y json
            # Solo cuando se sobreescriben los métodos de DRF y no se usa serializer

    @swagger_auto_schema(
        operation_summary="Obtener un gen por ID",
        operation_description="Retorna la información asociada del gen especificado",
        responses={200: "Success",
                   404: "Not found"}
    )
    def retrieve(self, request, pk=None):
        result = GeneService.get_gene(pk)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Crear un gen",
        operation_description="Retorna la info con la que se creó el gen",
        responses={200: "Success",
                   400: "Bad request"}
    )
    def create(self, request, *args, **kwargs):
        result = GeneService.create_gene(request.data)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Actualizar un gen",
        operation_description="Retorna la información que se cambió",
        responses={200: "Success",
                   400: "Bad request"}
    )
    def update(self, request, *args, **kwargs):
        result = GeneService.update_gene(self.get_object(), request.data)
        return Response({
            "success": True,
            "data": result
        })

    @swagger_auto_schema(
        operation_summary="Eliminar un gen por su ID",
        operation_description="Retorna mensaje de éxito",
        responses={200: "Success",
                   404: "Not found"}
    )
    def destroy(self, request, *args, **kwargs):
        result = GeneService.delete_gene(self.get_object())
        return Response({
            "success": True,
            "data": result
        })