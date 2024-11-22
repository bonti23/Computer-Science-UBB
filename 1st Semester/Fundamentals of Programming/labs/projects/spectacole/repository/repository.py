from domain.entity import Spectacol
import random

class spectacolRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file=self.__filename, mode='r')
        spectacole = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            titlu = params[0]
            artist = params[1]
            gen = params[2]
            durata = float(params[3])
            spectacol = Spectacol(titlu, artist, gen, durata)
            spectacole.append(spectacol)
        f.close()
        return spectacole

    def write_to_file(self, spectacole):
        with open(file=self.__filename, mode='w') as f:
            for spectacol in spectacole:
                params = (spectacol.get_titlu(), spectacol.get_artist(), spectacol.get_gen(), spectacol.get_durata())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def add_repository(self, titlu, artist, gen, durata):
        spectacole = self.read_from_file()
        spectacol = Spectacol(titlu, artist, gen, durata)
        spectacole.append(spectacol)
        self.write_to_file(spectacole)

    def modify_repository(self, titlu, artist, new_gen, new_durata):
        spectacole = self.read_from_file()
        ok = False
        for spectacol in spectacole:
            if spectacol.get_titlu() == titlu and spectacol.get_artist() == artist:
                spectacol.set_gen(new_gen)
                spectacol.set_durata(new_durata)
                ok = True
                break
        if not ok:
            raise ValueError("No such Spectacol found!")
        self.write_to_file(spectacole)

    def filter_repository(self):
        spectacole = self.read_from_file()
        filtered_spectacole = [spectacol for spectacol in spectacole if spectacol.get_gen() == "comedy"]
        return filtered_spectacole

    def export_repository(self, filename):
        spectacole = self.read_from_file()
        with open(file=filename, mode='w') as f:
            for spectacol in spectacole:
                random_duration = random.uniform(60, 180)
                random_gen = random.choice(["comedy", "drama", "action", "thriller"])
                params = (spectacol.get_titlu(), spectacol.get_artist(), random_gen, random_duration)
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

