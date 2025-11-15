from django.db import models

class Gene(models.Model):
    symbol = models.CharField(max_length=50, unique=True)
    full_name = models.CharField(max_length=150, blank=True, null=True)
    function_summary = models.TextField(blank=True, null=True)

    class Meta:
        managed = False # No se recrean las tablas en la base de datos
        db_table = 'gene'