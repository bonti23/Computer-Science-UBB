from repository.repository import evenimentRepository
from domain.validator import evenimentValidator

class evenimentService:
    def __init__(self, eveniment: evenimentRepository):
        self.__eveniment = eveniment

    def zi_curenta2(self):
        return self.__eveniment.zi_curenta()

    def add_service(self, data, ora, descriere):
        validator = evenimentValidator(self.__eveniment)
        if validator.validate_eveniment(data, ora, descriere) != True:
            return validator.validate_eveniment(data, ora, descriere)
        else:
            return self.__eveniment.add_repo(data, ora, descriere)

    def data_stabilita2(self, data):
        return self.__eveniment.data_stabilita(data)

    def export2(self, filename, sir):
        self.__eveniment.export(filename, sir)
