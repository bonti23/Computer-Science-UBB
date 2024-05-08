#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;


bool rel(TElem e1, TElem e2)
{
    if (e1 <= e2)
        return true;
    return false;
}


// Colectie
Colectie::Colectie()
{
    /*
    constructorul clasei colectie
    complexitate: theta(n)
    */

    cp = 1;
    len = 0;
    elem = new TElemCol[cp];
    urm = new int[cp];

    // completam vectorul de pozitii ca fiind liber
    for (int i = 0; i < cp - 1; i++)
    {
        urm[i] = i + 1;
    }
    urm[cp - 1] = -1;
    prim = -1;
    primLiber = 0;
}


int Colectie::aloca()
{
    /*
    marcheaza un nod din lista ca fiind ocupat
    complexitate: theta(1)
    */

    int i = primLiber;			 // primul nod liber va fi ocupat
    primLiber = urm[primLiber];  // primulLiber va fi urmatorul nod
    return i;
}


void Colectie::dealoca(int i)
{
    /*
    marcheaza un nod din lista ca fiind liber
    complexitate: theta(1)
    */

    urm[i] = primLiber;  // actualul primLiber se muta pe poz urmatoare
    primLiber = i;		 // actualizam primLiber
}


int Colectie::creeazaNod(TElem el)
{
    /*
    creeaza un nou nod in lista cu valoarea elementului dat
    complexitate: theta(1)
    */

    if (primLiber == -1)	// daca nu mai avem noduri libere
        redim();			// redimensionam vectorii

    int i = aloca();		// alocam spatiu pentru noul nod

    TElemCol el_nou{};
    el_nou.el = el;			// setam valoarea
    el_nou.frecv = 1;		// setam frecventa

    elem[i] = el_nou;		// punem elementul pe pozitia alocata
    urm[i] = -1;			// marcam nodul urmator ca fiind inexistent

    return i;				// returnam pozitia unde s-a creat nodul
}


void Colectie::redim()
{
    /*
    mareste capacitatea de stocare a vectorilor asociati implementarii colectiei
    complexitate: theta(n)
    */

    int cp_nou = cp * 2;	// dublam capacitatea
    TElemCol* elem_nou = new TElemCol[cp_nou];
    int* urm_nou = new int[cp_nou];

    // copiem toate elementele din vectorii actuali in cei noi
    for (int i = 0; i < cp; ++i)
    {
        elem_nou[i] = elem[i];
        urm_nou[i] = urm[i];
    }

    // marcam toate pozitiile noi adaugate ca facand
    // parte din vectorul de pozitii libere fiind consecutive
    for (int i = cp; i < cp_nou - 1; ++i)
    {
        urm_nou[i] = i + 1;
    }
    urm_nou[cp_nou - 1] = -1;  // ultima pozitie nu are niciun succesor

    this->primLiber = cp;	   // setam primLiber la primul element nou dupa redim

    delete[] elem;
    delete[] urm;

    // actualizam proprietatile colectiei
    this->elem = elem_nou;
    this->urm = urm_nou;
    this->cp = cp_nou;
}


void Colectie::adauga(TElem e)
{
    /*
    adaugam un nou element in colectie colectie
    complexitate: O(n)
    */

    this->len++;

    int anterior = -1;
    int curent = prim;

    // parcurgem lista de noduri a colectiei cat timp
    // elementele impreuna cu cel dat respecta relatia impusa
    while (curent != -1 && rel(elem[curent].el, e))
    {
        if (elem[curent].el == e)  // daca elementul mai exista deja in lista
        {						   // incrementam nr de aparitii al acestuia
            elem[curent].frecv++;
            return;
        }

        // trecem la urmatorul nod
        anterior = curent;
        curent = urm[curent];
    }

    // elementul nu apare deloc in colectie
    // si va trebui creat un nou nod pt acesta
    int i = creeazaNod(e);


    if (anterior == -1)  // adaugam inainte de primul element
    {
        urm[i] = prim;
        prim = i;
        return;
    }


    if (curent == -1)  // adaugam dupa ultimul element
    {
        urm[anterior] = i;
        urm[i] = -1;
        return;
    }


    // adaugam intre elementul anterior si cel curent
    urm[i] = curent;
    urm[anterior] = i;
}


