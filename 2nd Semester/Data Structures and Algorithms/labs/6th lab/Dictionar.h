#pragma once
#include <utility>
#include <climits>

using std::pair;

typedef int TCheie;
typedef int TValoare;
typedef pair<TCheie, TValoare> TElem;

#define NULL_TVALOARE INT_MIN
#define NULL_TELEM TElem(-1,-1)
#define MAX 11

class IteratorDictionar;

class Dictionar {
    friend class IteratorDictionar;

private:
    int cap;	// Capacitatea
    int n;		// Numarul de elemente din dictionar

    TElem* e;		// Vectorul care retine elementele dictionarului
    int* urm;		// Vectorul de legaturi
    bool* ocupat;	// Vectorul care retine daca o pozitie e ocupata
    int primLiber;	// Pozitia primului element liber din multime

    void actPrimLiber();		// Functia actualizeaza primul liber
    // O(n)				Best case:		Theta(1)	- daca pozitia de la primLiber + 1 este libera
    //					Worst case:		Theta(n)	- daca nu mai exista pozitii libere
    //					Average case:	Theta(n)
    void redim();				// Functia care redimensioneaza vectorii si redisperseaza elementele
    // Theta(n)			Best case = Worst case = Average case = Theta(cap)
    int dispersie(TCheie c);		// Functia care returneaza codul de dispersie al unei chei
    // Theta(1)			Best case = Worst case = Average case = Theta(1)

public:
    void afis(int start = 0, int final = 200);
    // Constructorul implicit al dictionarului
    Dictionar();					// Theta(1)			Best case = Worst case = Average case = Theta(1)

    // Adauga o pereche (cheie, valoare) in dictionar
    //		Daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
    //		Daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
    TValoare adauga(TCheie c, TValoare v);		// O(n)				Best case:		Theta(1)	- daca pozitia dispersata este libera
    //					Worst case:		Theta(n)
    //					Average case:	Theta(1)

    // Cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
    TValoare cauta(TCheie c);// O(n)				Best case:		Theta(1)	- daca cheia c se afla chiar pe pozitia dispersata
    //					Worst case:		Theta(n)	- daca cheia c nu exista
    //					Average case:	Theta(1)

    // Sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
    TValoare sterge(TCheie c);// O(n)				Best case:		Theta(1)	- daca cheia c se afla chiar pe pozitia dispersata
    //					Worst case:		Theta(n)
    //					Average case:	Theta(1)

    // Returneaza numarul de perechi (cheie, valoare) din dictionar
    int dim() const;// Theta(1)

    // Verifica daca dictionarul e vid
    bool vid() const;// Theta(1)

    // Se returneaza iterator pe dictionar
    IteratorDictionar iterator() const;	// Theta(1)


    // Destructorul dictionarului
    ~Dictionar();// Theta(1)

    int eliminaAparitiiValoare(int x, TValoare c);
};

