#include <stdio.h>

/**
 functie pentru un numar prim
 parametrii de intrare: x
 tipul acestuia: int
 daca x este orice numar <= 1 se returneaza 0 = false
 daca x ete un numar par, dar diferit de 2 se returneaza 0 = false
 daca x este un numar impar cu divizori prorpii se returneaza 0 = false
 pentru niciunul din cazurile de mai sus, se returneaza 1 = true
 */
int prim(int x){
    if (x<=1)
        return 0;
    if (x%2==0 && x!=2)
        return 0;
    for (int i=3; i*i<=x; i+=2)
        if (x%i==0)
            return 0;
    return 1;
}

int main() {
    int n;
    printf("Introduceti un numar: ");
    scanf("%d", &n);
    if (n>=1){
        printf("2 ");
        n -= 1;
    }
    int x=3;
    while (n){
        if (prim(x)==1){
            printf("%d ", x);
            n -= 1;
        }
        x += 2;
    }
    return 0;
}
