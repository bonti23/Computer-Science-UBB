from repository.repository import jucatorRepository
from domain.validator import jucatorValidator

class jucatorService:
    def __init__(self, jucator: jucatorRepository):
        self.__jucator = jucator

    def add_service(self, nume, prenume, inaltime, post):
        validator = jucatorValidator(self.__jucator)
        if validator.validate_jucator(nume, prenume, inaltime, post) == True:
            self.__jucator.add_repository(nume, prenume, inaltime, post)
        else:
            return validator.validate_jucator(nume, prenume, inaltime, post)

    def modify_service(self, nume, prenume, new_height):
        try:
            return self.__jucator.modify_repository(nume, prenume, new_height)
        except ValueError as e:
            print(e)

    def filter_service(self):
        return self.__jucator.filter_repository()

    def export_service(self, filename):
        self.__jucator.export_repository(filename)
