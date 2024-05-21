#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t theMutex;
char theString[128] = {0};
int stringuriGenerate = 0;
int stringuriDeGenerat; //O sa fie setat in main
char getRandom()
{
	int cateLitere = 'z' - 'a' + 1;
	char randCharIndex = rand() % (cateLitere) + 'a';
	return randCharIndex;
}

void* printerThread(void* f)
{
	while(1)
	{
		char shouldStop = 0;
		pthread_mutex_lock(&theMutex);
		char shouldPrint = 1;
	        for (int i=0;i<128;i++)
	        {
		        if (theString[i] == 0)
        	        {
				shouldPrint = 0;
				break;
			}
		}

		if (shouldPrint != 0)
		{
			for (int i=0;i<128;i++)
                	{
				printf("%c",theString[i]);
				theString[i] = 0;
			}
			printf("\n");
			stringuriGenerate++;
			if (stringuriGenerate == stringuriDeGenerat)
			{
				shouldStop = 1;
			}
		}
        	pthread_mutex_unlock(&theMutex);
		if (shouldStop == 1) break;
	}
	return NULL;
}
void* generatorThread(void* f)
{
	while(1)
	{
		char shouldStop = 0;
		pthread_mutex_lock(&theMutex);
		if (stringuriGenerate == stringuriDeGenerat)
		{
			shouldStop = 1;
		}
		else
		{
			for (int i=0;i<128;i++)
			{
				if (theString[i] == 0)
				{
					//printf("%d a pus ceva\n",f);
					theString[i] = getRandom();
					break;
				}
			}
		}
		pthread_mutex_unlock(&theMutex);
		if (shouldStop == 1)
		{
			break;
		}
	}
	return NULL;
}
int main(int argc, char** argv)
{
	srand(time(NULL));
	if (argc != 1+2)
	{
		printf("Trebuie doua\n");
		return 1;
	}
	
	int n = atoi(argv[1]);
	int m = atoi(argv[2]);
	
	stringuriDeGenerat = m;
	pthread_t theThreads[n];
	pthread_mutex_init(&theMutex,NULL);

	for (int i=0;i<n;i++)
	{
		pthread_create(&theThreads[i],NULL,&generatorThread,i);
	}
	pthread_t printerThread_var;
	pthread_create(&printerThread_var,NULL,&printerThread,NULL);

	for (int i=0;i<n;i++)
	{
		pthread_join(theThreads[i],NULL);
	}
	pthread_join(printerThread_var,NULL);
}
