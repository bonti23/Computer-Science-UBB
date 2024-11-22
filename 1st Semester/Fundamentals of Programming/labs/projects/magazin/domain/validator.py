from repository.repository import produsRepository

class produsValidator:
    def __init__(self, produs: produsRepository):
        self.__produs = produs

    def validate_produs(self, id, denumire, pret):
        errors = []
        if self.__produs.find_id(id):
            eroare = "id deja existent!"
            errors.append(eroare)
        pret = int(pret)
        if pret <= 0:
            eroare = "pretul nu poate fi negativ!"
            errors.append(eroare)
        if len(denumire) <= 1:
            eroare = "denumire mult prea scurta!"
            errors.append(eroare)
        if len(errors) != 0:
            return errors
        else:
            return True
