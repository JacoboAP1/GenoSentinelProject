class GeneOutDTO:
    def __init__(self, symbol, full_name):
        self.symbol = symbol
        self.full_name = full_name

    def to_dict(self):
        return {
            "symbol": self.symbol,
            "full_name": self.full_name
        }
