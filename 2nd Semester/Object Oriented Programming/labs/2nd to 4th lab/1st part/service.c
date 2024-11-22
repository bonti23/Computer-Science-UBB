#include "service.h"
#include "domain.h"
#include "string.h"
#include "stdlib.h"

struct ListaMea vid(){
    struct ListaMea lista;
    lista.lungime=0;
    return lista;
}
/*
 * functie de tip oferta pentru ca voi returna o oferta
 * date de intrare: atributele unei oferte
 */
struct oferta create_service(char tip[25], int suprafata, char adresa[25], int pret){
    struct oferta oferta;
    strncpy(oferta.tip, tip, sizeof(oferta.tip)-1);
    oferta.tip[sizeof(oferta.tip)-1]='\0';
    oferta.suprafata=suprafata;
    strncpy(oferta.adresa, adresa, sizeof(adresa)-1);
    oferta.adresa[sizeof(oferta.adresa)-1]='\0';
    oferta.pret=pret;
    return oferta;
}

/*
 * adaug oferta in lista
 * am folosit pointer pentru a avea efect asupra listei respectie, nu pt a apela o copie
 * un pointer isi acceseaza datele prin ->
 */
void add_service(struct oferta oferta, struct ListaMea *lista){
    lista->lista_oferte[lista->lungime]=oferta;
    lista->lungime++;
}

void modify_service(struct ListaMea *lista, int index, char tip[25], int suprafata, char adresa[25], int pret){
    index--;
    struct oferta oferta;
    oferta = lista->lista_oferte[index];
    strcpy(oferta.tip, tip);
    oferta.suprafata=suprafata;
    strcpy(oferta.adresa, adresa);
    oferta.pret=pret;
    lista->lista_oferte[index]=oferta;
}

void delete_service(struct ListaMea *lista, int index){
    index--;
    for(int i=index; i<lista->lungime-1; i++){
        lista->lista_oferte[i]=lista->lista_oferte[i+1];
    }
    lista->lungime--;
}

/*
 * filtrez tot lista pe care o am, de aceea folosim pointeri
 */
struct ListaMea *filtrare_service(struct ListaMea* lista, char criteriu[25], char tipul[25]){
    //alocam memorie pentru lista 'filtrat'
    struct ListaMea *filtrat=(struct ListaMea*)malloc(sizeof(struct ListaMea));
    filtrat->lungime=0;
    if(strcmp(criteriu,"tip")==0){
        for(int i=0; i<lista->lungime; i++){
            if(strcmp(lista->lista_oferte[i].tip, tipul)==0){
                filtrat->lista_oferte[filtrat->lungime]=lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    if(strcmp(criteriu,"suprafata")==0){
        tipul=tipul-'0';
        for(int i=0; i<lista->lungime; i++){
            if(lista->lista_oferte[i].suprafata==tipul){
                filtrat->lista_oferte[filtrat->lungime]=lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    if(strcmp(criteriu,"adresa")==0){
        for(int i=0; i<lista->lungime; i++){
            if(strcmp(lista->lista_oferte[i].adresa, tipul)==0){
                filtrat->lista_oferte[filtrat->lungime]=lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    if(strcmp(criteriu,"pret")==0){
        tipul=tipul-'0';
        for(int i=0; i<lista->lungime; i++){
            if(lista->lista_oferte[i].pret==tipul){
                filtrat->lista_oferte[filtrat->lungime]=lista->lista_oferte[i];
                filtrat->lungime++;
            }
        }
    }
    return filtrat;
}

void ordonare_service(struct ListaMea* lista, char ordonare[20]){
    struct oferta pahar;
    if(strcmp(ordonare,"crescator")==0){
        for (int i=0; i<lista->lungime-1; i++)
            for (int j=i+1; j<lista->lungime; j++)
                if (lista->lista_oferte[i].pret>lista->lista_oferte[j].pret){
                    pahar=lista->lista_oferte[i];
                    lista->lista_oferte[i]=lista->lista_oferte[j];
                    lista->lista_oferte[j]=pahar;
                }
    }
    if(strcmp(ordonare,"descrescator")==0){
        for (int i=0; i<lista->lungime-1; i++)
            for (int j=i+1; j<lista->lungime; j++)
                if (lista->lista_oferte[i].pret<lista->lista_oferte[j].pret){
                    pahar=lista->lista_oferte[i];
                    lista->lista_oferte[i]=lista->lista_oferte[j];
                    lista->lista_oferte[j]=pahar;
                }
    }
}
void validate_service(char erori[100], char tip[25], int suprafata, char adresa[25], int pret){
    if(strcmp(tip, "teren")!=0 && strcmp(tip, "casa")!=0 && strcmp(tip, "apartament")!=0)
        strcat(erori,"Tip invalid!\n");
    if(suprafata<0)
        strcat(erori,"Suprafata invalida!\n");
    if(strcmp(adresa, "")==0)
        strcat(erori, "Adresa invalida!\n");
    if(pret<0)
        strcat(erori, "Pret invalid!\n");
}
