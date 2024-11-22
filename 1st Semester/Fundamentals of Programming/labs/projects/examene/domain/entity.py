class Examen:
    def __init__(self, data, ora, materie, tip):
        self.__data = data
        self.__ora = ora
        self.__materie = materie
        self.__tip = tip

    def get_data(self):
        return self.__data

    def get_ora(self):
        return self.__ora

    def get_materie(self):
        return self.__materie

    def get_tip(self):
        return self.__tip

    def set_data(self, value):
        self.__data = value

    def set_ora(self, value):
        self.__ora = value

    def set_materie(self, value):
        self.__materie = value

    def set_tip(self, value):
        self.__tip = value
