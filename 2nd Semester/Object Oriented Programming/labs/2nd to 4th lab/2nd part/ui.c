#include "service.h"
#include "domain.h"
#include "stdio.h"
#include <string.h>
#include "lista_mea.h"
#include <stdbool.h>
#include <stdlib.h>


void print_menu(){
    printf("1. Adauga o oferta. \n");
    printf("2. Modifica o oferta. \n");
    printf("3. Sterge o oferta. \n");
    printf("4. Vizualizati oferta dupa un criteriu. \n");
    printf("5. Vizualizati ofertele ordonate.\n");
    printf("6. Vizualizati toate ofertele. \n");
    printf("0. Iesire din aplicatie. \n");
}

void ui_add(Service* repo_ui){
    int pret, suprafata;
    char* tip = (char*)malloc(100);
    char* adresa = (char*)malloc(100);
    bool intrare = true;
    while(intrare){
        printf("Introduceti tipul ofertei (casa/apartament/teren): ");
        scanf("%s", tip);
        printf("Introduceti suprafata ofertei: ");
        scanf("%d", &suprafata);
        printf("Introduceti adresa ofertei : ");
        scanf("%s", adresa);
        printf("Introduceti pretul ofertei: ");
        scanf("%d", &pret);
        if(strcmp(tip, "casa") == 0 || strcmp(tip, "apartament") == 0 || strcmp(tip, "teren") == 0)
            intrare = false;
        else printf("Tip invalid!\n");
    }
    service_add(repo_ui, tip, suprafata, adresa, pret);
    free(tip);
    free(adresa);
    printf("Oferta adaugata cu succes! \n");
}

void ui_delete(Service* repo_ui){
    int poz;
    printf("Introduceti pozitia ofertei pe care doriti sa o stergeti: ");
    scanf("%d", &poz);
    service_delete(repo_ui, poz);
    printf("Oferta a fost stearsa cu succes");
}

void ui_modifica(Service* repo){
    int poz, pret, suprafata;
    char* tip = (char*)malloc(100);
    char* adresa = (char*)malloc(100);
    bool intrare = true;
    while(intrare){
        printf("Introduceti pozitia ofertei pe care doriti sa o modificati: ");
        scanf("%d", &poz);
        printf("Introduceti tipul ofertei: ");
        scanf("%s", tip);
        printf("Introduceti suprafata tranzactiei: ");
        scanf("%d", &suprafata);
        printf("Introduceti adresa ofertei: ");
        scanf("%s", adresa);
        printf("Introduceti pretul ofertei: ");
        scanf("%d", &pret);
        if(strcmp(tip, "casa") == 0 || strcmp(tip, "apartament") == 0 || strcmp(tip, "teren") == 0)
            intrare = false;
        else printf("Tip invalid!\n");
        service_modificat(repo, poz, tip, suprafata, adresa, pret);
        printf("Oferta modificata cu succes!\n");
    }
    free(tip);
    free(adresa);
}

void ui_print(Service* repo) {
    int i;
    int nr = 0;
    for (i = 0; i < size(repo->listaOferte); i++) {
        Oferta *of = (Oferta *) get(repo->listaOferte, i);
        if (of->suprafata != 0) {
            printf("%d. %s %d %s %d \n", i, of->tip, of->suprafata, of->adresa, of->pret);
            nr++;
        }
        if (nr == 0)
            printf("Nu exista oferte!\n");
    }
}
void ui_printeaza_dupa_tip(Service* repo){
    char* tip = (char*)malloc(100);
    ListaMea* repo_nou;
    int i;
    int nr = 0;
    bool run = true;
    while(run){
        printf("Alegeti tipul (casa/apartament/teren): ");
        scanf("%s", tip);
        if(strcmp(tip, "casa") == 0 || strcmp(tip, "apartament") == 0 || strcmp(tip, "teren") == 0){
            run = false;
            repo_nou = service_vezi_tip(repo, tip);
        }
        else printf("Tip invalid!\n");
    }
    for(i=0; i<size(repo_nou); i++){
        Oferta* of = (Oferta*) get(repo_nou, i);
        if(of->suprafata != 0){
            printf("%d. %s %d %s %d \n", i, of->tip, of->suprafata, of->adresa, of->pret);
            nr++;
        }
    }
    if(nr==0) printf("Nu exista oferte cu acest tip!\n");

}

