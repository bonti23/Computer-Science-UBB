class Emisiune:
    def __init__(self, nume, tip, durata, descriere):
        self.__nume = nume
        self.__tip = tip
        self.__durata = durata
        self.__descriere = descriere

    def get_nume(self):
        return self.__nume

    def get_tip(self):
        return self.__tip

    def get_durata(self):
        return self.__durata

    def get_descriere(self):
        return self.__descriere

    def set_nume(self, valoare):
        self.__nume = valoare

    def set_tip(self, valoare):
        self.__tip = valoare

    def set_durata(self, valoare):
        self.__duratata = valoare

    def set_descriere(self, valoare):
        self.__descriere = valoare
