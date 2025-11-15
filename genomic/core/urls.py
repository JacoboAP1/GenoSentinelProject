from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),

    # microservicio genómico
    path('genomics/', include('genomics_api.urls')),
]
