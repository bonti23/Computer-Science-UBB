from domain.entity import Carte

class carteRepository:
    def __init__(self, filename):
        self.__filename = filename
        self.__anterior = []

    def read_from_file(self):
        f = open(file = self.__filename, mode = "r")
        carti = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            id = params[0]
            titlu = params[1]
            autor = params[2]
            anAparitie = params[3]
            carte = Carte(id, titlu, autor, anAparitie)
            carti.append(carte)
        f.close()
        return carti

    def write_to_file(self, carti):
        with open(file = self.__filename, mode = "w") as f:
            for carte in carti:
                params = (carte.get_id(), carte.get_titlu(), carte.get_autor(), carte.get_anAparitie())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def find_id(self, id):
        carti = self.read_from_file()
        for carte in carti:
            if carte.get_id() == id:
                return carte
        return None

    def add_repository(self, id, titul, autor, anAparitie):
        carti = self.read_from_file()
        copy = carti[:]
        carte = Carte(id, titul, autor, anAparitie)
        carti.append(carte)
        self.write_to_file(carti)
        self.__anterior.append(copy)

    def delete_repository(self, cifra):
        carti = self.read_from_file()
        copy = carti[:]
        removes = []
        for i, carte in enumerate(carti):
            ok = False
            an = int(carte.get_anAparitie())
            while an != 0 and ok == False:
                uc = an % 10
                if uc == cifra:
                    ok = True
                an = an // 10
            if ok == True:
                removes.append(i)
        for index in reversed(removes):
            carti.pop(index)
        self.write_to_file(carti)
        self.__anterior.append(copy)

    def filter_repository(self, sir, numar):
        carti = self.read_from_file()
        lista_filtrata = []
        if sir == "" and numar == -1:
            lista_filtrata = carti
        elif sir != "" and numar == -1: #au sirul in titlu
            lista_filtrata = [carte for carte in carti if sir in carte.get_titlu()]
        elif sir == "" and numar != -1: #au anul mai mic decat numarul
            lista_filtrata = [carte for carte in carti if int(numar) > int(carte.get_anAparitie())]
        elif sir != "" and numar != -1:#ambele
            lista_filtrata = [carte for carte in carti if int(numar) > int(carte.get_anAparitie()) and sir in carte.get_titlu()]
        return lista_filtrata

    def undo(self):
        if self.__anterior:
            inainte = self.__anterior.pop()
            self.write_to_file(inainte)
        else:
            return None
