#include "ui.h"
#include "service.h"
#include "domain.h"
#include "stdio.h"
#include "stdlib.h"
#include "string.h"

void afiseaza_oferte(struct ListaMea* lista){
    struct oferta oferta;
    for(int i=0; i<lista->lungime; i++){
        oferta=lista->lista_oferte[i];
        printf("%s  %d  %s  %d\n", oferta.tip, oferta.suprafata, oferta.adresa, oferta.pret);
    }
}
void meniu(){
    printf("Agentie!\n");
    printf("1. Adauga o oferta noua.\n");
    printf("2. Modifica oferta.\n");
    printf("3. Sterge oferta.\n");
    printf("4. Filtrare oferte.\n");
    printf("5. Oferte ordonate.\n");
    printf("6. Inchide aplicatia.\n");
}
void creare_ui(struct ListaMea* lista){
    char tip[25];
    int suprafata;
    char adresa[25];
    int pret;
    printf("Tipul: ");
    scanf("%s", tip);
    printf("Suprafata: ");
    scanf("%d", &suprafata);
    printf("Adresa: ");
    scanf("%s", adresa);
    printf("Pretul: ");
    scanf("%d", &pret);
    char erori[100];
    validate_service(erori, tip, suprafata, adresa, pret);
    if (strcmp(erori, "")!=0)
        printf("%s", erori);
    else{
        struct oferta oferta = create_service(tip, suprafata, adresa, pret);
        add_service(oferta, lista);
        afiseaza_oferte(lista);
    }
}

void modificare_ui(struct ListaMea* lista){
    int ok=1;
    int index;
    char tip[25];
    int suprafata;
    char adresa[25];
    int pret;
    printf("Indexul ofertei de actualizat: ");
    scanf("%d",&index);
    printf("Tipul nou: ");
    scanf("%s",tip);
    printf("Suprafata noua: ");
    scanf("%d",&suprafata);
    printf("Adresa noua: ");
    scanf("%s",adresa);
    printf("Pretul nou: ");
    scanf("%d",&pret);

    char erori[100];
    validate_service(erori, tip, suprafata, adresa, pret);

    if(index>=lista->lungime || index<0){
        printf("Index invalid!");
        ok=0;
    }
    if(strcmp(erori, "")!=0){
        printf("%s", erori);
        ok=0;
    }
    if(ok==1){
        modify_service(lista, index, tip, suprafata, adresa, pret);
        afiseaza_oferte(lista);
    }
}

void stergere_ui(struct ListaMea *lista){
    int index;
    printf("Indexul ofertei de sters: ");
    scanf("%d", &index);
    int ok=1;
    if(index>=lista->lungime || index<0){
        printf("Index invalid!");
        ok=0;
    }
    if(ok == 1){
        delete_service(lista, index);
        if(lista->lungime != 0)
            afiseaza_oferte(lista);
        else
            printf("Lista este vida!\n");
    }
}

void filtreaza_ui(struct ListaMea *lista){
    char criteriu[25], tipul[25];
    printf("Criteriul: ");
    scanf("%s",criteriu);
    printf("Ce tip: ");
    scanf("%s",tipul);
    struct ListaMea *filtrat=filtrare_service(lista,criteriu,tipul);
    if(filtrat==NULL){
        printf("Nu s-au gasit oferte conform criteriului.\n");
        return;
    }
    printf("Oferte filtrate:\n");
    afiseaza_oferte(filtrat);
    free(filtrat);//dealocam
}

void ordoneaza_ui(struct ListaMea *lista){
    char cum[20];
    printf("Introduceti crescator/descrescator in functie de ordinea dorita.\n");
    printf("Optiunea: ");
    scanf("%s", cum);
    ordonare_service(lista, cum);
    afiseaza_oferte(lista);
}

void run(){
    struct ListaMea lista = vid();
    char optiune;
    meniu();
    while(1){
        printf("Introduceti optiunea: ");
        scanf("%s", optiune);
        if(optiune == '1')
            creare_ui(&lista);
        else if(optiune == '2')
            modificare_ui(&lista);
        else if(optiune == '3')
            stergere_ui(&lista);
        else if(optiune == '4')
            filtreaza_ui(&lista);
        else if(optiune == '5')
            ordoneaza_ui(&lista);
        else if(optiune == '6'){
            printf("Aplicatie inchisa.");
            break;
        }
        else
            printf("Optiune invalida!\n");
    }
}
