from service.service import emisiuneService

class emisiuneController:
    def __init__(self, emisiune: emisiuneService):
        self.__emisiune = emisiune
        self.__blocari = []

    def main(self):
        print("EMISIUNI")
        print("Alegeti una dintre urmatoarele: ")
        print("1. Stergere emisiune")
        print("2. Actualizare emisiune")
        print("3. Afisare random")
        print("4. Blocare tip")
        print("5. Inchidere")

    def stergere(self):
        nume = input("Numele: ")
        tip = input("Tipul: ")
        if tip not in self.__blocari:
            self.__emisiune.stergere_service(nume, tip)
        else:
            print("emisiune blocata!")

    def actualizare(self):
        nume = input("Numele: ")
        tip = input("Tipul: ")
        durata = input("Durata: ")
        descriere = input("Descrierea: ")
        durata_noua = input("Noua durata: ")
        descriere_noua = input("Noua descriere: ")
        if tip not in self.__blocari:
            self.__emisiune.actualizare_service(nume, tip, durata, descriere, durata_noua, descriere_noua)
        else:
            print("emisiune blocata!")

    def random(self):
        ora_inceput = input("Ora inceput: ")
        ora_sfarsit = input("Ora sfarsit: ")
        print("Ora        Nume        Tip        Descriere")
        list = self.__emisiune.tiparire_program(ora_inceput, ora_sfarsit)
        for i in range(len(list)):
            print(list[i][0], "        ", list[i][1], "        ", list[i][2], "        ", list[i][3])

    def blocare(self):
        tip = input("Tipul blocat: ")
        if tip=="stiri" or tip=="informare" or tip=="film":
            self.__blocari.append(tip)
        else:
            raise ValueError("Tip invalid!")

    def runner(self):
        self.main()
        while True:
            choice = int(input("Introduceti un numar de la 1 la 5: "))
            if choice == 1:
                self.stergere()
            elif choice == 2:
                self.actualizare()
            elif choice == 3:
                self.random()
            elif choice == 4:
                self.blocare()
            elif choice == 5:
                print("Program inchis.")
                break
            else:
                print("Invalid!")

