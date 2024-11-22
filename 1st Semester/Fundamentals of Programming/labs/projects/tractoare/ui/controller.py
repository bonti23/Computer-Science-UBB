from service.service import tractorService

class Controller:
    def __init__(self, tractor: tractorService):
        self.__tractor = tractor
        self.__sir = ""
        self.__numar = -1

    def menu(self):
        print("TRACTOARE")
        print("Alegeti o instructiune din meniu: ")
        print("1. Adaugare.")
        print("2. Stergere.")
        print("3. Filtrare.")
        print("4. Undo")
        print("5. Inchide aplicatia.")

    def adaugare(self):
        id = input("Id-ul: ")
        denumire = input("Denumirea: ")
        pret = input("Pretul: ")
        model = input("Modelul: ")
        data = input("Data: ")
        print("Erori:", self.__tractor.add_service(id, denumire, pret, model, data))

    def stergere(self):
        cifra = input("Cifra: ")
        self.__tractor.delete_service(cifra)

    def filtrare(self):
        tractoare = self.__tractor.filtrare(self.__sir, self.__numar)
        for tractor in tractoare:
            print(f"{tractor.get_id()}, {tractor.get_denumire()}, {tractor.get_pret()}, {tractor.get_model()}, {tractor.get_data()}")

    def undo(self):
        self.__tractor.undo()

    def runner(self):
        self.menu()
        while True:
            choice = input("Instructiunea: ")
            if choice == "1":
                self.adaugare()
                self.filtrare()
            elif choice == "2":
                self.stergere()
                self.filtrare()
            elif choice == "3":
                self.__sir = input("Sirul: ")
                self.__numar = int(input("Numarul: "))
                self.filtrare()
            elif choice == "4":
                self.undo()
                self.filtrare()
            elif choice == "5":
                print("inchis")
                break
            else:
                print("invalid")
