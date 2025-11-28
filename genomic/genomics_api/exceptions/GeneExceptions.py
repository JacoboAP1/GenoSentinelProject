class GeneNotFoundException(Exception):
    def __init__(self, message):
        super().__init__(message)

class GeneDuplicatedException(Exception):
    def __init__(self, message):
        super().__init__(message)