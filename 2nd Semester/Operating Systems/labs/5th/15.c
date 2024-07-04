#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <ctype.h>

struct arg {
    char* str;
    int litere;
    int cifre;
    int speciale;
};

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
int total_litere = 0;
int total_cifre = 0;
int total_speciale = 0;

void* functie(void* arg) {
    struct arg* data = (struct arg*)arg;
    data->litere = 0;
    data->cifre = 0;
    data->speciale = 0;

    char* str = data->str;
    for (int i = 0; str[i] != '\0'; i++) {
        if (isalpha(str[i])) {
            data->litere++;
        } else if (isdigit(str[i])) {
            data->cifre++;
        } else {
            data->speciale++;
        }
    }

    pthread_mutex_lock(&mutex);
    total_litere += data->litere;
    total_cifre += data->cifre;
    total_speciale += data->speciale;
    pthread_mutex_unlock(&mutex);

    return NULL;
}

int main(int argc, char* argv[]) {
    pthread_t threads[argc];
    struct arg stringuri[argc];

    for (int i = 1; i < argc; i++) {
        stringuri[i].str = argv[i];
        pthread_create(&threads[i], NULL, functie, &stringuri[i]);
    }

    for (int i = 1; i < argc; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("Total litere: %d\n", total_litere);
    printf("Total cifre: %d\n", total_cifre);
    printf("Total caractere speciale: %d\n", total_speciale);

    pthread_mutex_destroy(&mutex);

    return 0;
}
