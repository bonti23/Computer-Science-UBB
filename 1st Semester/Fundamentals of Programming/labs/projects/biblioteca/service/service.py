from repository.repository import carteRepository
from domain.validator import carteValidator

class carteService:
    def __init__(self, carte: carteRepository):
        self.__carte = carte

    def add_service(self, id, titlu, autor, anAparitie):
        validator = carteValidator(self.__carte)
        validator.validate_carte(id, titlu, autor, anAparitie)
        self.__carte.add_repository(id, titlu, autor, anAparitie)

    def delete_service(self, cifra):
        self.__carte.delete_repository(cifra)

    def filter_service(self, sir, numar):
        return self.__carte.filter_repository(sir, numar)

    def undo_service(self):
        self.__carte.undo()
