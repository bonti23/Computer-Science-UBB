#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wvisibility"
#ifndef LAB2_4_SERVICE_H
#define LAB2_4_SERVICE_H

struct ListaMea creeazavid();
struct oferta creare(char tip[25], int suprafata, char adresa[25], int pret);
void adaugare(struct oferta oferta, struct ListaMea *lista);
void validare(char erori[100], int *ok, char tip[25], int suprafata, char adresa[25], int pret);
void validare_index(struct ListaMea *lista, int index, char erori[100], int* ok);
void modificare(struct ListaMea *lista, int index, char tip[25], int suprafata, char adresa[25], int pret);
void stergere(struct ListaMea *lista, int index);
void ordonare(struct ListaMea *lista, int ordonare);
struct ListaMea *filtrare(struct ListaMea *lista, char criteriu[20], char ce_fel[20]);

#endif //LAB2_4_SERVICE_H
#pragma clang diagnostic pop
