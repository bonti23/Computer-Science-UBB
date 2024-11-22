from service.service import melodieService

class melodiesController:
    def __init__(self, melodie: melodieService):
        self.__melodie = melodie

    def menu(self):
        print("MUZICA!")
        print("Alegeti una dintre variantele urmatoare: ")
        print("1. Modificati o melodie.")
        print("2. Generati random un anumit numar de melodii.")
        print("3. Exportati intr-un nou fisier melodiile din vechiul fisier.")
        print("4. Inchideti aplicatia")

    def modify_controller(self):
        titlu = input("Titlul: ")
        artist = input("Artistul: ")
        gen = input("Genul: ")
        durata = input("Durata: ")
        new_gen = input("Genul nou: ")
        new_durata = input("Durata noua: ")
        try:
            print(self.__melodie.modify_service(titlu, artist, gen, durata, new_gen, new_durata))
        except ValueError as e:
            print(e)

    def random_controller(self):
        numar = int(input("Numarul de melodii pe care doriti sa le generati: "))
        artisti = []
        titluri = []

        print("Introduceti o lista de titluri. Daca doriti sa nu mai introduceti scrieti: 'atat'. Titlurile: ")
        text = input("titlu: ")
        while text != "atat":
            titluri.append(text)
            text = input("titlu: ")

        print("Introduceti o lista de artisti. Daca doriti sa nu mai introduceti scrieti: 'atat'. Artistii: ")
        text = input("artist: ")
        while text != "atat":
            artisti.append(text)
            text = input("artist: ")

        print("S-au generat ", self.__melodie.random_service(numar, titluri, artisti), " melodii.")

    def export_service(self):
        filename = input("Introduceti un nume pentru nou fisier: ")
        self.__melodie.export_service(filename)

    def runner(self):
        self.menu()
        while True:
            choice = input("Introduceti un numar de la 1 la 4: ")
            if choice == "1":
                self.modify_controller()
            elif choice == "2":
                self.random_controller()
            elif choice == "3":
                self.export_service()
            elif choice == "4":
                print("Aplicatie inchisa!")
                break
            else:
                print("Invalid!")
