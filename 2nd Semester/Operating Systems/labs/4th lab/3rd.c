#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>

int pid;

void child(int sig){
  printf("Child process terminating...\n");
  exit(0);
}

void parent(int sig){
  printf("Parent process terminating...\n");
  kill(pid, SIGUSR1);
  wait(0);
  exit(0);
}

int main(int argc, char **argv){
	printf("parent's id: %d\n", getpid());
	pid = fork();
	if (pid==-1){
    		perror("Error on fork");
  }
	else if (pid==0){
		    printf("child's id: %d\n", getpid());
    		signal(SIGUSR1, child);
  }
  else{
    	  signal(SIGUSR1, parent);
    		while(1);
	}
  	return 0;
}
