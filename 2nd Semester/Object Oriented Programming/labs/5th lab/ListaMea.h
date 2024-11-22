#pragma once
#include "cheltuieli.h"

// elementele sunt de tip void*
typedef void* ElemType;

// pointer la functia care sterge un element
typedef void(*DestroyFct) (ElemType);

// pointer la functia care copiaza un element
typedef ElemType(*CopyFct) (ElemType);

// lista de cheltuieli
typedef struct {
    ElemType* elems;
    int lg, cap;
    DestroyFct dfnc;
} ElemTypeList;

/*
* Creeaza o lista vida
*/
ElemTypeList* creeazaVid(DestroyFct f);

/*
* Distruge lista
* eliberam zona de memorie alocata listei de cheltuieli
* ElemTypeList* l - pointer catre lista de cheltuieli
*/
void destroyList(ElemTypeList* l);

// getter pentru un element din lista
ElemType get(ElemTypeList* l, int poz);

// setter pentru un element din lista
ElemType modifica(ElemTypeList* l, int poz, ElemType p);

// returneaza lungimea listei
int lungime(ElemTypeList* l);

/*
* Adauga unei cheltuieli in vector
* ElemTypeList* l - pointer catre lista de cheltuieli
* Cheltuieli c - cheltuiala pe care o adaugam in vector
*/
void adauga(ElemTypeList* l, ElemType el);

// stergem un element din lista
ElemType sterge(ElemTypeList* l, int poz);

// facem o copie shallow listei
ElemTypeList* copyList(ElemTypeList* l, CopyFct cf);

// teste pentru lista
void testElemTypeList();
