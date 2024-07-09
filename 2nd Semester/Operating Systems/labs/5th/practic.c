#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;
int a[1000];
int frecventa[101]={0};
int prim(int x){
        if(x%2==0 && x!=2){
                return 0;
        }
        for(int i=3; i*i<=x; i=i+2){
                if(x%i==0)
                        return 0;
        }
        return 1;

}
void* functie(void* a){
        int cual=*(int*) a;
        pthread_mutex_lock(&mutex);
        if(prim(cual)==1 && cual>=0 && cual<100){
                frecventa[cual]++;
        }
        pthread_mutex_unlock(&mutex);
        free(a);
        return NULL;
}
int main(int argc, char** argv){
        pthread_t t[1000];
        int n = atoi(argv[1]);
        FILE *file=fopen("b.txt", "r");
        for(int i=1; i<=n; i++){
                fscanf(file, "%d", &a[i]);
                int* arg = malloc(sizeof(*arg));
                *arg=a[i];
                pthread_create(&t[i], NULL, functie, arg);
        }
        for(int i=1; i<=n; i++)
                pthread_join(t[i], NULL);
        int numere=0, suma=0;
        for(int i=0; i<100; i++){
                if(frecventa[i]!=0){
                        printf("numarul: %d cu frecventa: %d\n", i, frecventa[i]);
                        numere++;
                        suma=suma+i;
                }
        }
        int medie=suma/numere;
        printf("media: %d", medie);
        pthread_mutex_destroy(&mutex);
        return 0;
}
