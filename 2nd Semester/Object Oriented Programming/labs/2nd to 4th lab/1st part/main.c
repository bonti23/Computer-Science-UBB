#include <stdio.h>
#include "domain.h"
#include "ui.h"
#include "teste.h"

int main() {
    struct ListaMea lista;
    lista.lungime = 0;
    test_all();
    run(&lista);
    return 0;
}
