#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

int a[100][100], n, m;
int sume[100]={0};
void* sum(void* arg){
	int rand = *(int*)arg;
	int sum=0;
	for(int j=1; j<=m; j++){
		sum=sum+a[rand][j];
	}
	sume[rand]=sum;
	free(arg);
	return NULL;
}
int main(int argc, char** argv){
	FILE *file = fopen("a.txt", "r");
	fscanf(file, "%d", &n);//nr de linii
	fscanf(file, "%d", &m);//nr de coloane
	for(int i=1; i<=n; i++){
		for(int j=1; j<=m; j++){
			fscanf(file, "%d", &a[i][j]);
		}
	}
	pthread_t threads[m];
	for(int i=1; i<=n; i++){
		int* arg = malloc(sizeof(*arg));
		*arg = i;
		pthread_create(&threads[i], NULL, sum, arg);
	}
	for(int i=1; i<=n; i++){
                pthread_join(threads[i], NULL);
        }
	for(int i=1; i<=n; i++)
		printf("%d\n", sume[i]);
	return 0;
}
