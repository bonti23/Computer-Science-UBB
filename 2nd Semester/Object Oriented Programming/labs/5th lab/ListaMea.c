#include "ListaMea.h"
#include <assert.h>
#include <string.h>
#include <stdlib.h>

ElemTypeList* creeazaVid(DestroyFct f)
{
    ElemTypeList* rez = malloc(sizeof(ElemTypeList));
    if (rez != NULL)
    {
        rez->lg = 0;
        rez->cap = 2;
        rez->elems = malloc(sizeof(ElemType) * rez->cap);
        rez->dfnc = f;
    }
    return rez;
}

void destroyList(ElemTypeList* l)
{
    for (int i = 0; i < l->lg; i++)
        l->dfnc(l->elems[i]);
    free(l->elems);
    free(l);
}

ElemType get(ElemTypeList* l, int poz)
{
    return l->elems[poz];
}

ElemType modifica(ElemTypeList* l, int poz, ElemType p)
{
    ElemType rez = l->elems[poz];
    l->elems[poz] = p;
    return rez;
}

int lungime(ElemTypeList* l)
{
    return l->lg;
}

void ensureCapacity(ElemTypeList* l)
{
    if (l->lg < l->cap) {
        return;
    }
    ElemType* nElems = malloc(sizeof(ElemType) * (l->cap + 2));
    for (int i = 0; i < l->lg; i++) {
        nElems[i] = l->elems[i];
    }
    free(l->elems);
    l->elems = nElems;
    l->cap += 2;
}

void adauga(ElemTypeList* l, ElemType el)
{
    ensureCapacity(l);
    l->elems[l->lg] = el;
    l->lg++;
}

ElemType sterge(ElemTypeList* l, int poz)
{
    ElemType rez = l->elems[poz];
    for (int i = poz; i < l->lg; i++)
        l->elems[i] = l->elems[i + 1];
    l->lg--;
    return rez;
}

ElemTypeList* copyList(ElemTypeList* l, CopyFct cf)
{
    ElemTypeList* rez = creeazaVid(l->dfnc);
    for (int i = 0; i < lungime(l); i++) {
        ElemType p = get(l, i);
        adauga(rez, cf(p));
    }
    return rez;
}

void testCreateList()
{
    // crearea unei liste vide

    ElemTypeList* testList = creeazaVid((DestroyFct) destroyCheltuiala);
    assert (testList->lg == 0);
    assert (testList->cap == 2);
    assert (testList->dfnc == destroyCheltuiala);
    assert (lungime(testList) == 0);
    destroyList(testList);
}

void testAddModifyDelete()
{
    // crearea unei liste vide
    ElemTypeList* testList = creeazaVid((DestroyFct) destroyCheltuiala);
    Cheltuieli* testCheltuiala;

    // adaugarea a trei elemente in lista
    adauga(testList, createCheltuiala(20, 200, "mancare"));
    adauga(testList, createCheltuiala(15, 300, "mancare"));
    adauga(testList, createCheltuiala(31, 400, "mancare"));
    assert(lungime(testList) == 3);

    //modificarea unui element din lista
    testCheltuiala = modifica(testList, 0, createCheltuiala(30, 500, "mancare"));
    destroyCheltuiala(testCheltuiala);
    testCheltuiala = get(testList, 0);
    assert(testCheltuiala->suma == 500);

    // stergerea unui element din lista
    testCheltuiala = sterge(testList, 2);
    destroyCheltuiala(testCheltuiala);
    assert(lungime(testList) == 2);

    // distrugerea listei
    destroyList(testList);
}

void testCopyList()
{
    // crearea unei liste vide
    ElemTypeList* testList = creeazaVid((DestroyFct) destroyCheltuiala);

    // adaugarea a doua elemente in lista
    adauga(testList, createCheltuiala(20, 200, "mancare"));
    adauga(testList, createCheltuiala(15, 300, "transport"));

    // copierea listei
    ElemTypeList* List = copyList(testList, (CopyFct) copyCheltuiala);
    assert(lungime(List) == 2);

    // verificarea ca s-a efectuat corect copierea
    Cheltuieli* testCheltuiala = get(List, 0);
    assert(strcmp(testCheltuiala->tip, "mancare") == 0);

    // distrugerea listelor
    destroyList(testList);
    destroyList(List);
}

void testListofLists()
{
    // verificarea faptului ca implementarea functioneaza si pentru o lista de liste
    ElemTypeList* listOfLists = creeazaVid((DestroyFct) destroyList);
    ElemTypeList* listOfCheltuieli = creeazaVid((DestroyFct) destroyCheltuiala);

    // adaugarea a doua elemente in lista de cheltuieli
    adauga(listOfCheltuieli, createCheltuiala(20, 200, "mancare"));
    adauga(listOfCheltuieli, createCheltuiala(15, 300, "transport"));

    // adaugarea listei de cheltuieli la lista de lsite
    adauga(listOfLists, listOfCheltuieli);
    assert(lungime(listOfCheltuieli) == 2);
    assert(lungime(listOfLists) == 1);

    // verificarea ca s-a efectuat cu succes adaugarea
    ElemTypeList* testList = get(listOfLists, 0);
    assert(lungime(testList) == 2);

    // distrugerea listei de liste (implicit si a listei de cheltuieli)
    destroyList(listOfLists);
}

void testElemTypeList()
{
    // apelam toate testele
    testCreateList();
    testAddModifyDelete();
    testCopyList();
    testListofLists();
}
