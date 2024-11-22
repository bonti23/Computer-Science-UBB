class Eveniment:
    def __init__(self, data, ora, descriere):
        self.__data = data
        self.__ora = ora
        self.__descriere = descriere

    def get_data(self):
        return self.__data

    def get_ora(self):
        return self.__ora

    def get_descriere(self):
        return self.__descriere

    def set_data(self, value):
        self.__data = value

    def set_ora(self, value):
        self.__ora = value

    def set_descriere(self, value):
        self.__descriere = value
