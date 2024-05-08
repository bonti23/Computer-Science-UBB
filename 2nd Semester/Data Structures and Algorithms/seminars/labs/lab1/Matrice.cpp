#include "Matrice.h"
#include "VectorDinamic.h"
#include <stdexcept>
#include <iostream>

using namespace std;
//complexitate: O(m)
Matrice::Matrice(int n, int m)
        : Linie(1), Valoare(1) {
    if (m < 0 || n < 0) {
        throw std::runtime_error("valori invalide!");
    }
    nrLin = n;
    nrCol = m;
    Coloana = new int[nrCol + 1];
    for (int i = 0; i <= nrCol; ++i) {
        Coloana[i] = 0;
    }
}

//complexitate: O(1)
Matrice::~Matrice() {
    delete[] Coloana;
}

//complexitate: O(1)
int Matrice::nrLinii() const{
    return nrLin;
}

//complexitate: O(1)
int Matrice::nrColoane() const{
    return nrCol;
}

//complexitate: O(n)
//returnare element pozitie specificata
TElem Matrice::element(int i, int j) const {
    if (i < 0 || i > nrLin || j < 0 || j > nrCol) {
        throw std::runtime_error("coordonate invalide!");
    }
    for (int k = Coloana[i]; k < Coloana[i + 1]; ++k) {
        if (Linie.element(k) == j) {
            return Valoare.element(k);
        }
    }
    return NULL_TELEMENT;
}

//complexitate: O(n^2)
TElem Matrice::modifica(int i, int j, TElem e) {
    if (i < 0 || i > nrLin || j < 0 || j > nrCol) {
        throw std::runtime_error("coordonate invalide!");
    }
    int k;
    bool passed = false;
    for (k = Coloana[i]; k < Coloana[i + 1]; ++k) {
        passed = true;
        if (Linie.element(k) >= j) {
            break;
        }
    }
    if (passed && Linie.element(k) == j) {
        TElem valoareVeche = Valoare.element(k);
        if (e == NULL_TELEMENT) {
            for (int j = i; j < nrCol; j++) {
                Coloana[j]--;
            }
            Linie.sterge(k);
            Valoare.sterge(k);
        }
        else {
            Valoare.modifica(k, e);
        }
        return valoareVeche;
    }

    if (e != NULL_TELEMENT) {
        Linie.adauga(k, j);
        Valoare.adauga(k, e);

        for (int p = i + 1; p <= nrLin; ++p) {
            Coloana[p]++;
        }
    }

    return NULL_TELEMENT;
}

//complexitate O(n^2)
Matrice Matrice::transpunere() const{
    Matrice transpusa(nrCol, nrLin);
    for (int i = 0; i < nrLin; i++) {
        for (int j = Coloana[i]; j < Coloana[i + 1]; j++) {
            int col = Linie.element(j);
            TElem val = Valoare.element(j);
            transpusa.modifica(col, i, val);
        }
    }
    return transpusa;
}

void Matrice::afisareMatrice() {
    for (int i = 0; i < this->nrLinii(); ++i) {
        for (int j = 0; j < this->nrColoane(); ++j) {
            std::cout << this->element(j, i) << " ";
        }
        std::cout << std::endl;
    }
}
