#ifndef LAB2_4_SERVICE_H
#define LAB2_4_SERVICE_H

#include "lista_mea.h"

typedef struct service{
    ListaMea* listaOferte;
} Service;

Service createService();

void destroyService(Service* service);

void service_add(Service* service, char* tip, int suprafata, char *adresa, int pret);

void service_delete(Service* service, int poz);

void service_modificat(Service* service, int poz, char* tip, int suprafata, char *adresa, int pret);

ListaMea* service_vezi_tip(Service* service, char* criteriu);

ListaMea* service_vezi_suprafata(Service* service, int criteriu, int ordine);

ListaMea* service_selecteaza_sortare(Service* service, int criteriu, int ordine);

#endif //LAB2_4_SERVICE_H
