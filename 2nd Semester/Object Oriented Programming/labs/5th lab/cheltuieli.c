#include "cheltuieli.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>

Cheltuieli* createCheltuiala(int ziua, int suma, char tip[])
{
    int nrC = (int)strlen(tip) + 1;
    Cheltuieli* rez = malloc(sizeof(Cheltuieli));
    if (rez != NULL)
    {
        rez->ziua = ziua;
        rez->suma = suma;
        rez->tip = malloc(sizeof(char) * nrC);
        if (rez->tip != NULL)
            strcpy(rez->tip, tip);
    }
    return rez;
}

void destroyCheltuiala(Cheltuieli* c)
{
    free(c->tip);
    free(c);
}

Cheltuieli* copyCheltuiala(Cheltuieli* c)
{
    return createCheltuiala(c->ziua, c->suma, c->tip);
}

void testCheltuieli()
{
    Cheltuieli* testCheltuiala = createCheltuiala(20, 200, "transport");
    assert (testCheltuiala->ziua == 20);
    assert (testCheltuiala->suma == 200);
    assert (strcmp(testCheltuiala->tip, "transport") == 0);

    Cheltuieli* copieCheltuiala = copyCheltuiala(testCheltuiala);
    assert (copieCheltuiala->ziua == 20);
    assert (copieCheltuiala->suma == 200);
    assert (strcmp(copieCheltuiala->tip, "transport") == 0);

    destroyCheltuiala(copieCheltuiala);
    destroyCheltuiala(testCheltuiala);
}
