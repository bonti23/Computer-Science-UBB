from service.service import evenimentService

class evenimentController:
    def __init__(self, eveniment: evenimentService):
        self.__eveniment = eveniment

    def menu(self):
        print("EVENIMENTE")
        print("Alegeti una dintre variantele urmatoare:")
        print("1. Afiseaza evenimentele din ziua curenta.")
        print("2. Adauga eveniment.")
        print("3. Afiseaza evenimentele dintr-o anumita data.")
        print("4. Exporta.")
        print("5. Inchide aplicatia.")

    def zi_curenta3(self):
        evenimente = self.__eveniment.zi_curenta2()
        if evenimente != None:
            for eveniment in evenimente:
                print(f"data: {eveniment.get_data()}, ora: {eveniment.get_ora()}, descrierea: {eveniment.get_descriere()}")
        else:
            print("Nu exista evenimente in ziua curenta!")

    def add(self):
        data = input("Data: ")
        ora = input("Ora: ")
        descriere = input("Descrierea: ")
        print(self.__eveniment.add_service(data,ora,descriere))

    def anumita_data(self):
        data = input("Data: ")
        if self.__eveniment.data_stabilita2(data) != None:
            evenimente = self.__eveniment.data_stabilita2(data)
            for eveniment in evenimente:
                print(f" data: {eveniment.get_data()}, ora: {eveniment.get_ora()}, descrierea: {eveniment.get_descriere()}")
        else:
            print("Nu exista evenimente in aceasta data!")

    def export(self):
        filename = input("Filename: ")
        sir = input("Sirul: ")
        self.__eveniment.export2(filename, sir)

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti un numar de la 1 la 5: ")
            if choice == "1":
                self.zi_curenta3()
            elif choice == "2":
                self.add()
            elif choice == "3":
                self.anumita_data()
            elif choice == "4":
                self.export()
            elif choice == "5":
                print("inchis")
                break
            else:
                print("invalid")
