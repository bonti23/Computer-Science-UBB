from service.service import produsService

class produsController:
    def __init__(self, produs: produsService):
        self.__produs = produs
        self.__sir = ""
        self.__numar = -1

    def menu(self):
        print("MAGAZIN")
        print("Alegeti varianta dorinta:")
        print("1. Adauga produs.")
        print("2. Sterge produsele care contin o anumita cifra in id-ul lor.")
        print("3. Filtreaza produsele in functie de denumire si de pret.")
        print("4. Undo")
        print("5. Inchide")

    def add_controller(self):
        id = input("Id-ul: ")
        denumire = input("Denumirea: ")
        pret = input("Pretul: ")
        if self.__produs.add_service(id, denumire, pret) != None:
            print(self.__produs.add_service(id, denumire, pret))

    def delete_controller(self):
        cifra = input("Cifra: ")
        print("S-au sters",self.__produs.delete_service(cifra),"produse.")

    def filtrare_controller(self):
        self.__sir = input("Sirul nou: ")
        self.__numar = input("Numarul nou: ")
        produse = self.__produs.filtrare(self.__sir, self.__numar)
        for produs in produse:
            print(produs.get_id(), produs.get_denumire(), produs.get_pret())

    def undo_controller(self):
        self.__produs.undo()

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti instructiunea dorita: ")
            if choice == "1":
                self.add_controller()
            elif choice =="2":
                self.delete_controller()
            elif choice =="3":
                self.filtrare_controller()
            elif choice =="4":
                self.undo_controller()
            elif choice =="5":
                print("Aplicatie inchisa!")
                break
            else:
                print("Invalid!")