void ui_printeaza_dupa_suprafata(Service* repo){
    int ordine;
    bool run = true;
    int i;
    int suprafata;
    int nr = 0;
    ListaMea* repo_nou;
    printf("Introduceti suprafata: ");
    scanf("%d", &suprafata);
    printf("Introduceti ordinea(1.Mai mare/2.Mai mic): ");
    while(run){
        scanf("%d", &ordine);
        if(ordine == 1 || ordine == 2){
            run = false;
            repo_nou = service_vezi_suprafata(repo, suprafata, ordine);
        }
        else printf("Optiune invalida!\n");
    }
    for (i=0; i<size(repo_nou); i++){
        Oferta *of = (Oferta*)get(repo_nou, i);
        if(of->suprafata != 0){
            printf("%d. %s %d %s %d \n", i, of->tip, of->suprafata, of->adresa, of->pret);
            nr++;
        }
    }
    if(nr == 0) printf("Nu exista oferte cu aceasta suprafata!\n");
}

void ui_selecteaza_sortarea(Service* repo_ui, int criteriu, int ordine){
    ListaMea* repo_nou;
    repo_nou = service_selecteaza_sortare(repo_ui, criteriu, ordine);
    int i;
    int nr = 0;
    for(i=0; i<size(repo_nou); i++){
        Oferta *of = (Oferta*)get(repo_nou, i);
        if(of->suprafata !=0){
            printf("%d. %s %d %s %d \n", i, of->tip, of->suprafata, of->adresa, of->pret);
            nr++;
        }
    }
    if(nr==0) printf("Nu exista oferte!\n");
}

void run(){
    Service repo;
    repo = createService();
    int status = 1;
    int status_criteriu;
    int optiune_criteriu;
    int status_ordine;
    int optiune;
    int ordine;
    int criteriu;
    print_menu();
    while (status) {
        printf("Optiunea introdusa: ");
        scanf("%d", &optiune);
        if (optiune == 0) {
            status = 0;
            printf("Exiting...");
        } else if (optiune == 1)
            ui_add(&repo);
        else if (optiune == 2)
            ui_modifica(&repo);
        else if (optiune == 3)
            ui_delete(&repo);
        else if (optiune == 4) {
            status_criteriu = 1;
            while (status_criteriu) {
                printf("1. Vizualizati dupa tip: \n2. Vizualizati in functie de suprafata: \n");
                scanf("%d", &optiune_criteriu);
                if (optiune_criteriu == 1) {
                    status_criteriu = 0;
                    ui_printeaza_dupa_tip(&repo);

                } else if (optiune_criteriu == 2) {
                    status_criteriu = 0;
                    ui_printeaza_dupa_suprafata(&repo);

                } else printf("Optiune inexistenta!\n");
            }
        } else if (optiune == 5) {
            status_criteriu = 1;
            while (status_criteriu) {
                printf("Sortati dupa criteriu (1. Dupa suprafata / 2. Dupa pret): \n");
                scanf("%d", &criteriu);
                if (criteriu == 1 || criteriu == 2) {
                    status_criteriu = 0;
                    status_ordine = 1;
                    while (status_ordine) {
                        printf("Ordine sortare (1. Crescator / 2. Descrescator): \n");
                        scanf("%d", &ordine);
                        if (ordine == 1 || ordine == 2)
                        {
                            status_ordine = 0;
                            ui_selecteaza_sortarea(&repo, criteriu, ordine);
                        }
                        else printf( "Ordine invalida!\n");
                    }

                }
                else printf("Criteriu invalid!\n");
            }
        } else if (optiune == 6)
            ui_print(&repo);
        else printf("Optiune invalida!\n");


    }
    destroyService(&repo);
}
