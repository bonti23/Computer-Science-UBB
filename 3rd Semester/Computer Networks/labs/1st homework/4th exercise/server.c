#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
 
int main() {
  int s;
  struct sockaddr_in server, client;
  int c, l;
  
  s = socket(AF_INET, SOCK_STREAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
  
  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }
 
  listen(s, 5);
  
  l = sizeof(client);
  memset(&client, 0, sizeof(client));
  
  while (1) {
    char sir[100];
    char sir2[100];
    char interclasat[200];
    c = accept(s, (struct sockaddr *) &client, &l);
    printf("S-a conectat un client.\n");
    // deservirea clientului
    recv(c, &sir, sizeof(sir), 0);
    recv(c, &sir2, sizeof(sir2), 0);
    int len=strlen(sir);
    int len2=strlen(sir2);
    int i=0;
    int j=0;
    int k=0;
    while(i<=len-1 && j<=len2-1){
       if(sir[i]<sir2[j]){
           interclasat[k]=sir[i];
           i++;
           k++;
       }
       else{
           interclasat[k]=sir2[j];
           j++;
           k++;
       }
    }
    while(i<=len-1){
           interclasat[k]=sir[i];
           i++;
           k++;
    }
    while(j<=len2-1){
           interclasat[k]=sir2[j];
           j++;
           k++;
    }
    send(c, interclasat, strlen(interclasat)+1, 0);
    close(c);
    // sfarsitul deservirii clientului;
  }
  close(s);
  return 0;
}


