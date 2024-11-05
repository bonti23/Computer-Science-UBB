
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
  char sir2[100];
  char interclasat[200];
  
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
  
  printf("sirul 1: ");
  fgets(sir, sizeof(sir), stdin);
  sir[strcspn(sir, "\n")] = '\0';
  printf("sirul 2: ");
  fgets(sir2, sizeof(sir2), stdin);
  sir2[strcspn(sir2, "\n")] = '\0';

  send(c, &sir, strlen(sir)+1, 0);
  send(c, &sir2, strlen(sir2)+1, 0);
  recv(c, interclasat, sizeof(interclasat), 0);
  printf("Sirul interclasat este: %s\n", interclasat);
  close(c);
  return 0;
}
