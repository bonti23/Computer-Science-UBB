//EN: Implement a program that simulates the following metaphor using threads and synchronization mechanisms. A group of 10 passengers go on a trip by airplane. The passengers climb into the airplane on an airstair (airplane stairs) that fits a maximum of 3 passengers, but they start climbing only after all passengers have arrived near the plane. When all passengers are in the airplanee, the pilot takes off.
#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond= PTHREAD_COND_INITIALIZER;
int contor=0; //pt a tine cont ca incap cate trei pe scara

void* functie(void* a){
	int cual=*(int*)a;
	pthread_mutex_lock(&mutex);//blocam pentru a fi accesata variabila contor de catre un singur thread
	while(contor>=3){//maxim 3 pe scara
		pthread_cond_wait(&cond, &mutex);
	}
	contor++;//a urcat cineva pe scara
	pthread_mutex_unlock(&mutex);
	printf("s-a urcat pasagerul %d! poate urca urmatorul!\n", cual);
	pthread_mutex_lock(&mutex);
	contor--;//acum e in avion
	pthread_cond_signal(&cond);
	pthread_mutex_unlock(&mutex);
	return NULL;
}
int main(int argc, char** argv){
	//rezolvarea se putea si cu bariere, si sa setam bariera la 3
	//mutexul are 2 atribute, acestea fiind lock si unlock pentru a sectiona partea critica
	pthread_t threaduri[11];
	for(int i=0; i<10; i++){//crearea threadurilor
		pthread_create(&threaduri[i], NULL, functie, &i);//retinem i-ul ca fiind un id al pasagerului
	}
	for(int i=0; i<10; i++){//incheierea lor
		pthread_join(threaduri[i], NULL);
	}
	printf("pilotul poate decola!\n");
	pthread_mutex_destroy(&mutex);//distrugerea mutexului
	return 0;
}
