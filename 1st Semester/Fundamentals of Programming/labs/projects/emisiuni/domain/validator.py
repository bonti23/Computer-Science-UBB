from repository.repository import emisiuneRepository

class emisiuneValidator:
    def __init__(self, emisiune: emisiuneRepository):
        self.__emisiune = emisiune

    def emisiune_validator(self, nume, tip, durata, descriere):
        if len(nume)<4:
            raise ValueError("nume de emisiune invalid!")
        if tip != "stiri" or tip != "informare" or tip != "film":
            raise ValueError("nu exista un asemenea tip!")
        if durata <= 0 or durata > 14:
            raise ValueError("durata invalida!")
        if len(descriere)<=2:
            raise ValueError("descriere mult prea scurta!")
