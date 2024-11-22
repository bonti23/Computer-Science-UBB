#include "service.h"
#include <string.h>
#include <assert.h>

CheltuieliAndUndo createCheltuieliAndUndo()
{
    CheltuieliAndUndo rez;
    rez.allCheltuieli = creeazaVid((DestroyFct) destroyCheltuiala);
    rez.undoList = creeazaVid((DestroyFct) destroyList);
    return rez;
}

void addCheltuiala(CheltuieliAndUndo* lista, int ziua, int suma, char tip[])
{
    Cheltuieli* c = createCheltuiala(ziua, suma, tip);
    ElemTypeList* toUndo = copyList(lista->allCheltuieli, (CopyFct) copyCheltuiala);
    adauga(lista->allCheltuieli, c);
    adauga(lista->undoList, toUndo);
}

void modificaCheltuiala(CheltuieliAndUndo* lista, int poz, int ziua, int suma, char tip[])
{
    Cheltuieli* c = createCheltuiala(ziua, suma, tip);
    ElemTypeList* toUndo = copyList(lista->allCheltuieli, (CopyFct) copyCheltuiala);
    c = modifica(lista->allCheltuieli, poz, c);
    destroyCheltuiala(c);
    adauga(lista->undoList, toUndo);

}

void stergeCheltuiala(CheltuieliAndUndo* lista, int poz)
{
    ElemTypeList* toUndo = copyList(lista->allCheltuieli, (CopyFct) copyCheltuiala);
    Cheltuieli* c;
    c = sterge(lista->allCheltuieli, poz);
    destroyCheltuiala(c);
    adauga(lista->undoList, toUndo);
}

int undo(CheltuieliAndUndo* lista)
{
    if (lungime(lista->undoList) == 0)
        return 1;
    ElemTypeList* l = sterge(lista->undoList,lungime(lista->undoList)-1);
    destroyList(lista->allCheltuieli);
    lista->allCheltuieli = l;
    return 0;
}

ElemTypeList* filtersum(CheltuieliAndUndo* lista, int sum)
{
    ElemTypeList* rez = creeazaVid((DestroyFct) destroyCheltuiala);
    for (int i = 0; i < lungime(lista->allCheltuieli); i++)
    {
        Cheltuieli* p = get(lista->allCheltuieli, i);
        if (p->suma == sum)
            adauga(rez, copyCheltuiala(p));
    }
    return rez;
}

ElemTypeList* filterzi(CheltuieliAndUndo* lista, int zi)
{
    ElemTypeList* rez = creeazaVid((DestroyFct) destroyCheltuiala);
    for (int i = 0; i < lungime(lista->allCheltuieli); i++) {
        Cheltuieli* p = get(lista->allCheltuieli, i);
        if (p->ziua == zi)
            adauga(rez, copyCheltuiala(p));
    }
    return rez;
}

ElemTypeList* filtertip(CheltuieliAndUndo* lista, char tip[])
{
    ElemTypeList* rez = creeazaVid((DestroyFct) destroyCheltuiala);
    for (int i = 0; i < lungime(lista->allCheltuieli); i++) {
        Cheltuieli* p = get(lista->allCheltuieli, i);
        if (strcmp(p->tip, tip) == 0)
            adauga(rez, copyCheltuiala(p));
    }
    return rez;
}

ElemTypeList* filterzipara(CheltuieliAndUndo* lista)
{
    ElemTypeList* rez = creeazaVid((DestroyFct) destroyCheltuiala);
    for (int i = 0; i < lungime(lista->allCheltuieli); i++) {
        Cheltuieli* p = get(lista->allCheltuieli, i);
        if (p->ziua%2==0) {
            adauga(rez, copyCheltuiala(p));
        }
    }
    return rez;
}

int cmpType(Cheltuieli* c1, Cheltuieli* c2) {
    return strcmp(c1->tip, c2->tip);
}

