from repository.repository import piesaRepository
class piesaValidator():
    def __init__(self, piesa: piesaRepository):
        self.__piesa = piesa

    def validate_piesa(self, titlu, regizor, gen, durata):
        if titlu == " ":
            raise ValueError("Titlu vid!")
        if regizor == " ":
            raise ValueError("Regizor vid!")
        if gen != "Comedie" and gen != "Drama" and gen != "Satira" and gen != "Altele":
            raise ValueError("Doesn't exist this kind of category of plays!")
        durata = int(durata)
        if durata <= 0:
            raise ValueError("The duration cannot be negative!")
