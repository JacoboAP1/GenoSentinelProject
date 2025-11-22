from rest_framework import permissions
from drf_yasg.views import get_schema_view
from drf_yasg import openapi

schema_view = get_schema_view(
    openapi.Info(
        title="Genomic Service API",
        default_version='v1',
        description="Microservicio para análisis y procesamiento de datos genómicos",
        terms_of_service="https://example.com/terms/",
        contact=openapi.Contact(email="contact@genosentinel.com"),
        license=openapi.License(name="MIT License"),
    ),
    public=True,
    permission_classes=[permissions.AllowAny],
)
