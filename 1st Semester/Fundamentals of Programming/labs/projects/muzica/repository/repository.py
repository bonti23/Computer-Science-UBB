from domain.entity import Melodie
import random

class melodieRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode="r")
        melodii = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            titlu = params[0]
            artist = params[1]
            gen = params[2]
            durata = params[3]
            melodie = Melodie(titlu, artist, gen, durata)
            melodii.append(melodie)
        f.close()
        return melodii

    def write_to_file(self, melodii):
        with open(file = self.__filename, mode="w") as f:
            for melodie in melodii:
                params = (melodie.get_titlu(), melodie.get_artist(), melodie.get_gen(), melodie.get_durata())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def modify_repository(self, titlu, artist, gen, durata, new_gen, new_durata):
        melodii = self.read_from_file()
        ok = False
        for melodie in melodii:
            if melodie.get_titlu() == titlu and melodie.get_artist() == artist and melodie.get_gen() == gen and melodie.get_durata() == durata:
                ok = True
                melodie.set_gen(new_gen)
                melodie.set_durata(new_durata)
        self.write_to_file(melodii)
        if ok == False:
            raise ValueError("No such tune found")

    def find_artist(self, artist):
        melodii = self.read_from_file()
        for melodie in melodii:
            if melodie.get_artist() == artist:
                return melodie
        return None

    def find_title(self, titlu):
        melodii = self.read_from_file()
        for melodie in melodii:
            if melodie.get_titlu() == titlu:
                return melodie
        return None

    def random_repository(self, numar, lista_artisti, lista_melodii):
        melodii = self.read_from_file()
        genuri = ["rock", "pop", "jazz", "altele"]
        for i in range(numar):
            artist = random.choice(lista_artisti)
            titlu = random.choice(lista_melodii)
            gen = random.choice(genuri)
            durata = random.randint(0, 300)
            melodie = Melodie(artist, titlu, gen, durata)
            melodii.append(melodie)
        self.write_to_file(melodii)
        return numar

    def export_repository(self, filename):
        melodii = self.read_from_file()
        melodii_sortate = sorted(melodii, key=lambda x: x.get_titlu(), reverse=False)
        melodii_sortate2 = sorted(melodii_sortate, key=lambda x: x.get_artist(), reverse=False)
        with open(file = filename, mode = "w") as f:
            for melodie in melodii_sortate2:
                params = (melodie.get_titlu(), melodie.get_artist(), melodie.get_gen(), melodie.get_durata())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

