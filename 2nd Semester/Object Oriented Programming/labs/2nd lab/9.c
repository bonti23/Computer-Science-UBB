#include <stdio.h>
#include <math.h>

/**
 functie care numara cifrele egale cu 0 din finalul numarului
 date de intrare: n
 tip n: int
 returneaza numarul acestora
 return contor
 tip contor: int
 */
int cifreNule(int n){
    int contor = 0;
    while (n%10==0 && n!=0){
        contor+=1;
        n=n/10;
    }
    return contor;
}
int main() {
    int n, sum=0, contor;
    while(scanf("%d", &n) && n!=0){
        contor = cifreNule(n);
        sum = sum + contor;
    }
    printf("%d", sum);
    return 0;
}
