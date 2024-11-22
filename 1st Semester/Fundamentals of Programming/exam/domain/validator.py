from repository.repository import jucatorRepository

class jucatorValidator:
    """
    clasa pentru validarea unui jucator
    """
    def __init__(self, jucator: jucatorRepository):
        self.__jucator = jucator

    def validate_jucator(self, nume, prenume, inaltime, post):
        """
        valideaza jucatorul
        nume, prenume, inaltime, post de tip string
        numele si prenumele nu pot fi vide
        inaltimea trebuie sa fie mai mare de 140cm
        postul trebuie sa fie unul dintre: fundas, extrema si pivot
        lista errors contine erorile pentru fiecare
        daca lungimea ei este mai mare de 0, atunci jucatorul nu este valid => return errors
        altfel, este valid => return True
        """
        errors = []
        if nume == "":
            errors.append("Numele nu poate fi vid!")
        if prenume == "":
            errors.append("Prenumele nu poate fi vid!")
        inaltime = int(inaltime)
        if inaltime <= 140:
            errors.append("Inaltimea trebuie sa fie un numar intreg mai mare decat 140")
        if post != "fundas" and post != "pivot" and post != "extrema":
            errors.append("Post invalid!")
        if len(errors) == 0:
            return True
        else:
            return errors