bool Colectie::sterge(TElem e)
{
    /*
    sterge un element din colectie
    complexitate: O(n)
    */

    int anterior = -1;
    int curent = prim;

    // parcurgem lista de noduri a colectiei
    while (curent != -1 && rel(elem[curent].el, e))
    {
        if (e == elem[curent].el)  // daca gasim un nod cu element egal cu cel dat
        {
            if (elem[curent].frecv > 1)  // daca elementul apare de mai multe ori in colectie
            {
                elem[curent].frecv--;  // stergem o aparitie a acestuia
            }
            else  // elementul apare o singura data in colectie => stergem nodul
            {
                if (anterior == -1)  // daca nodul este primul din colectie
                {
                    prim = urm[curent];  // setam prim la urmatorul nod
                    dealoca(curent);	 // dealocam spatiul pt nodul curent
                }
                else
                {
                    urm[anterior] = urm[curent];  // eliminam din lista nodul curent (sarim peste el)
                    dealoca(curent);			  // dealocam spatiul pentru nodul curent
                }
            }

            this->len--;  // decrementam nr de elem al colectiei
            return true;  // elementul s-a sters cu succes
        }

        // trecem la urmatorul nod
        anterior = curent;
        curent = urm[curent];
    }
    return false;  // elementul nu exista in lista
}


int Colectie::eliminaAparitii(int nr, TElem el)
{
    /*
    sterge nr aparitii element din colectie
    complexitate: O(n)
    */

    if (nr <= 0)
        throw std::exception();

    int anterior = -1;
    int curent = prim;

    // parcurgem lista de noduri a colectiei
    while (curent != -1 && rel(elem[curent].el, el))
    {
        if (el == elem[curent].el)  // daca gasim un nod cu element egal cu cel dat
        {
            if (elem[curent].frecv > nr)  // daca elementul apare de mai multe ori in colectie
            {
                elem[curent].frecv -= nr;
                this->len -= nr;
                return nr;
            }
            else  // elementul apare o singura data in colectie => stergem nodul
            {	  // elem[curent].frecv <= nr
                int rez = elem[curent].frecv;

                if (anterior == -1)  // daca nodul este primul din colectie
                {
                    prim = urm[curent];  // setam prim la urmatorul nod
                    dealoca(curent);	 // dealocam spatiul pt nodul curent
                }
                else
                {
                    urm[anterior] = urm[curent];  // eliminam din lista nodul curent (sarim peste el)
                    dealoca(curent);			  // dealocam spatiul pentru nodul curent
                }

                this->len -= rez;
                return rez;
            }
        }

        // trecem la urmatorul nod
        anterior = curent;
        curent = urm[curent];
    }
    return 0;  // elementul nu exista in lista
}


bool Colectie::cauta(TElem elem) const
{
    /*
    determina daca un element se afla in colectie sau nu
    complexitate: O(n)
    */

    // parcurgem lista de noduri a colectiei
    int p = prim;
    while (p != -1 && rel(this->elem[p].el, elem))
    {
        if (this->elem[p].el == elem) // daca am gasit un nod a carui element este
            return true;			  // egal cu cel dat returnam adevarat
        p = urm[p];		// trecem la urmatorul nod
    }
    return false; // daca am parcurs colectia si nu am gasit elementul returnam false
}


int Colectie::nrAparitii(TElem elem) const
{
    /*
     returneaza numarul de aparitii unui element dat
     complexitate: O(n)
     */

    // parcurgem lista de noduri a colectiei
    int p = prim;
    while (p != -1 && rel(this->elem[p].el, elem))
    {
        if (this->elem[p].el == elem)	  // daca am gasit un nod a carui element este egal cu cel dat
            return this->elem[p].frecv;   // returnam frecventa elementului respectiv
        p = urm[p];		// trecem la urmatorul nod
    }
    return 0;  // daca elementul dat nu se gaseste in lista returnam 0
}


int Colectie::dim() const
{
    /*
     returneaza dimensiunea colectiei
     complexitate: theta(1)
     */
    return len;
}


bool Colectie::vida() const
{
    /*
     verifica daca colectia este vida (nu contine niciun element)
     complexitate: theta(1)
     */
    if (prim == -1)
        return true;
    return false;
}

IteratorColectie Colectie::iterator() const
{
    /*
     creeaza un iterator pentru colectia curenta
     returneaza un iterator ce indica spre colectia curenta
     complexitate: theta(1)
     */
    return IteratorColectie(*this);
}


Colectie::~Colectie()
{
    /*
     destructorul clasei Colectie
     complexitate: theta(1)
     */

    delete[] elem;	// dealocam spatiu vector elemente
    delete[] urm;	// dealocam spatiu vector pozitii urmatoare
}

int Colectie::transinMultime() {
    /*
     functie pentru returnarea numarului de eliminari ale unui element
     complexitate: theta(n)
     */
    int eliminari = 0;

    // parcurgem toate elementele din colectie
    IteratorColectie it = iterator();
    while (it.valid()) {
        TElem elem = it.element();
        int nrAparitiiElem = nrAparitii(elem);
        // daca un element apare de mai multe ori => eliminam
        if (nrAparitiiElem > 1) {
            int eliminariCurente = nrAparitiiElem - 1;
            eliminaAparitii(eliminariCurente, elem);
            eliminari += eliminariCurente;
        }
        it.urmator();
    }
    return eliminari;
}


