from repository.repository import examenRepository
from domain.validator import Validator

class examenService:
    def __init__(self, examen: examenRepository):
        self.__examen = examen

    def next_day2(self):
        return self.__examen.next_day()

    def add2(self, data, ora, materie, tip):
        validator = Validator(self.__examen)
        if validator.validate(data, ora, materie, tip) != True:
            return validator.validate(data, ora, materie, tip)
        else:
            self.__examen.add_exam(data, ora, materie, tip)

    def interval(self, date):
        return self.__examen.third_day(date)

    def export2(self, filename, sir):
        self.__examen.export(filename, sir)
