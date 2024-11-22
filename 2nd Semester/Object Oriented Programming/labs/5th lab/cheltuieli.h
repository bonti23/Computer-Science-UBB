#pragma once

// Obiectul de tip cheltuiala
typedef struct {
    /*
    * ziua - ziua in care s-a efectuat cheltuiala
    * suma - suma platita
    * *tip - tipul cheltuielii
    */

    int ziua;
    int suma;
    char* tip;
} Cheltuieli;

/*
* Creeaza un obiect de tip cheltuiala
* returneaza un pointer la cheltuiala creata
* int ziua - ziua in care s-a efectuat cheltuiala
* int suma - suma platita
* char *tip - tipul cheltuielii
*/
Cheltuieli* createCheltuiala(int ziua, int suma, char tip[]);

/*
* Dealoca memoria ocupata de un obiect de tip cheltuiala
*/
void destroyCheltuiala(Cheltuieli* c);

/*
* Creeaza o copie (deep copy - copiaza toate atributele) pentru cheltuiala
*/
Cheltuieli* copyCheltuiala(Cheltuieli* c);

/*
* Teste pentru cheltuieli
*/
void testCheltuieli();
