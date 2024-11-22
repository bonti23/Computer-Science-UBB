from service.service import piesaService

class piesaController:
    def __init__(self, piesa: piesaService):
        self.__piesa = piesa

    def menu(self):
        print("TEATRU")
        print("Alegeti una dintre urmatoarele: ")
        print("1. Adauga piesa")
        print("2. Modifica piesa")
        print("3. Creeaza aleator piese de teatru")
        print("4. Exporta piese de teatru")
        print("5. Inchide aplicatia")

    def add_controller(self):
        titlu = input("Titlul: ")
        regizor = input("Regizorul: ")
        gen = input("Genul: ")
        durata = input("Durata: ")
        try:
            self.__piesa.add_service(titlu, regizor, gen, durata)
        except ValueError as e:
                print(e)

    def modify_controller(self):
        titlu = input("Titlul: ")
        regizor = input("Regizorul: ")
        gen = input("Genul: ")
        durata = input("Durata: ")
        new_gen = input("Genul nou: ")
        new_durata = input("Durata noua: ")
        try:
            self.__piesa.modify_service(titlu, regizor, gen, durata, new_gen, new_durata)
        except ValueError as e:
            print(e)

    def random_controller(self):
        numar = int(input("Numarul de piese generate: "))
        if numar >= 1:
            self.__piesa.random_service(numar)
        else:
            print("Numar invalid!")

    def export_controller(self):
        nume_fisier = input("Numele fisierului: ")
        self.__piesa.export_file_service(nume_fisier)

    def runner(self):
        self.menu()
        while True:
            choice = int(input("Introduceti o cifra de la 1 la 5: "))
            if choice == 1:
                self.add_controller()
            elif choice == 2:
                self.modify_controller()
            elif choice == 3:
                self.random_controller()
            elif choice == 4:
                self.export_controller()
            elif choice == 5:
                print("Program inchis!")
                break
            else:
                print("Invalid!")

