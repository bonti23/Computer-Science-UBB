#include <iostream>
#include "Matrice.h"
#include "TestExtins.h"
#include "TestScurt.h"

using namespace std;


int main() {
    Matrice matrice(2, 2);
    testAll();
	testAllExtins();
    cout<<"End";
    matrice.modifica(0, 0, 1);
    matrice.modifica(0, 1, 2);
    matrice.modifica(1, 0, 3);
    matrice.modifica(1, 1, 4);
    matrice.afisareMatrice();
}
