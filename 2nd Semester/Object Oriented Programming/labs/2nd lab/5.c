
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

/**
 functie pentru fisarea numerelor naturale <=un numar in ordine inversa pt un numar prim
 parametrii de intrare: k
 tip k: int
 */
void pprim(int k){
    for (int i=k; i>=1; i-=1)
        printf("%d,", i);
    printf(" ");
}

/**
 functie pentru afisarea numarului si a divizorilor acestuia + de atatea ori cat reprezinta numarul lor pt un numar neprim
 date de intrare: k
 tip k: int
 */
void neprim(int k){
    printf("%d,", k);
    for (int i=2; i<k; i+=1)
        if (k%i==0){
            int copy = i;
            while(copy){
                printf("%d,", i);
                copy -= 1;
            }
        }
    printf(" ");
}

int main() {
    int n;
    printf("Intoduceti numarul: ");
    scanf("%d", &n);
    for (int i=1; i<=n; i++)
        if (prim(i)==1)
            pprim(i);
        else
            neprim(i);
    return 0;
}
