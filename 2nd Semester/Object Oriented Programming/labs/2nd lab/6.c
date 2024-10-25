#include <stdio.h>

/**
 functie care returneaza combinarile de n luate cate k
 daca n=k=0 => return 1 (0!=1)
 daca n>0 si k=0 => n!/(0!*n!) = 1 => return 1
 altfel, avem:
     n!
-----------
 (n-k)!*k!
 se simplifica de la 1 pana la k la numarator => vom avea: (k+1)*(k+2)*...*n
 la numitor vom avea (n-k)!, pt ca k! s-a simplificat
 => impartim numaratorul la numitor si avem rezuktatul in triunghi
 */
int C(int n, int k){
    int prod=1;
    if (n==0 && k==0)
        return 1;
    else
        if (n>0 && k==0){
            return 1;
        }
        else{
            //ce ramane sus nesimplificat
            for (int i=k+1; i<=n; i++)
                prod *=i;
            int prod2 = 1;
            //(n-k)!
            for (int i=1; i<=n-k; i++)
                prod2 *= i;
            return prod/prod2;
        }
}

int main() {
    int n;
    printf("Intoduceti un numar: ");
    scanf("%d", &n);
    int contorN = 0;
    while(contorN<=n){
        for (int j=0; j<=contorN; j+=1){
            int el = C(contorN, j);
            printf("%d ", el);
        }
        printf("\n");
        contorN +=1;
    }

    return 0;
}
