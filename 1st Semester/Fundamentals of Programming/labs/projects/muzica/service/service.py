from repository.repository import melodieRepository
from domain.validator import melodieValidator

class melodieService:
    def __init__(self, melodie: melodieRepository):
        self.__melodie = melodie

    def modify_service(self, artist, titlu, gen, durata, new_gen, new_durata):
        validator = melodieValidator(self.__melodie)
        if validator.validate_tune(artist, titlu, new_gen, new_durata) == True:
            self.__melodie.modify_repository(artist, titlu, gen, durata, new_gen, new_durata)
        else:
            return validator.validate_tune(artist, titlu, new_gen, new_durata)

    def random_service(self, numar, lista_artisti, lista_melodii):
        return self.__melodie.random_repository(numar, lista_artisti, lista_melodii)

    def export_service(self, filename):
        return self.__melodie.export_repository(filename)
