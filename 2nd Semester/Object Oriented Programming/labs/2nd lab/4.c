#include <stdio.h>

/**
 functie pentru afisarea primelor y cifre de dupa , ale numarului x
 x - double
 y - int
 inmultim succesiv x cu 10 pentru trecerea unei cifre de dupa , inaintea ei
 o afisam
 */
void cifre(double x, int y){
    int cifra, z;
    while (y){
        x = x*10;
        z = x;
        cifra = z % 10;
        y -= 1;
        printf("%d ", cifra);
    }
}

int main() {
    // k/m
    int k, m, n;
    scanf("%d %d %d", &k, &m, &n);
    double result = (double) k/m;
    cifre(result, n);
    return 0;
}
