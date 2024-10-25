#include "stdio.h"
#include "domain.h"
#include "string.h"
#include "stdlib.h"

struct ListaMea creeazavid(){
    struct ListaMea lista;
    lista.lungime = 0;
    return lista;
}

struct oferta creare(char tip[25], int suprafata, char adresa[25], int pret){
    struct oferta oferta;
    strncpy(oferta.tip, tip, sizeof(oferta.tip)-1);
    oferta.tip[sizeof(oferta.tip)-1] ='\0';
    oferta.suprafata = suprafata;
    strncpy(oferta.adresa, adresa, sizeof(oferta.adresa)-1);
    oferta.adresa[sizeof(oferta.adresa)-1] = '\0';
    oferta.pret = pret;
    return oferta;
}

void adaugare(struct oferta oferta, struct ListaMea *lista){
    lista -> lista_oferte[lista->lungime] = oferta;
    lista -> lungime++;
}

void validare(char erori[100], int *ok, char tip[25], int suprafata, char adresa[25], int pret){
    if (strcmp(tip, "teren") != 0 && strcmp(tip, "casa") != 0 && strcmp(tip, "apartament") != 0){
        strcat(erori, "Tip invalid!\n");
        *ok = 0;
    }
    if (suprafata<0){
        strcat(erori, "Suprafata invalida!\n");
        *ok = 0;
    }
    if (strcmp(adresa, "") == 0){
        strcat(erori, "Adresa invalida!\n");
        *ok = 0;
    }
    if (pret<0){
        strcat(erori, "Pret invalid!\n");
        *ok = 0;
    }
}

void validare_index(struct ListaMea *lista, int index, char erori[100], int* ok){
    if(index> lista->lungime || index < 1)
        strcat(erori, "Index out of range!\n"), *ok = 0;
}

void modificare(struct ListaMea *lista, int index, char tip[25], int suprafata, char adresa[25], int pret){
    index--;
    struct oferta oferta;
    oferta = lista->lista_oferte[index];
    strcpy(oferta.tip, tip);
    oferta.suprafata = suprafata;
    strcpy(oferta.adresa, adresa);
    oferta.pret = pret;
    lista->lista_oferte[index] = oferta;
}

void stergere(struct ListaMea *lista, int index){
    index--;
    for (int i=index; i<lista->lungime-1; i++)
        lista->lista_oferte[i]=lista->lista_oferte[i+1];
    lista->lungime--;
}

struct ListaMea *filtrare(struct ListaMea *lista, char criteriu[20], char ce_fel[20]){
    struct ListaMea *filtrat = (struct ListaMea *)malloc(sizeof(struct ListaMea));
    filtrat->lungime = 0;
    if (strcmp(criteriu, "suprafata") == 0) {
        ce_fel = ce_fel - '0';
        for (int i = 0; i < lista->lungime; i++) {
            if (lista->lista_oferte[i].suprafata == ce_fel) {
                filtrat->lista_oferte[filtrat->lungime] = lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    else if (strcmp(criteriu, "tip") == 0) {
        for (int i = 0; i < lista->lungime; i++) {
            if (strcmp(lista->lista_oferte[i].tip, ce_fel) == 0) {
                filtrat->lista_oferte[filtrat->lungime] = lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    else if (strcmp(criteriu, "pret") == 0) {
        ce_fel = ce_fel - '0';
        for (int i = 0; i < lista->lungime; i++) {
            if (lista->lista_oferte[i].pret == ce_fel) {
                filtrat->lista_oferte[filtrat->lungime] = lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    return filtrat;
}

void ordonare(struct ListaMea *lista, int ordonare){
    struct oferta cup;
    if (ordonare==0){//crescator
        for (int i=0; i<lista->lungime-1; i++)
            for (int j=i+1; j<lista->lungime; j++)
                if (lista->lista_oferte[i].pret > lista->lista_oferte[j].pret){
                    cup = lista->lista_oferte[i];
                    lista->lista_oferte[i] = lista->lista_oferte[j];
                    lista->lista_oferte[j] = cup;
                }
    }
    else{//descrescator
        for (int i=0; i<lista->lungime-1; i++)
            for (int j=i+1; j<lista->lungime; j++)
                if (lista->lista_oferte[i].pret <= lista->lista_oferte[j].pret){
                    cup = lista->lista_oferte[i];
                    lista->lista_oferte[i] = lista->lista_oferte[j];
                    lista->lista_oferte[j] = cup;
                }
    }
}
