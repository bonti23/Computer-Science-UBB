#include "IteratorColectie.h"
#include "Colectie.h"
#include <exception>


IteratorColectie::IteratorColectie(const Colectie& c) : col(c)
{
    /*
    constructorul pentru iteratorul unei colectii
    complexitate: theta(1)
    */

    curent = col.prim;
    aparitie = 1;
}


TElem IteratorColectie::element() const
{
    /*
    returneaza valoarea elementului din container referit de iterator
    complexitate: theta(1)
    */

    if (!valid())
        throw std::exception();

    // returnam elementul din nodul referit de iterator la momentul actual
    return col.elem[curent].el;
}


bool IteratorColectie::valid() const
{
    /*
    verifica daca iteratorul e valid (indica un element al containerului)
    complexitate: theta(1)
    */

    // daca elementul curent este -1 atunci
    // am terminat de parcurs elementele colectiei
    return curent != -1;
}


void IteratorColectie::urmator()
{
    /*
    muta iteratorul in container
    complexitate: theta(1)
    */

    if (!valid())
        throw std::exception();

    if (aparitie < col.elem[curent].frecv)  // verificam daca am parcurs toate instantele
    {									    // unui element in colectie
        aparitie++;  // daca nu trecem la urmatoarea aparitie a elementului
    }
    else  // altfel
    {
        curent = col.urm[curent];    // trecem la urmatorul nod din lista colectiei
        aparitie = 1;				 // resetam contorul pentru nr de aparitii
    }
}


void IteratorColectie::prim()
{
    /*
    reseteaza pozitia iteratorului la inceputul containerului
    complexitate: theta(1)
    */

    curent = col.prim;
    aparitie = 1;
}