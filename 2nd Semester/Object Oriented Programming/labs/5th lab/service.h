#pragma once

#include "ListaMea.h"

// obiectul care retine lista de cheltuieli si cea de undo
typedef struct {
    ElemTypeList* allCheltuieli;
    ElemTypeList* undoList;
} CheltuieliAndUndo;

// crearea obiectului care retine lista de cheltuieli si cea de undo
CheltuieliAndUndo createCheltuieliAndUndo();

// distrugerea obiectului care retine lista de cheltuieli si cea de undo
void destroyCheltuieliAndUndo(CheltuieliAndUndo* lista);

// adaugarea unei cheltuieli in lista de cheltuieli si salvarea starii precedente in lista de undo
void addCheltuiala(CheltuieliAndUndo* lista, int ziua, int suma, char tip[]);

// modificarea unei cheltuieli din lista de cheltuieli si salvarea starii precedente in lista de undo
void modificaCheltuiala(CheltuieliAndUndo* lista, int poz, int ziua, int suma, char tip[]);

// stergerea unei cheltuieli din lista de cheltuieli si salvarea starii precedente in lista de undo
void stergeCheltuiala(CheltuieliAndUndo* lista, int poz);

// revenirea la starea precedenta
int undo(CheltuieliAndUndo* lista);

// sortarea listei de cheltuieli dupa suma
ElemTypeList* sortbysum(CheltuieliAndUndo* lista, char mode);

// sortarea listei de cheltuieli dupa tip
ElemTypeList* sortbyType(CheltuieliAndUndo* lista, char mode);

// sortarea listei de cheltuieli dupa zi
ElemTypeList* filterzi(CheltuieliAndUndo* lista, int zi);

// filtrarea listei de cheltuieli dupa zile pare
ElemTypeList* filterzipara(CheltuieliAndUndo* lista);

// filtrarea listei de cheltuieli dupa suma
ElemTypeList* filtersum(CheltuieliAndUndo* lista, int suma);

// filtrarea listei de cheltuieli dupa tip
ElemTypeList* filtertip(CheltuieliAndUndo* lista, char* tip);

// teste pentru service
void testService();
