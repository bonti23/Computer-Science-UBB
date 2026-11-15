#include <iostream>
#include "DynamicMatrices.h"

int main() {
    cout << "=== Laborator 2 - Convolutie in-place ===\n";
    DynamicMatrices matrices(8); // 8 threads
    matrices.run();
    cout << "=== Program finished ===\n";
    return 0;
}