void sort(ElemTypeList* l,int (*cmpF)(Cheltuieli* c1, Cheltuieli* c2), char mode)
{
    int i, j;
    for (i = 0; i < lungime(l); i++)
        for (j = i + 1; j < lungime(l); j++)
        {
            void* p1 = get(l, i);
            void* p2 = get(l, j);
            if (mode == 'c')
            {
                if (cmpF(p1, p2) > 0)
                {
                    modifica(l, i, p2);
                    modifica(l, j, p1);
                }
            }
            else
            if (cmpF(p1, p2) < 0)
            {
                modifica(l, i, p2);
                modifica(l, j, p1);
            }
        }
}

int cmpsuma(Cheltuieli* c1, Cheltuieli* c2)
{
    return c1->suma-c2->suma;
}

ElemTypeList* sortbyType(CheltuieliAndUndo* list, char mode)
{
    ElemTypeList* l = copyList(list->allCheltuieli, (CopyFct) copyCheltuiala);
    sort(l, cmpType, mode);
    return l;
}

ElemTypeList* sortbysum(CheltuieliAndUndo* list, char mode)
{
    ElemTypeList* l = copyList(list->allCheltuieli, (CopyFct) copyCheltuiala);
    sort(l, cmpsuma, mode);
    return l;
}

void destroyCheltuieliAndUndo(CheltuieliAndUndo* list)
{
    destroyList(list->allCheltuieli);
    destroyList(list->undoList);
}

void testAMDF()
{
    // crearea unei obiect care sa retina lista de cheltuieli si cea de undo
    CheltuieliAndUndo list = createCheltuieliAndUndo();
    Cheltuieli* c;

    // verificam ca la inceput nu exista operatie de undo
    assert(undo(&list) == 1);

    // adaugam trei cheltuieli
    addCheltuiala(&list, 20, 300, "mancare");
    addCheltuiala(&list, 15, 200, "transport");
    addCheltuiala(&list, 16, 230, "altele");

    // modificam o cheltuiala
    modificaCheltuiala(&list, 2, 17, 350, "transport");

    // ne asiguram ca modificarea a fost efectuata corect
    c = get(list.allCheltuieli, 2);
    assert(c->suma == 350);
    assert(c->ziua == 17);

    // stergem o cheltuiala
    stergeCheltuiala(&list, 2);

    // ne asiguram ca a fost efectuata corect stergerea
    assert(lungime(list.allCheltuieli) == 2);

    // refacem lista la starea de dinainte de stergere
    undo(&list);

    // ne asiguram ca s-a refacut
    assert(lungime(list.allCheltuieli) == 3);

    // filtram lista dupa suma 200
    ElemTypeList* filtered;
    filtered = filtersum(&list, 200);
    assert(lungime(filtered) == 1);
    destroyList(filtered);

    // filtram lista dupa ziua 20
    filtered = filterzi(&list, 20);
    assert(lungime(filtered) == 1);
    destroyList(filtered);

    // filtram lista dupa tipul mancare
    filtered = filtertip(&list, "mancare");
    assert(lungime(filtered) == 1);
    destroyList(filtered);

    // filtram lista dupa zile pare
    filtered = filterzipara(&list);
    assert(lungime(filtered) == 1);
    destroyList(filtered);

    // sortam lista crescator dupa suma
    ElemTypeList* sortedList = sortbysum(&list, 'c');
    c = get(sortedList, 0);
    assert(c->suma == 200);
    destroyList(sortedList);

    // sortam lista descrescator dupa suma
    sortedList = sortbysum(&list, 'd');
    c = get(sortedList, 0);
    assert(c->suma == 350);
    destroyList(sortedList);

    // sortam lista descrescator dupa tip
    sortedList = sortbyType(&list, 'd');
    c = get(sortedList, 0);
    assert(strcmp(c->tip, "transport") == 0);
    destroyList(sortedList);

    // sortam lista crescator dupa tip
    sortedList = sortbyType(&list, 'c');
    c = get(sortedList, 0);
    assert(strcmp(c->tip, "mancare") == 0);
    destroyList(sortedList);

    // distrugem obiectul
    destroyCheltuieliAndUndo(&list);
}

void testService()
{
    testAMDF();
}
