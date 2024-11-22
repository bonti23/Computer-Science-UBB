from domain.entity import Produs

class produsRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = 'r')
        produse = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            id = params[0]
            denumire = params[1]
            pret = params[2]
            produs = Produs(id, denumire, pret)
            produse.append(produs)
        f.close()
        return produse

    def write_to_file(self, produse):
        with open(file = self.__filename, mode = 'w') as f:
            for produs in produse:
                params = (produs.get_id(), produs.get_denumire(), produs.get_pret())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def add_repository(self, id, denumire, pret):
        produse = self.read_from_file()
        produs = Produs(id, denumire, pret)
        produse.append(produs)
        self.write_to_file(produse)

    def delete_repository(self, cifra):
        produse = self.read_from_file()
        removes = []
        for i, produs in enumerate(produse):
            cifra = int(cifra)
            ok = False
            n = int(produs.get_id())
            while n != 0 and ok == False:
                uc = n % 10
                if uc == cifra:
                    ok = True
                n = n // 10
            if ok == True:
                removes.append(i)
        k = len(removes)
        for index in reversed(removes):
            produse.pop(index)
        self.write_to_file(produse)
        return k

    def find_id(self, id):
        produse = self.read_from_file()
        for produs in produse:
            if produs.get_id() == id:
                return produs
        return None
