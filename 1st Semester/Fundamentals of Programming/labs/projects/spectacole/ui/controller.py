from service.service import spectacolService

class spectacolController:
    def __init__(self, spectacol: spectacolService):
        self.__spectacol = spectacol

    def menu(self):
        print("SPECTACOLE")
        print("Introduceti o anumita comanda:")
        print("1. Adauga spectacol.")
        print("2. Modifica un spectacol.")
        print("3. Genereaza random.")
        print("4. Exporta.")
        print("5. Inchide aplicatia.")

    def adaugare(self):
        titlu = input("Titlul: ")
        artist = input("Artistul: ")
        gen = input("Genul: ")
        durata = input("Durata: ")
        print("Erori:", self.__spectacol.adaugare2(titlu, artist, gen, durata))

    def modificare(self):
        titlu = input("Titlul: ")
        artist = input("Artistul: ")
        gen_nou = input("Genul nou: ")
        durata_noua = input("Durata noua: ")
        print("Erori:", self.__spectacol.modificare2(titlu, artist, gen_nou, durata_noua))

    def genereaza(self):
        numar = int(input("Numarul de spectacole: "))
        if numar <= 0:
            print("Numarul de spectacole trebuie sa fie strict pozitiv!")
        else:
            self.__spectacol.random2(numar)

    def exporta(self):
        filename = input("Filename: ")
        self.__spectacol.export2(filename)

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti o cifra de la 1 la 5: ")
            if choice == "1":
                self.adaugare()
            elif choice == "2":
                self.modificare()
            elif choice == "3":
                self.genereaza()
            elif choice == "4":
                self.exporta()
            elif choice == "5":
                print("Inchis!")
                break
            else:
                print("invalid!")
