from repository.repository import jucatorRepository

class jucatorValidator:
    def __init__(self, jucator: jucatorRepository):
        self.__jucator = jucator

    def validate_jucator(self, nume, prenume, inaltime, post):
        errors = []
        if nume == "":
            errors.append("nume vid!")
        if prenume == "":
            errors.append("prenume vid!")
        inaltime = int(inaltime)
        if inaltime <= 0:
            errors.append("inaltimea trebuie sa fie un numar strict pozitiv!")
        if post != "fundas" and post != "extrema" and post != "pivot":
            errors.append("nu exista acest post!")
        if len(errors) > 0:
            return errors
        else:
            return True
