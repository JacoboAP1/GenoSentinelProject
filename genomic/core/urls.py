from django.contrib import admin
from django.urls import path, include

# Todas las rutas definidas en genomics_api/urls.py van a vivir bajo el prefijo /genomics/
urlpatterns = [
    path('admin/', admin.site.urls),

    # microservicio genómico
    path('genomics/', include('genomics_api.urls')),
]
