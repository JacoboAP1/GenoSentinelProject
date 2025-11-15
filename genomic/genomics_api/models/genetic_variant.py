from django.db import models
from .gene import Gene

class GeneticVariant(models.Model):
    gene = models.ForeignKey(Gene, on_delete=models.CASCADE, related_name='variants')
    chromosome = models.CharField(max_length=10)
    position = models.IntegerField()
    reference_base = models.CharField(max_length=1)
    alternate_base = models.CharField(max_length=1)
    impact = models.CharField(max_length=20, blank=True, null=True)

    def __str__(self):
        return f"{self.gene.symbol} {self.reference_base}>{self.alternate_base} ({self.chromosome}:{self.position})"
