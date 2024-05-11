#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>

int pid;

void f(int number){
	if(number>0){
		printf("id: %d\n", getpid());
		pid=fork();
		if (pid==-1){
			perror("fork incorect!");
			exit(1);
		}
		else if (pid==0){
			f(number-1);
		}
		wait(0);
	}
	exit(0);
}
int main(int argc, char** argv){
	int n;
	printf("Introduceti numarul de child processes pe care doriti sa le creati: ");
	scanf("%d",&n);
	f(n-1);
	return 0;
}
