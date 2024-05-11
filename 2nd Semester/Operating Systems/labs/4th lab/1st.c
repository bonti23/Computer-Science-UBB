#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>

int children[100];

int main(int argc, char** argv){
	int n, i;
	printf("Introduceti numarul de child processes pe care doriti sa le creati: ");
	scanf("%d",&n);
	if(argc!=1){
		perror("Introduceti exact un argument!");
        	exit(1);
	}
	for(i=0; i<n; i++){
		children[i]=fork();
		if(children[i]==0){
			//getppid() - functie predefinita care returneaza id-ul parintelui
			//getpid() - functie predefinita care returneaza id-ul procesului curent
			printf("id parinte: %d; id curent: %d\n", getppid(), getpid());
			exit(0);
		}
		else if(children[i]==-1){
            		perror("Eroare  fork!");
		}	
	}
	for(i=0; i<n; i++){
                wait(0);
        }
	printf("parintele: %d\n", getpid());
	printf("copiii sai: ");
	for(i=0; i<n; i++){
		printf("%d ", children[i]);
	}
	return 0;
}
