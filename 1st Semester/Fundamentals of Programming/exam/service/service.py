from repository.repository import jucatorRepository
from domain.validator import jucatorValidator

class jucatorService:
    def __init__(self, jucator: jucatorRepository):
        self.__jucator = jucator

    def add_service(self, nume, prenume, inaltime, post):
        """
        functie adaugare jucator
        daca validatorul returneaza True, se adauga, daca nu, returneaza erorile
        """
        validator = jucatorValidator(self.__jucator)
        if validator.validate_jucator(nume, prenume, inaltime, post) == True:
            self.__jucator.add_repository(nume, prenume, inaltime, post)
        else:
            return validator.validate_jucator(nume, prenume, inaltime, post)

    def modify_service(self, numar):
        """
        functie de modificare inaltime
        """
        return self.__jucator.modify_repository(numar)

    def tiparire_service(self):
        """
        functie de tiparire

        """
        return self.__jucator.tiparire_repository()

    def import_service(self, filename):
        """
        functie de import
        """
        return self.__jucator.import_repository2(filename)
