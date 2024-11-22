from domain.entity import Piesa
import random

class piesaRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = 'r')
        piese = []
        lines = f.readlines()
        for line in lines:
            params = line.split(',')
            params = [param.strip() for param in params]
            titlu = params[0]
            regizor = params[1]
            gen = params[2]
            durata = params[3]
            piesa = Piesa(titlu, regizor, gen, durata)
            piese.append(piesa)
        f.close()
        return piese

    def write_to_file(self, piese):
        with open(file = self.__filename, mode = 'w') as f:
            for piesa in piese:
                params2 = (piesa.get_titlu(), piesa.get_regizor(), piesa.get_gen(), piesa.get_durata())
                params2 = [str(param) for param in params2]
                line = ",".join(params2) + "\n"
                f.write(line)

    def add_repository(self, titlu, regizor, gen, durata):
        piese = self.read_from_file()
        piesa = Piesa(titlu, regizor, gen, durata)
        piese.append(piesa)
        self.write_to_file(piese)

    def modify_repository(self, titlu, regizor, gen, durata, new_gen, new_durata):
        ok = False
        piese = self.read_from_file()
        for piesa in piese:
            if piesa.get_titlu() == titlu and piesa.get_regizor() == regizor and piesa.get_gen() == gen and piesa.get_durata() == durata:
                    ok = True
                    piesa.set_gen(new_gen)
                    piesa.set_durata(new_durata)

        if ok == False:
            raise ValueError("This play doesn't exist!")
        self.write_to_file(piese)

    def random_repository(self, numar):
        piese = self.read_from_file()
        vocale = ["a", "e", "i", "o", "u"]
        consoane = ["b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"]
        ok = 0
        for i in range(numar):
            lungime = random.randint(8, 12)
            text = []
            for i in range(lungime):
                if i == 4:
                    text.append(" ")
                elif ok == 0:
                    text.append(random.choice(vocale))
                    ok = 1
                elif ok == 1:
                    text.append(random.choice(consoane))
                    ok = 0

            text_string = "".join(text)
            titlu, regizor = text_string.split(" ", 1)

            genuri = ["Comedie", "Drama", "Satira", "Altele"]
            gen = random.choice(genuri)

            durata = random.randint(900, 3600)

            piesa = Piesa(titlu, regizor, gen, durata)
            piese.append(piesa)

        self.write_to_file(piese)

    def export_file_repository(self, nume_fisier):
        piese = self.read_from_file()
        with open(file = nume_fisier, mode = "w") as f:
            for piesa in piese:
                params3 = (piesa.get_titlu(), piesa.get_regizor(), piesa.get_gen(), piesa.get_durata())
                params3 = [str(param) for param in params3]
                line = ",".join(params3) + "\n"
                f.write(line)




