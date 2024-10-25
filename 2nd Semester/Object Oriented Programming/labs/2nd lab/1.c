#include <stdio.h>
//& address
//in functie cu * pt adresa = adresa spre care arata valoarea
//* pointer spre
//** dublu pointer =
//typedef struct student Stud
//sizeof
int main() {
    printf("Hello World!\n");
    int n, sum=0, m;
    printf("Introduceti un numar: ");
    scanf("%d", &n);
    for(int i=1; i<=n; i++){
        scanf("%d", &m);
        sum = sum + m;
    }
    printf("%d", sum);
    return 0;
}
