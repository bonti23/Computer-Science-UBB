#include <iostream>
#include "TS.h"

int main() {
    TS ts;
    initTS(&ts);

    addSymbol(&ts, "a");
    addSymbol(&ts, "b");
    addSymbol(&ts, "c");

    printTS(&ts);
    return 0;
}
