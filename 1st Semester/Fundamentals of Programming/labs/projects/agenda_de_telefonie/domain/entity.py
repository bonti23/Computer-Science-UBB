class Contact:
    def __init__(self, id, nume, telefon, grup):
        self.__id = id
        self.__nume = nume
        self.__telefon = telefon
        self.__grup = grup

    def get_id(self):
        return self.__id

    def get_nume(self):
        return self.__nume

    def get_telefon(self):
        return self.__telefon

    def get_grup(self):
        return self.__grup

    def set_id(self, value):
        self.__id = value

    def set_nume(self, value):
        self.__nume = value

    def set_telefon(self, value):
        self.__telefon = value

    def set_grup(self, value):
        self.__grup = value
