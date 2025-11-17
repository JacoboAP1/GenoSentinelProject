from django.db import models

class Gene(models.Model):
    symbol = models.CharField(max_length=50, unique=True)
    full_name = models.CharField(max_length=150, blank=True, null=True)
    function_summary = models.TextField(blank=True, null=True)

<<<<<<< Updated upstream:genomic/genomics_api/models/gene.py
    def __str__(self):
        return self.symbol
=======
    class Meta:
        managed = False # No se migran las tablas en la base de datos, se usan las existentes
        db_table = 'gene'
>>>>>>> Stashed changes:genomic/genomics_api/models/entities/Gene.py
