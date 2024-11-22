from repository.repository import emisiuneRepository
from domain.validator import emisiuneValidator

class emisiuneService:
    def __init__(self, emisiune: emisiuneRepository):
        self.__emisiune = emisiune

    def stergere_service(self, nume, tip):
        return self.__emisiune.stergere(nume, tip)

    def actualizare_service(self, nume, tip, durata, descriere, new_durata, new_descriere):
        validator = emisiuneValidator(self.__emisiune)
        validator.emisiune_validator(nume, tip, new_durata, new_descriere)
        return self.__emisiune.actualizare(nume, tip, durata, descriere, new_durata, new_descriere)

    def tiparire_program(self, ora_inceput, ora_sfarsit):
        return self.__emisiune.tiparire_program(ora_inceput, ora_sfarsit)
