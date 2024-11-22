from service.service import jucatorService

class jucatorController:
    def __init__(self, jucator: jucatorService):
        self.__jucator = jucator

    def menu(self):
        print("ECHIPA DE BASKET")
        print("Alegeti una dintre urmatoarele variante: ")
        print("1. Adauga jucator.")
        print("2. Modifica inaltimea unui jucator.")
        print("3. Creeaza o noua echipa.")
        print("4. Exporteaza jucatorii intr-un nou fisier.")
        print("5. Inchide aplicatia!")

    def add_controller(self):
        nume = input("Numele: ")
        prenume = input("Prenumele: ")
        inaltime = input("Inaltimea: ")
        post = input("Postul: ")
        print(self.__jucator.add_service(nume, prenume, inaltime, post))

    def modify_controller(self):
        nume = input("Numele: ")
        prenume = input("Prenumele: ")
        new_inaltime = input("Inaltimea noua: ")
        new_inaltime = int(new_inaltime)
        if new_inaltime <= 0:
            print("Inaltime invalida!")
        else:
            self.__jucator.modify_service(nume, prenume, new_inaltime)

    def filter_contractor(self):
        echipa = self.__jucator.filter_service()
        for jucator in echipa:
            print(f"prenume: {jucator.get_prenume()}, nume: {jucator.get_nume()}, inaltime: {jucator.get_inaltime()}, post: {jucator.get_post()}")

    def export_controller(self):
        filename = input("Filename: ")
        self.__jucator.export_service(filename)

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti un numar de la 1 la 5: ")
            if choice == "1":
                self.add_controller()
            elif choice == "2":
                self.modify_controller()
            elif choice == "3":
                self.filter_contractor()
            elif choice == "4":
                self.export_controller()
            elif choice == "5":
                print("Aplicatie inchisa!")
                break
            else:
                print("Invalid!")
