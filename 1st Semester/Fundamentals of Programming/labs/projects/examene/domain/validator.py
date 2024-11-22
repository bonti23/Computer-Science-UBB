from repository.repository import examenRepository
from datetime import *

class Validator:
    def __init__(self, examen: examenRepository):
        self.__examen = examen

    def validate(self, data, ora, materie, tip):
        errors = []
        data = datetime.strptime(data, "%d.%m")
        if not((data.month >=1 and data.month <=12) and (data.day >=1 and data.day<=31)):
            errors.append("data invalida!")
        ora = datetime.strptime(ora, "%H:%M")
        if not ((ora.hour >= 0 and ora.hour <=23) and (ora.minute >= 0 and ora.minute <=59)):
            errors.append("ora invalida!")
        if tip != "normal" and tip != "restanta":
            errors.append("tip invalid!")
        if self.__examen.find_materie(materie):
            if self.__examen.find_tip(tip):
                errors.append("nu pot exista 2 tipuri de examen la aceeasi materie!")
        if len(errors) == 0:
            return True
        else:
            return errors
