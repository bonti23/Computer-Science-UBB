from datetime import *
from repository.repository import evenimentRepository

class evenimentValidator:
    def __init__(self, eveniment: evenimentRepository):
        self.__eveniment = eveniment

    def validate_eveniment(self, data, ora, descriere):
        errors = []
        data = datetime.strptime(data, "%d.%m.%Y")
        if not ((data.year >= 2024 and data.year <= 2025) and (data.month >=1 and data.month <=12) and (data.day >=1 and data.day<=31)):
            errors.append("data invalida!")
        ora = datetime.strptime(ora, "%H.%M")
        if not ((ora.hour >= 0 and ora.hour <= 23) and (ora.minute >= 0 and ora.minute <= 59)):
            errors.append("ora invalida!")
        if len(descriere) <= 2:
            errors.append("descriere mult prea scurta!")
        if len(errors) == 0:
            return True
        else:
            return errors
