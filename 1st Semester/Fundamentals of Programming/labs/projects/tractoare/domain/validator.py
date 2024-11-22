from repository.repository import tractorRepository
from datetime import *

class Validator:
    def __init__(self, tractor: tractorRepository):
        self.__tractor = tractor

    def validate(self, id, denumire, pret, model, data):
        errors = []
        if self.__tractor.find_id(id):
            errors.append("id deja existent!")
        if denumire == "":
            errors.append("denumire vida!")
        pret = int(pret)
        if pret <= 0:
            errors.append("pretul trebuie sa fie un numar pozitiv!")
        if model == "":
            errors.append("model vid!")
        data = datetime.strptime(data, "%d.%m.%Y")
        if not((data.month >= 1 and data.month <= 12) or (data.day >= 1 and data.day <= 12)):
            errors.append("data invalida!")
        if len(errors) == 0:
            return True
        else:
            return errors
