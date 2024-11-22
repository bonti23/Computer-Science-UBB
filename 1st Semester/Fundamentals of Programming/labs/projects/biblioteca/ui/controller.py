from service.service import carteService

class carteController:
    def __init__(self, carte: carteService):
        self.__carte = carte
        self.__sir = ""
        self.__numar = -1

    def menu(self):
        print("BIBLIOTECA")
        print("Alegeti varianta dorita:")
        print("1. Adauga o carte.")
        print("2. Sterge o carte.")
        print("3. Seteaza filtrul")
        print("4. Undo.")
        print("5. Inchide aplicatia.")

    def add_controller(self):
        id = input("Id-ul: ")
        titlu = input("Titlul: ")
        autor = input("Autorul: ")
        anAparitie = input("Anul aparitiei: ")
        try:
            self.__carte.add_service(id, titlu, autor, anAparitie)
        except ValueError as e:
            print(e)

    def delete_controller(self):
        cifra = int(input("Cifra: "))
        self.__carte.delete_service(cifra)

    def filter_controller(self):
        self.__sir = input("Sirul de caractere: ")
        self.__numar = int(input("Anul: "))
        lista = self.__carte.filter_service(self.__sir, self.__numar)
        for element in lista:
            print(element)

    def undo_controller(self):
        self.__carte.undo_service()

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti un numar de la 1 la 5: ")
            if choice == "1":
                self.add_controller()
            elif choice == "2":
                self.delete_controller()
            elif choice == "3":
                self.filter_controller()
            elif choice == "4":
                self.undo_controller()
            elif choice == "5":
                print("Aplicatie inchisa!")
                break
            else:
                print("Incorrect")
