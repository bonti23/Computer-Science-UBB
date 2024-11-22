from domain.entity import Tractor

class tractorRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = "r")
        tractoare = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            id = params[0]
            denumire = params[1]
            pret = params[2]
            model = params[3]
            data = params[4]
            tractor = Tractor(id, denumire, pret, model, data)
            tractoare.append(tractor)
        f.close()
        return tractoare

    def write_to_file(self, tractoare):
        with open(file = self.__filename, mode = "w") as f:
            for tractor in tractoare:
                params = (tractor.get_id(), tractor.get_denumire(), tractor.get_pret(), tractor.get_model(), tractor.get_data())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def add_repository(self, id, denumire, pret, model, data):
        tractoare = self.read_from_file()
        tractor = Tractor(id, denumire, pret, model, data)
        tractoare.append(tractor)
        self.write_to_file(tractoare)

    def delete_repository(self, cifra):
        tractoare = self.read_from_file()
        removes = []
        cifra = int(cifra)
        for i, tractor in enumerate(tractoare):
            numar = int(tractor.get_pret())
            ok = False
            while numar != 0 and ok == False:
                uc = numar % 10
                if uc == cifra:
                    ok = True
                numar = numar // 10
            if ok == True:
                removes.append(i)
        for index in reversed(removes):
            tractoare.pop(index)
        self.write_to_file(tractoare)

    def find_id(self, id):
        tractoare = self.read_from_file()
        for tractor in tractoare:
            if tractor.get_id() == id:
                return tractor
        return None
