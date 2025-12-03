from django.db import models
from genomics_api.models.entities.Gene import Gene

class GeneticVariant(models.Model):
    gene = models.ForeignKey(
        Gene,
        on_delete=models.CASCADE,
        db_column='gene_id'
    )
    chromosome = models.CharField(max_length=10)
    position = models.IntegerField()
    reference_base = models.CharField(max_length=1)
    alternate_base = models.CharField(max_length=1)
    impact = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        db_table = 'genomics_api_geneticvariant'