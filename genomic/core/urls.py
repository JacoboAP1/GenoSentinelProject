from django.contrib import admin
from django.urls import path, include
from genomics_api.documentation.Swagger import schema_view

# Todas las rutas definidas en genomics_api/urls.py van a vivir bajo el prefijo /genomics/
urlpatterns = [
    path('admin/', admin.site.urls),

    # microservicio genómico
    path('genomic/', include('genomics_api.urls')),

    # Swagger UI
    path('genomic/swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='swagger-ui')
]
