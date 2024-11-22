class Jucator:
    """
    clasa jucator retine date pentru un jucator
    """
    def __init__(self, nume, prenume, inaltime, post):
        """
        functie de initializare pentru un jucator
        numele, prenumele, inaltimea si postul sunt tiputi de date string
        inaltimea va fi modificata pe parcursul programului in int
        """
        self.__nume = nume
        self.__prenume = prenume
        self.__inaltime = inaltime
        self.__post = post

    def get_nume(self):
        """
        returneaza numele unui jucator
        """
        return self.__nume

    def get_prenume(self):
        """
        returneaza prenumele unui jucator
        """
        return self.__prenume

    def get_inaltime(self):
        """
        returneaza inaltimea unui jucator
        """
        return self.__inaltime

    def get_post(self):
        """
        returneaza postul unui jucator
        """
        return self.__post

    def set_nume(self, value):
        """
        valoarea de tip string
        seteaza numele unui jucator cu o noua valoare
        """
        self.__nume = value

    def set_prenume(self, value):
        """
        valoarea de tip string
        seteaza prenumele unui jucator cu o noua valoare
        """
        self.__prenume = value

    def set_inaltime(self, value):
        """
        valoarea de tip string
        seteaza inaltimea unui jucator cu o noua valoare
        """
        self.__inaltime = value

    def set_post(self, value):
        """
        valoarea de tip string
        seteaza postul unui jucator cu o noua valoare
        """
        self.__post = value

def test_entity():
    jucator = Jucator("pop", "ana", "150", "pivot")
    assert jucator.get_nume() == "pop"
    assert jucator.get_prenume() == "ana"
    assert jucator.get_inaltime() == "150"
    assert jucator.get_post() == "pivot"

test_entity()
