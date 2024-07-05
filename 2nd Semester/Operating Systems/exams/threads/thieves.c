#include <stdlib.h>
#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

struct arg{
	int bani;
	int id;
};

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
int count = 0;

void* functie(void* a){
	struct arg* x = (struct arg*) a;
	struct arg* hoti = (struct arg*) x - x->id;
	for (int runda=1; runda<=10; runda++) {
        	pthread_mutex_lock(&mutex);
        	while(count>=3) {
            		pthread_cond_wait(&cond, &mutex);
        	}
        	count++;
        	pthread_mutex_unlock(&mutex);
        	int next_id = x->id+1;
        	struct arg* next = &hoti[next_id];
        	int suma_furata = rand() % (next->bani);
        	next->bani -= suma_furata;
        	x->bani += suma_furata;
        	pthread_mutex_lock(&mutex);
        	count--;
        	pthread_cond_signal(&cond);
        	pthread_mutex_unlock(&mutex);

		usleep(100);
        
    	}
	return NULL;
}

int main(int argc, char** argv){
	pthread_t threaduri[20];
	struct arg hoti[20];
	for(int i=0; i<20; i++){
		hoti[i].bani=100;
		hoti[i].id=i;
		pthread_create(&threaduri[i], NULL, functie, &hoti[i]);
	}
	for(int i=0; i<20; i++)
		pthread_join(threaduri[i], NULL);
	for (int i = 0; i <20; i++) {
        	printf("hot: %d suma: %d\n", i, hoti[i].bani);
    	}
	return 0;
}
