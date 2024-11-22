#include <assert.h>
#include <stdlib.h>
#include <string.h>
#include "domain.h"
#include "lista_mea.h"


/*
 * creeaza o lista goala
 * destroy - functie de destroy specifica elementelor
 * returneaza o lista goala de elemente de tip ElemType
 */

ListaMea* creeazaVid(DestroyFct destroy){
    ListaMea* lista = malloc(sizeof(ListaMea));
    lista->lg = 0; //lungimea listei
    lista->cp = 2;//
    lista->destroy = destroy;//distructorul
    lista->list = malloc(sizeof(ElemType) *lista->cp);//lista in sine
    return lista;
}
/*
 * distruge lista
 */
void destroyList(ListaMea* lista){
    if(lista == NULL)
        return;

    for(int i = 0; i < lista->lg; ++i) {
        if (lista->list[i] != NULL)
            lista->destroy(lista->list[i]);
    }
    free(lista->list);
    free(lista);
}
/*
 * ia un element de pe pozitia poz
 * poz - intreg
 * returneaza elementul de pe pozitia poz
 */
ElemType get(ListaMea* lista, int poz){
    return lista->list[poz];
}
/*
* modifica elementul de pe pozitia poz cu elementul p
* returneaza elementul rescris
*/
ElemType set(ListaMea* lista, int poz, ElemType p){
    ElemType rez = lista->list[poz];
    lista->list[poz] = p;
    return rez;
}
/*
 * returneaza dimensiunea listei
 */
int size(ListaMea* lista){
    return lista->lg;
}


/*
 * asigura ca exista destul loc in lista pentru adaugarea elementelor
 */
void ensureCapacity(ListaMea* lista){
    if(lista->lg < lista->cp)
        return;
    ElemType* nElems = malloc(sizeof(ElemType) *(lista->cp +4));
    for(int i=0; i<lista->lg; ++i)
        nElems[i] = lista->list[i];
    free(lista->list);
    lista->list = nElems;
    lista->cp = lista->cp + 4;
}
/*
 * adauga elementul p la finalul listei
 */
void add(ListaMea* lista, ElemType p){
    ensureCapacity(lista);
    lista->list[lista->lg] = p;
    lista->lg++;
}
/*
 * sterge ultimul element din lista
 * returneaza ultimul element din lista
 */
ElemType removeLast(ListaMea* lista){
    ElemType rez = lista->list[lista->lg -1];
    lista->lg-- ;
    return rez;
}
/*
 * sterge elementul de pe pozitia poz din lista
 * poz - intreg
 */
void removeP(ListaMea* lista, int poz){
    lista->destroy(lista->list[poz]);
    for(int i = poz; i<lista->lg - 1; ++i)
        lista->list[i] = lista->list[i+1];
    lista->lg--;
}
/*
 * face o copie a listei
 * returneaza o lista cu aceleasi elemente din lista l
 */
ListaMea* copyList(ListaMea* lista, CopyFct copy){
    ListaMea* copie = creeazaVid(lista->destroy);
    for(int i=0; i< size(lista); ++i){
        ElemType e = get(lista, i);
        add(copie, copy(e));
    }
    return copie;
}
void testCreateList() {
    ListaMea * l = creeazaVid((DestroyFct) distruge_oferta);
    assert(size(l) == 0);
    destroyList(l);
}
void testIterateList() {
    ListaMea * l = creeazaVid((DestroyFct) distruge_oferta);
    add(l, creator_oferta( "casa", 100, "ciresilor", 2000));
    add(l, creator_oferta( "apartament", 245, "viilor", 3000));
    assert(size(l) == 2);
    Oferta* p = (Oferta*)get(l,0);

    assert(strcmp(p->tip,"casa") == 0);
    p = (Oferta*)get(l, 1);
    assert(strcmp(p->adresa, "viilor") == 0);
    destroyList(l);
}
void testCopyList() {
    ListaMea * l = creeazaVid((DestroyFct) distruge_oferta);
    add(l, creator_oferta("casa", 100, "ciresilor", 2000));
    add(l, creator_oferta("apartament", 245, "viilor", 3000));
    ListaMea *l2 = copyList(l, (CopyFct)copyOferta);
    assert(size(l2) == 2);
    Oferta* p = (Oferta*)get(l2, 0);
    assert(strcmp(p->tip, "casa") == 0);
    destroyList(l);
    destroyList(l2);
    l2 = NULL;
}
void testResize() {
    ListaMea * l = creeazaVid((DestroyFct) distruge_oferta);
    for (int i = 0; i < 10; i++) {
        add(l, creator_oferta("apartament", 245, "viilor", 3000));
    }
    assert(size(l) == 10);
    destroyList(l);
}
void testListOfLists() {
    ListaMea *listOfLists = creeazaVid((DestroyFct)destroyList);
    ListaMea * listOfOferte = creeazaVid((DestroyFct) distruge_oferta);
    add(listOfOferte, creator_oferta("casa", 100, "ciresilor", 2000));
    add(listOfOferte, creator_oferta("apartament", 245, "viilor", 3000));
    add(listOfOferte, creator_oferta("teren", 1050, "mestecenilor", 4000));
    add(listOfLists, listOfOferte);
    ListaMea * cpy = copyList(listOfOferte, (CopyFct)copyOferta);
    add(listOfLists, cpy);
    assert(size(listOfLists) == 2);
    assert(size(listOfOferte) == 3);

    ListaMea * el2 = (ListaMea *)get(listOfLists, 1);
    assert(size(el2) == 3);
    removeP(listOfLists, 0);
    assert(size(listOfLists) == 1);

    destroyList(listOfLists);
}

int* createIntOnHeap(int value) {
    static int intValue[5]; // Alocare statică a unui array de 5 elemente întregi
    intValue[0] = value; // Setarea valorii în funcție de parametrul primit
    return intValue; // Returnarea pointerului către array-ul alocat static
}
void testListOfInts() {
    ListaMea * l = creeazaVid(free); // using stdlib free to free memory

    // Check for allocation failure
    if (l == NULL) {
        // Handle allocation failure
        return;
    }

    // Add integers to the list
    for (int i = 0; i < 5; ++i) {
        int* intValue = createIntOnHeap(i); // Allocate memory for integer i
        if (intValue == NULL) {
            // Handle allocation failure
            break;
        }
        add(l, intValue); // Add integer pointer to the list
    }

    // Freeing memory allocated for integers
    for (int i = 0; i < size(l); ++i) {
        free(get(l, i)); // Freeing the integer pointer stored in the list
    }

    destroyList(l); // Destroy the list after its use
}
void testRemoveLast() {
    ListaMea * l = creeazaVid((DestroyFct) distruge_oferta);
    add(l, creator_oferta("apartament", 245, "viilor", 3000));
    add(l, creator_oferta("teren", 1050, "mestecenilor", 4000));
    assert(size(l) == 2);
    Oferta* el = (Oferta*)removeLast(l);
    assert(size(l) == 1);
    distruge_oferta(el);

    el = (Oferta*)removeLast(l);
    assert(size(l) == 0);
    distruge_oferta(el);

    destroyList(l);
}
