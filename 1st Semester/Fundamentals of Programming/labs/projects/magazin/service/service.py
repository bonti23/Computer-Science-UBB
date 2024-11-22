from repository.repository import produsRepository
from domain.validator import produsValidator

class produsService:
    def __init__(self, produs: produsRepository):
        self.__produs = produs
        self.__anterior = []

    def add_service(self, id, denumire, pret):
        ok = True
        validator = produsValidator(self.__produs)
        if validator.validate_produs(id, denumire, pret) != True:
            ok = False
            return validator.validate_produs(id, denumire, pret)

        if ok == True:
            produse = self.__produs.read_from_file()
            lista = produse[:]
            self.__anterior.append(lista)

            self.__produs.add_repository(id, denumire, pret)
            return None

    def delete_service(self, cifra):
        produse = self.__produs.read_from_file()
        lista = produse[:]
        self.__anterior.append(lista)

        return self.__produs.delete_repository(cifra)

    def filtrare(self, sir, numar):
        produse = self.__produs.read_from_file()
        lista_filtrata = []
        if sir == "" and int(numar) == -1:
            lista_filtrata.append(produse)
        elif sir != "" and int(numar) != -1:
            lista_filtrata = [produs for produs in produse if produs.get_pret() < numar and sir in produs.get_denumire()]
        elif sir == "" and int(numar) != -1:
            lista_filtrata = [produs for produs in produse if sir in produs.get_denumire()]
        elif sir != "" and int(numar) == -1:
            lista_filtrata = [produs for produs in produse if produs.get_pret() < numar]
        return lista_filtrata

    def undo(self):
        if len(self.__anterior) > 0:
            inainte = self.__anterior.pop()
            self.__produs.write_to_file(inainte)
