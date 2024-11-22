import random
from domain.entity import Jucator

class jucatorRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        """
        functie pentru citirea din fisier a jucatorilor
        """
        f = open(file = self.__filename, mode = "r")
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
        """
        functie pentru afisarea in fisier a jucatorilor
        parametrii de intrare: lista jucatori
        """
        with open(file = self.__filename, mode = "w") as f:
            for jucator in jucatori:
                params = (jucator.get_nume(), jucator.get_prenume(), jucator.get_inaltime(), jucator.get_post())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def add_repository(self, nume, prenume, inaltime, post):
        """
        functie pentru adaugarea unui jucator
        date de intrare: nume, prenume, inaltime, post
        tipul lor: string
        functia citeste lista de jucatori din fisier, la care se adauga jucatorul nou
        se rescrie lista jucatorilor in fisier (update)
        """
        jucatori = self.read_from_file()
        jucator = Jucator(nume, prenume, inaltime, post)
        jucatori.append(jucator)
        self.write_to_file(jucatori)

    def modify_repository(self, numar):
        """
        functi pentru modificarea inaltimilor jucatorilor din fisier
        date de intrare: numar de tip string
        se converteste la int
        functia citeste lista de jucatori din fisier
        se pacurge lista si se modifica inaltimea pentru fiecare jucator
        se rescrie lista jucatorilor in fisier (update)
        """
        numar = int(numar)
        jucatori = self.read_from_file()
        for jucator in jucatori:
            inaltime = jucator.get_inaltime()
            inaltime = int(inaltime)
            inaltime = inaltime + numar
            jucator.set_inaltime(inaltime)
        self.write_to_file(jucatori)

    def tiparire_repository(self):
        """
        tiparirea noii echipe
        functia citeste lista de jucatori din fisier
        se ordoneaza lista de jucatori in functie de inaltime
        avem 3 noi liste: fundasi, extreme, pivoti
        se parcurge si se adauga in fiecare lista jucatori astfel incat:
        sunt maxim: 2 fundasi, 2 extreme si un pivot
        ulterior se adauga in lista echipa cei 5 jucatori si se returneaza lista
        """
        jucatori = self.read_from_file()
        fundasi = []
        extreme = []
        pivoti = []
        echipa = []
        sortati_inaltime = sorted(jucatori, key = lambda x: x.get_inaltime(), reverse = True)
        for jucator in sortati_inaltime:
            if jucator.get_post() == "fundas" and len(fundasi) < 2:
                fundasi.append(jucator)
            if jucator.get_post() == "extrema" and len(extreme) < 2:
                extreme.append(jucator)
            if jucator.get_post() == "pivot" and len(pivoti) < 1:
                pivoti.append(jucator)

        for jucator in fundasi:
            echipa.append(jucator)

        for jucator in pivoti:
            echipa.append(jucator)

        for jucator in extreme:
            echipa.append(jucator)

        return echipa

    def find(self, nume, prenume, jucatori):
        """
        functie de cautare pentru jucatorii care au acelasi nume si acelasi prenume
        se returneaza cati sunt de acest tip
        """
        contor = 0
        for jucator in jucatori:
            if jucator.get_nume() == nume and jucator.get_prenume() == prenume:
                contor = contor + 1
        return contor

    def import_repository(self, filename):
        """
        parametrii de intrare: numele fisierului
        import dintr-un fisier existent numele si prenumele
        se genereaza random postul si inaltimea
        si se rescrie in fisier pt fiecare jucator inaltimea si postul
        """
        posturi = ["fundas", "extrema", "pivot"]
        f = open(file=filename, mode="r")
        jucatori = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            nume = params[0]
            prenume = params[1]
            inaltime = random.randint(141, 200)
            post = random.choice(posturi)
            jucator = Jucator(nume, prenume, inaltime, post)
            jucatori.append(jucator)
        f.close()
        return jucatori

    def import_repository2(self, filename):
        """
        date de intrare: nume fisier existent
        se citesc jucatorii din fisierul existent
        avem doua liste in care adaugam numele si prenumele jucatorilor care se repeta
        daca se repeta si nu au fost adaugate in listele precizate, se adauga primul jucator gasit de acest tip
        ceilalti ii ignoram si nu ii mai importam
        se returneaza numarul jucatorilor importati
        """
        jucatori = self.import_repository(filename)
        contor = 0

        ignored_nume = []
        ignored_prenume = []

        with open(file=filename, mode="w") as f:
            for jucator in jucatori:

                params = (jucator.get_nume(), jucator.get_prenume(), jucator.get_inaltime(), jucator.get_post())
                params = [str(param) for param in params]

                if (params[0] not in ignored_nume and params[1] not in ignored_prenume) and self.find(params[0], params[1], jucatori) > 1:
                    ignored_nume.append(params[0])
                    ignored_prenume.append(params[1])
                    contor = contor + 1
                    line = ", ".join(params) + "\n"
                    f.write(line)

                elif self.find(params[0], params[1], jucatori) == 1:
                    contor = contor + 1
                    line = ", ".join(params) + "\n"
                    f.write(line)

        return contor


def test_read_from_file():
    jucator = jucatorRepository("domain/teste_fisier.txt")
    jucatori = jucator.read_from_file()
    jucator.add_repository("popa", "alexia", "155", "extrema")
    updated = jucator.read_from_file()

    assert updated[-1].get_nume() == "popa"
    assert updated[-1].get_prenume() == "alexia"
    assert updated[-1].get_inaltime() == "155"
    assert updated[-1].get_post() == "extrema"

def test_write_to_file():
    jucator = jucatorRepository("domain/teste_fisier.txt")
    jucator.add_repository("mesesan", "timotei", "155", "fundas")
    updated = jucator.read_from_file()

    assert updated[-1].get_nume() == "mesesan"
    assert updated[-1].get_prenume() == "timotei"
    assert updated[-1].get_inaltime() == "155"
    assert updated[-1].get_post() == "fundas"

def test_add_repository():
    jucator = jucatorRepository("domain/teste_fisier.txt")
    jucator.add_repository("mesesan", "timotei", "155", "fundas")
    updated = jucator.read_from_file()

    assert updated[-1].get_nume() == "mesesan"
    assert updated[-1].get_prenume() == "timotei"
    assert updated[-1].get_inaltime() == "155"
    assert updated[-1].get_post() == "fundas"

def test_modify_repository():
    jucator = jucatorRepository("domain/teste_fisier.txt")
    jucator.add_repository("mesesan", "timotei", "155", "fundas")
    updated = jucator.read_from_file()
    jucator.modify_repository("3")
    updated2 = jucator.read_from_file()
    assert updated2[-1].get_inaltime() == "158"

test_read_from_file()
test_write_to_file()
test_add_repository()
test_modify_repository()
