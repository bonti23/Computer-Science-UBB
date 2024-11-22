#include "implicit_input.h"
#include <string.h>

void addImplicit(ElemTypeList* l)
{
    Cheltuieli* newcheltuiala;
    char tip[25] = "mancare";
    newcheltuiala = createCheltuiala(22, 200, tip);
    adauga(l, newcheltuiala);

    strcpy(tip, "internet");
    newcheltuiala = createCheltuiala(23, 400, tip);
    adauga(l, newcheltuiala);

    strcpy(tip, "transport");
    newcheltuiala = createCheltuiala(15, 150, tip);
    adauga(l, newcheltuiala);

    strcpy(tip, "mancare");
    newcheltuiala = createCheltuiala(31, 70, tip);
    adauga(l, newcheltuiala);

    strcpy(tip, "telefon");
    newcheltuiala = createCheltuiala(1, 80, tip);
    adauga(l, newcheltuiala);
}
