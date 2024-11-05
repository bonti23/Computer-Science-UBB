
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
 
int main() {
  int c;
  struct sockaddr_in server;
  char sir[100];
  char oglindit[100];
  
  c = socket(AF_INET, SOCK_STREAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr("127.0.0.1");
  
  if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }
  
  printf("sirul : ");
  fgets(sir, sizeof(sir), stdin);
  sir[strcspn(sir, "\n")]='\0';

  send(c, &sir, strlen(sir)+1, 0);
  recv(c, oglindit, sizeof(oglindit), 0);
  printf("Sirul oglindit este: %s\n", oglindit);
  close(c);
  return 0;
}
