from service.service import contactService

class Controller:
    def __init__(self, contact: contactService):
        self.__contact = contact

    def menu(self):
        print("AGENDA DE TELEFON")
        print("Alegeti una dintre variantele urmatoare:")
        print("1. Adauga contact.")
        print("2. Cauta numarul de telefon pentru un anumit contact.")
        print("3. Tipareste contactele pentru un anumit grup.")
        print("4. Exporta contactele pentru un anumit grup intr-un nou fisier.")
        print("5. Inchide aplicatia.")

    def add_controller(self):
        id = input("Id-ul: ")
        nume = input("Numele: ")
        telefon = input("Telefonul: ")
        grup = input("Grupul: ")
        print("Erori:",self.__contact.add_service(id, nume, telefon, grup))

    def find_controller(self):
        nume = input("Numele: ")
        try:
            contact = self.__contact.find_service(nume)
            print(f"id: {contact.get_id()}, nume: {contact.get_nume()}, telefon: {contact.get_telefon()}, grup: {contact.get_grup()}")
        except ValueError as e:
            print(e)

    def tiparire_controller(self):
        grup = input("Grupul: ")
        try:
            contacte = self.__contact.tiparire_service(grup)
            for contact in contacte:
                print(f"id: {contact.get_id()}, nume: {contact.get_nume()}, telefon: {contact.get_telefon()}, grup: {contact.get_grup()}")
        except ValueError as e:
            print(e)

    def export_controller(self):
        grup = input("Grupul: ")
        filename = input("Filename: ")
        self.__contact.export_service(grup, filename)

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti un numar de la 1 la 5: ")
            if choice == "1":
                self.add_controller()
            elif choice == "2":
                self.find_controller()
            elif choice == "3":
                self.tiparire_controller()
            elif choice == "4":
                self.export_controller()
            elif choice == "5":
                print("Aplicatie inchisa!")
                break
            else:
                print("Invalid!")
