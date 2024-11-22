from repository.repository import spectacolRepository

class Validator:
    def __init__(self, spectacol: spectacolRepository):
        self.__spectacol = spectacol

    def validate_spectacol(self, titlu, artist, gen, durata):
        errors = []
        if titlu == "" :
            errors.append("titlu vid!")
        if artist == "" :
            errors.append("artist vid!")
        if gen != "comedie" and gen != "concert" and gen != "balet" and gen != "altele":
            errors.append("gen invalid!")
        durata = int(durata)
        if durata <= 0:
            errors.append("durata trebuie sa fie un numar strict pozitiv!")

        """
        if self.__spectacol.find_titlu(titlu) != None:
            if self.__spectacol.find_artist(artist) != None:
                errors.append("exista deja un artis care a creat spectacolul cu acest nume!")
        """

        if len(errors) == 0:
            return True
        else:
            return errors
