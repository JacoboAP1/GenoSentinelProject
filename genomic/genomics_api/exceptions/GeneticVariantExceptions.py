class InvalidImpactValueException(Exception):
    def __init__(self, message):
        super().__init__(message)

class VariantNotFoundException(Exception):
    def __init__(self, message):
        super().__init__(message)

class ChromosomeDuplicatedException(Exception):
    def __init__(self, message):
        super().__init__(message)