from domain.entity import Eveniment
from datetime import *

class evenimentRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = "r")
        evenimente = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [params.strip() for params in params]
            data = params[0]
            ora = params[1]
            descriere = params[2]
            eveniment = Eveniment(data, ora, descriere)
            evenimente.append(eveniment)
        f.close()
        return evenimente

    def write_to_file(self, evenimente):
        with open(file = self.__filename, mode = "w") as f:
            for eveniment in evenimente:
                params = (eveniment.get_data(), eveniment.get_ora(), eveniment.get_descriere())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def zi_curenta(self):
        evenimente = self.read_from_file()
        bune = []
        azi = date.today().strftime("%d.%m.%Y")
        azi = str(azi)
        for eveniment in evenimente:
            if azi == eveniment.get_data():
                bune.append(eveniment)
        sortate = sorted(bune, key = lambda x: x.get_ora(), reverse = False)
        if len(sortate) == 0:
            return None
        else:
            return sortate

    def add_repo(self, data, ora, descriere):
        evenimente = self.read_from_file()
        eveniment = Eveniment(data, ora, descriere)
        evenimente.append(eveniment)
        self.write_to_file(evenimente)

    def data_stabilita(self, data):
        bune = []
        evenimente = self.read_from_file()
        for eveniment in evenimente:
            if eveniment.get_data() == data:
                bune.append(eveniment)
        if len(bune) == 0:
            return None
        else:
            return bune

    def export(self, filename, sir):
        evenimente = self.read_from_file()
        bune = []
        for eveniment in evenimente:
            if sir in eveniment.get_descriere():
                bune.append(eveniment)
        sortate = sorted(bune, key = lambda x: x.get_data(), reverse = True)
        sortate2 = sorted(sortate, key = lambda x: x.get_ora(), reverse = True)

        with open(file = filename, mode = "w") as f:
            for event in sortate2:
                params = (event.get_data(), event.get_ora(), event.get_descriere())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)



