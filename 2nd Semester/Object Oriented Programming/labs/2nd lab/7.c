#include <stdio.h>
#include <math.h>

/**
 functie care verifica daca un numar este patrat perfect
 parametrii de intrare: n
 tip n: int
 */
int radacina(int n){
    int k = sqrt(n);
    if (k*k == n)
        return 1;
    else
        return 0;
}
int main() {
    int n;
    printf("Intoduceti un numar: ");
    scanf("%d", &n);
    int k = sqrt(n), m;
    if (radacina(n)==1)
        printf("%d este radacina patrata a lui %d.", k, n);
    else{
        m = k + 1;
        printf("Intre %d si %d se afla radacina patrata a numarului %d.", k, m, n);
    }
    return 0;
}
