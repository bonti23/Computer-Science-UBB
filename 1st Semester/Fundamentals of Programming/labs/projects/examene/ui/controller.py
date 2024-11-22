from service.service import examenService

class Controller:
    def __init__(self, examen: examenService):
        self.__examen = examen

    def menu(self):
        print("EXAMENE")
        print("Alegeti o instructiune din meniu:")
        print("1. Afisati examenele planificate pentru ziua urmatoare.")
        print("2. Adauga examen.")
        print("3. Exporta examene.")
        print("4. Inchide aplicatia.")

    def next(self):
        examene = self.__examen.next_day2()
        for examen in examene:
            print(f"data: {examen.get_data()}, ora: {examen.get_ora()}, materia: {examen.get_materie()}, tip: {examen.get_tip()}")

    def add(self):
        data = input("Data: ")
        ora = input("Ora: ")
        materia = input("Materia: ")
        tip = input("Tipul: ")
        print("Erori: ", self.__examen.add2(data, ora, materia, tip))

    def set_date(self):
        date = input("Data: ")
        examene = self.__examen.interval(date)
        for examen in examene:
            print(f"data: {examen.get_data()}, ora: {examen.get_ora()}, materia: {examen.get_materie()}, tip: {examen.get_tip()}")

    def export(self):
        filename = input("Filename: ")
        sir = input("Sirul: ")
        self.__examen.export2(filename, sir)

    def runner(self):
        self.menu()
        date = input("Data pentru a treia functionalitate: ")
        while True:
            choice = input("Introduceti un numar de la 1 la 4: ")
            if choice == "1":
                self.next()
            elif choice == "2":
                self.add()
            elif choice == "3":
                self.export()
            elif choice == "4":
                print("inchis")
                break
            else:
                print("incorrect")
            examene = self.__examen.interval(date)
            print("\n")
            print("A TREIA FUNCTIONALITATE")
            for examen in examene:
                print(f"data: {examen.get_data()}, ora: {examen.get_ora()}, materia: {examen.get_materie()}, tip: {examen.get_tip()}")
            print("\n")
