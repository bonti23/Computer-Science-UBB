from domain.entity import Jucator
import random

class jucatorRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = 'r')
        jucatori = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            nume = params[0]
            prenume = params[1]
            inaltime = params[2]
            post = params[3]
            jucator = Jucator(nume, prenume, inaltime, post)
            jucatori.append(jucator)
        f.close()
        return jucatori

    def write_to_file(self, jucatori):
        with open(file = self.__filename, mode = 'w') as f:
            for jucator in jucatori:
                params = (jucator.get_nume(), jucator.get_prenume(), jucator.get_inaltime(), jucator.get_post())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def add_repository(self, nume, prenume, inaltime, post):
        jucatori = self.read_from_file()
        jucator = Jucator(nume, prenume, inaltime, post)
        jucatori.append(jucator)
        self.write_to_file(jucatori)

    def modify_repository(self, nume, prenume, new_height):
        jucatori = self.read_from_file()
        ok = False
        for jucator in jucatori:
            if jucator.get_nume() == nume and jucator.get_prenume() == prenume:
                jucator.set_inaltime(new_height)
                ok = True
        if ok == False:
            raise ValueError("No such player!")
        self.write_to_file(jucatori)

    def filter_repository(self):
        jucatori = self.read_from_file()

        fundasi = [jucator for jucator in jucatori if jucator.get_post() == "fundas"]
        pivoti = [jucator for jucator in jucatori if jucator.get_post() == "pivot"]
        extreme = [jucator for jucator in jucatori if jucator.get_post() == "extrema"]

        max_fundas = max(fundasi, key=lambda jucator: jucator.get_inaltime(), default=None)
        fundasi.remove(max_fundas)
        max_fundas2 = max(fundasi, key=lambda jucator: jucator.get_inaltime(), default=None)
        list = []
        list.append(max_fundas)
        list.append(max_fundas2)

        max_extrema = max(extreme, key=lambda jucator: jucator.get_inaltime(), default=None)
        extreme.remove(max_extrema)
        max_extrema2 = max(extreme, key=lambda jucator: jucator.get_inaltime(), default=None)
        list.append(max_extrema)
        list.append(max_extrema2)

        max_pivot = max(pivoti, key=lambda jucator: jucator.get_inaltime(), default=None)
        list.append(max_pivot)

        return list

    def export_repository(self, filename):
        jucatori = self.read_from_file()
        posturi = ['extrema', 'pivot', 'fundas']
        with open(file = filename, mode = 'w') as f:
            for jucator in jucatori:
                inaltime = random.randint(150, 200)
                post = random.choice(posturi)
                params = (jucator.get_nume(), jucator.get_prenume(), inaltime, post)
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

