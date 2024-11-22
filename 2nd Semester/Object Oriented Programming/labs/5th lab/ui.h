#pragma once
#include "ListaMea.h"
#include "service.h"

// apelarea tuturor testelor
void callAllTests();

// printarea meniului principal
void printmainmenu();

// afisarea listei de cheltuieli
void afisare(ElemTypeList* l);

// adaugarea unei cheltuieli
void addcheltuiala(CheltuieliAndUndo* ch);

// stergerea unei cheltuieli
void stergecheltuiala(CheltuieliAndUndo* ch);

// modificarea unei cheltuieli
void modificacheltuiala(CheltuieliAndUndo* ch);

// sortarea cheltuielilor dupa tip
void sortbytip(CheltuieliAndUndo* list);

// sortarea cheltuielilor dupa suma
void sortbysuma(CheltuieliAndUndo* list);

// filtrarea cheltuielilor dupa suma
void filterbysuma(CheltuieliAndUndo* list);

// filtrarea cheltuielilor dupa zi
void filterbyzi(CheltuieliAndUndo* list);

// filtrarea cheltuielilor dupa tip
void filterbytip(CheltuieliAndUndo* list);

// filtrarea cheltuielilor dupa zile pare
void filterbyzipara(CheltuieliAndUndo* list);

// pornirea aplicatiei
void startApp();
