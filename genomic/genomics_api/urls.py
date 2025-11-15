from django.urls import path, include
from rest_framework.routers import DefaultRouter

from genomics_api.views import (
    GeneViewSet,
    GeneticVariantViewSet,
    PatientVariantReportViewSet
)

router = DefaultRouter()
router.register(r'gene', GeneViewSet)
router.register(r'variants', GeneticVariantViewSet)
router.register(r'reports', PatientVariantReportViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
