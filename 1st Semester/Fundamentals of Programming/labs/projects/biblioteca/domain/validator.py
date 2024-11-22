from repository.repository import carteRepository

class carteValidator:
    def __init__(self, carte: carteRepository):
        self.__carte = carte

    def validate_carte(self, id, titlu, autor, anAparitie):
        if self.__carte.find_id(id):
            raise ValueError("id deja existent!")
        if titlu == "":
            raise ValueError("titlu vid!")
        if autor == "":
            raise ValueError("autor vid!")
        anAparitie = int(anAparitie)
        if anAparitie < 1 or anAparitie > 2024:
            raise ValueError("an inexistent!")
