from domain.entity import Emisiune
from random import *

class emisiuneRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = 'r')
        emisiuni = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            nume = params[0]
            tip = params[1]
            durata = params[2]
            descriere = params[3]
            emisiune = Emisiune(nume, tip, durata, descriere)
            emisiuni.append(emisiune)
        f.close()
        return emisiuni

    def write_to_file(self, emisiuni):
        with open(file = self.__filename, mode = 'w') as f:
            for emisiune in emisiuni:
                params = (emisiune.get_nume(), emisiune.get_tip(), emisiune.get_durata(), emisiune.get_descriere())
                params = [str(param) for param in params]
                line = ",".join(params)+"\n"
                f.write(line)

    def stergere(self, nume, tip):
        emisiuni = self.read_from_file()
        eliminari = []
        for i, emisiune in enumerate(emisiuni):
            try:
                if emisiune.get_nume() == nume and emisiune.get_tip() == tip:
                    eliminari.append(i)
            except:
                raise ValueError("Nu exista emisiunea!")
        for index in reversed(eliminari):
            emisiuni.pop(index)
        self.write_to_file(emisiuni)

    def actualizare(self, nume, tip, durata, descriere, new_durata, new_descriere):
        emisiuni = self.read_from_file()
        for emisiune in emisiuni:
            try:
                if emisiune.get_nume() == nume and emisiune.get_tip() == tip and emisiune.get_durata() == durata and emisiune.get_descriere() == descriere:
                    emisiune.set_durata(new_durata)
                    emisiune.set_descriere(new_descriere)
            except:
                raise ValueError("Nu exista emisiunea!")
        self.write_to_file(emisiuni)

    def tiparire_program(self, ora_inceput, ora_sfarsit):
        emisiuni = self.read_from_file()

        lista_de_liste = []

        interval = []
        ora_inceput = int(ora_inceput)
        ora_sfarsit = int(ora_sfarsit)
        for i in range(ora_inceput, ora_sfarsit + 1):
            interval.append(i)

        deja_adaugate = []

        for emisiune in emisiuni:
            lista = []

            ora = choice(interval)
            interval.remove(ora)

            nume = emisiune.get_nume()
            if nume not in deja_adaugate:
                tip = emisiune.get_tip()
                descriere = emisiune.get_descriere()

                lista.append(ora)
                lista.append(nume)
                lista.append(tip)
                lista.append(descriere)

                lista_de_liste.append(lista)

                deja_adaugate.append(nume)

        if len(lista_de_liste) <=2 :
            lista_de_liste *= 2
            for i in range(len(lista_de_liste)):
                lista_de_liste[i][3] = "****"

        return lista_de_liste
