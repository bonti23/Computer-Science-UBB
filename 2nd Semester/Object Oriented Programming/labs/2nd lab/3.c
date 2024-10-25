#include <stdio.h>
 /**
  functie pt generarea unei secvente de termeni consecutivi astfel incat suma lor = numarul
  parametrii de intrare: x, contor
  tipul lor: int
  se incrementeaza contorul la fiecare pas, acesta adunandu-se la suma
  daca suma >= numarul iesim din structura repetitiva
  in cazul in care suma == numarul se returneaza contorul - 1(ultima incrementare)
  in caz contrar, se returneaza 0 = false
  */
int reprezentare(int x, int contor){
    int sum = 0;
    while (sum < x){
        sum = sum + contor;
        contor += 1;
    }
    if (sum == x)
        return contor-1;
    else
        return 0;
}

int main() {
    int n;
    printf("Introduceti un numar: ");
    scanf("%d", &n);
    if (n%2==0)
        printf("Nu exista reprezentari!");
    else{
        int contor = 0;
        while (contor<n){
            int k = reprezentare(n, contor);
            if (k!=0 && n!=0){
                for (int i=contor; i<=k; i++)
                    printf("%d ", i);
                printf("\n");
            }
            contor += 1;
        }
    }
    return 0;
}
