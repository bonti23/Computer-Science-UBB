from service.service import jucatorService

class Controller:
    def __init__(self, jucator: jucatorService):
        self.__jucator = jucator

    def menu(self):
        print("ECHIPA DE BASKET")
        print("Alegeti o instructiune din meniu: ")
        print("1. Adauga jucator.")
        print("2. Modifica inaltimea jucatorilor.")
        print("3. Tipareste echipa noua.")
        print("4. Importa jucatori.")
        print("5. Inchide aplicatia.")

    def add_controller(self):
        nume = input("Numele: ")
        prenume = input("Prenumele: ")
        inaltime = input("Inaltimea: ")
        post = input("Postul: ")
        print("Errors:", self.__jucator.add_service(nume, prenume, inaltime, post))

    def modify_controller(self):
        numar = input("Numarul: ")
        self.__jucator.modify_service(numar)

    def tiparire_controller(self):
        echipa = self.__jucator.tiparire_service()
        print("Prenume      Nume        Post        Inaltime")
        for jucator in echipa:
            print(f" {jucator.get_prenume()}      {jucator.get_nume()}      {jucator.get_post()}      {jucator.get_inaltime()}")

    def import_controller(self):
        filename = input("Filename: ")
        contor = self.__jucator.import_service(filename)
        print("S-au adaugat",contor, "jucatori.")

    def runner(self):
        self.menu()
        while True:
            choice = input("Instructiunea dorita: ")
            if choice == "1":
                self.add_controller()
            elif choice == "2":
                self.modify_controller()
            elif choice == "3":
                self.tiparire_controller()
            elif choice == "4":
                self.import_controller()
            elif choice == "5":
                print("Aplicatie inchisa.")
                break
            else:
                print("Invalid")
