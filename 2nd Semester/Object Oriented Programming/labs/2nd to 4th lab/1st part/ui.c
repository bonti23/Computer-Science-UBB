#include "service.h"
#include "domain.h"
#include "stdio.h"
#include "stdlib.h"
#include "teste.h"

void afisare_lista_oferte(struct ListaMea *lista){
    struct oferta oferta;
    int index = lista->lungime;
    printf("Lista: \n");
    for (int i=0; i<index; i++){
        oferta = lista-> lista_oferte[i];
        printf("tipul: %s; suprafata: %d; adresa: %s; pretul: %ld", oferta.tip, oferta.suprafata, oferta.adresa, oferta.pret);
        printf("\n");
    }
}

void menu(){
    printf("Agentie de imobiliare! \n");
    printf("1. Adauga o oferta noua.\n");
    printf("2. Modifica oferta.\n");
    printf("3. Sterge oferta.\n");
    printf("4. Filtrare oferte.\n");
    printf("5. Oferte ordonate.\n");
    printf("6. Inchide aplicatia.\n");
    printf("Alegeti optiunea: \n");
}

void creare_oferta_ui(struct ListaMea *lista){
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
    int ok=1;
    validare(erori, &ok, tip, suprafata, adresa, pret);
    if (ok==0)
        printf("%s", erori);
    else{
        struct oferta oferta = creare(tip, suprafata, adresa, pret);
        adaugare(oferta, lista);
        afisare_lista_oferte(lista);
    }
}

void modificare_oferta_ui(struct ListaMea *lista){
    int index;
    char tip[25];
    int suprafata;
    char adresa[25];
    int pret;

    printf("Indexul ofertei de actualizat: ");
    scanf("%d", &index);
    printf("Tipul nou: ");
    scanf("%s", tip);
    printf("Suprafata noua: ");
    scanf("%d", &suprafata);
    printf("Adresa noua: ");
    scanf("%s", adresa);
    printf("Pretul nou: ");
    scanf("%d", &pret);

    char erori[100];
    int ok = 1;

    validare_index(lista, index, erori, &ok);
    validare(erori, &ok, tip, suprafata, adresa, pret);

    if(ok == 0)
        printf("%s", erori);
    else{
        modificare(lista, index, tip, suprafata, adresa, pret);
        afisare_lista_oferte(lista);
    }
}
void stergere_oferta_ui(struct ListaMea *lista){
    int index;
    printf("Indexul ofertei de sters: ");
    scanf("%d", &index);

    char erori[100];
    int ok = 1;
    validare_index(lista, index, erori, &ok);

    if(ok == 1){
        stergere(lista, index);
        if(lista->lungime != 0)
            afisare_lista_oferte(lista);
        else
            printf("Lista este vida!\n");
    }
    else
        printf("%s", erori);
}

void filtrare_ui(struct ListaMea *lista){
    char criteriu[25], ce_fel[25];
    printf("Criteriul: ");
    scanf("%s", criteriu);
    printf("Ce tip: ");
    scanf("%s", ce_fel);
    struct ListaMea *filtrat = filtrare(lista, criteriu, ce_fel);
    if (filtrat == NULL) {
        printf("Nu s-au gasit oferte conform criteriului.\n");
        return;
    }
    printf("Oferte filtrate:\n");
    afisare_lista_oferte(filtrat);

    free(filtrat);
}

void ordonare_ui(struct ListaMea *lista){
    int cum;
    printf("Introduceti 0 pentru ordonarea crescatoare sau 1 pentru ordonara descrescatoare.\n");
    printf("Optiunea: ");
    scanf("%d", &cum);
    ordonare(lista, cum);
    afisare_lista_oferte(lista);
}

void run(){
    test_all();
    struct ListaMea lista = creeazavid();
    char optiune;
    menu();
    while(1){
        printf(">>> ");
        scanf("%s", &optiune);
        if(optiune == '1')
            creare_oferta_ui(&lista);
        else if(optiune == '2')
            modificare_oferta_ui(&lista);
        else if(optiune == '3')
            stergere_oferta_ui(&lista);
        else if(optiune == '4')
            filtrare_ui(&lista);
        else if(optiune == '5')
            ordonare_ui(&lista);
        else if(optiune == '6'){
            printf("Aplicatie inchisa.");
            break;
        }
        else
            printf("Optiune invalida!\n");
    }

}
