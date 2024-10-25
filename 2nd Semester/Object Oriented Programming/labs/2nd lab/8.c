#include <stdio.h>
#include <math.h>

/**
 functie care returneaza la ce putere se afla p in descompunerea in factori primi a numarului n
 parametrii de intrare: n, p
 tip n, p: int
 return i de tip int
 */
int putere(int n, int p){
    int i=0;
    while (n!=1 && n%p==0){
        n = n/p;
        i++;
    }
    return i;
}
int main() {
    int n, p;
    printf("Intoduceti un numar: ");
    scanf("%d", &n);
    printf("Introduceti un numar prim: ");
    scanf("%d", &p);
    int exponent = putere(n, p);
    printf("%d", exponent);
    return 0;
}
