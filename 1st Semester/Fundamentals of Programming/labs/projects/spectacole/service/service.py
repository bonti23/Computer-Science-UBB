from repository.repository import spectacolRepository
from domain.validator import Validator

class spectacolService:
    def __init__(self, spectacol: spectacolRepository):
        self.__spectacol = spectacol

    def adaugare2(self, titlu, artist, gen, durata):
        validated = Validator(self.__spectacol)
        if validated.validate_spectacol(titlu, artist, gen, durata) != True:
            return validated.validate_spectacol(titlu, artist, gen, durata)
        else:
            return self.__spectacol.adaugare(titlu, artist, gen, durata)

    def modificare2(self, titlu, artist, gen_nou, durata_noua):
        validated = Validator(self.__spectacol)
        if validated.validate_spectacol(" ", " ", gen_nou, durata_noua) != True:
            return validated.validate_spectacol(" ", " ", gen_nou, durata_noua)
        else:
            return self.__spectacol.modificare(titlu, artist, gen_nou, durata_noua)

    def random2(self, numar):
        return self.__spectacol.random(numar)

    def export2(self, filename):
        return self.__spectacol.export(filename)
