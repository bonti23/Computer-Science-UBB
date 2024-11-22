from repository.repository import melodieRepository

class melodieValidator:
    def __init__(self, melodie: melodieRepository):
        self.__melodie= melodie

    def validate_tune(self, titlu, artist, gen, durata):
        errors = []
        durata = int(durata)
        if durata <= 0:
            eroare = "Durata trebuie sa fie un numar strict pozitiv!"
            errors.append(eroare)
        if gen != "rock" and gen != "pop" and gen != "jazz" and gen != "altele":
            eroare = "Nu exista un asemenea gen!"
            errors.append(eroare)
        """
        if self.__melodie.find_artist(artist):
            if self.__melodie.find_title(titlu):
                eroare = "Exista deja o asemenea melodie!" + "\n"
                errors.append(eroare)
        """
        if len(errors) == 0:
            return True
        else:
            return errors
