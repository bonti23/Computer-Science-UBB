from repository.repository import piesaRepository
from domain.validator import piesaValidator

class piesaService:
    def __init__(self, piesa: piesaRepository):
        self.__piesa = piesa

    def add_service(self, titlu, regizor, gen, durata):
        validator = piesaValidator(self.__piesa)
        validator.validate_piesa(titlu, regizor, gen, durata)
        self.__piesa.add_repository(titlu, regizor, gen, durata)

    def modify_service(self, titlu, regizor, gen, durata, new_gen, new_durata):
        validator = piesaValidator(self.__piesa)
        validator.validate_piesa(titlu, regizor, new_gen, new_durata)
        return self.__piesa.modify_repository(titlu, regizor, gen, durata, new_gen, new_durata)

    def random_service(self, numar):
        return self.__piesa.random_repository(numar)

    def export_file_service(self, new_filename):
        return self.__piesa.export_file_repository(new_filename)
