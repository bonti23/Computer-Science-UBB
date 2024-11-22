from repository.repository import tractorRepository
from domain.validator import Validator
from datetime import *
class tractorService:
    def __init__(self, tractor: tractorRepository):
        self.__tractor = tractor
        self.__anterior = []

    def add_service(self, id, denumire, pret, model, data):
        validator = Validator(self.__tractor)
        tractoare = self.__tractor.read_from_file()
        copy = tractoare [:]
        self.__anterior.append(copy)
        if validator.validate(id, denumire, pret, model, data) == True:
            self.__tractor.add_repository(id, denumire, pret, model, data)
        else:
            return validator.validate(id, denumire, pret, model, data)


    def delete_service(self, cifra):
        tractoare = self.__tractor.read_from_file()
        copy = tractoare[:]
        self.__anterior.append(copy)
        return self.__tractor.delete_repository(cifra)

    def filtrare(self, sir, numar):
        tractoare = self.__tractor.read_from_file()
        lista_filtrata = []
        if sir == "" and numar == -1:#nimic
            lista_filtrata = tractoare
        elif sir == "" and numar != -1:#care au sirul in denumire
            lista_filtrata = [tractor for tractor in tractoare if sir in tractor.get_denumire()]
        elif sir != "" and numar == -1:#care au pretul < numar
            lista_filtrata = [tractor for tractor in tractoare if int(tractor.get_pret())<numar]
        elif sir != "" and numar != -1:#tot
            lista_filtrata = [tractor for tractor in tractoare if int(tractor.get_pret()) < numar and sir in tractor.get_denumire()]
        #expirare
        today = datetime.today().strftime("%d.%m.%Y")
        for tractor in lista_filtrata:
            denumire = []
            denumire.append("*")
            data = tractor.get_data()
            data = datetime.strptime(data, "%d.%m.%Y")
            if str(data) <= str(today):
                denumire.append(tractor.get_denumire())
                text = "".join(denumire)
                tractor.set_denumire(text)
        return lista_filtrata

    def undo(self):
        if len(self.__anterior) > 0:
            inainte = self.__anterior.pop()
            self.__tractor.write_to_file(inainte)
