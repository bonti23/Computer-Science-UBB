#include "domain.h"
#include "lista_mea.h"
#include "service.h"
#include "string.h"

Service createService(){
    Service  rez;
    rez.listaOferte = creeazaVid((DestroyFct) distruge_oferta);
    return rez;
}

void destroyService(Service* service){
    destroyList(service->listaOferte);
}

void service_add(Service* service, char* tip, int suprafata, char *adresa, int pret){
    Oferta* oferta = creator_oferta(tip, suprafata, adresa, pret);
    add(service->listaOferte, oferta);
}

void service_delete(Service* service, int poz){
    removeP(service->listaOferte, poz);
}

void service_modificat(Service* service, int poz, char* tip, int suprafata, char *adresa, int pret){
    Oferta* oferta = creator_oferta(tip, suprafata, adresa, pret);
    Oferta* of = set(service->listaOferte, poz, oferta);
    distruge_oferta(of);
}

ListaMea* service_vezi_tip(Service* service, char* criteriu){
    ListaMea* repo_nou;
    repo_nou = creeazaVid((DestroyFct) distruge_oferta);
    int i;
    for(i=0; i<size(service->listaOferte); i++){
        Oferta* oferta = (Oferta *)get(service->listaOferte, i);
        if(strcmp(oferta->tip, criteriu) == 0){
            add(repo_nou, copyOferta(oferta));
        }
    }
    return repo_nou;
}

ListaMea* service_vezi_suprafata(Service *service, int suprafata, int ordine){
    ListaMea *repo_nou;
    repo_nou = creeazaVid((DestroyFct) distruge_oferta);
    int i;
    if(ordine == 1)//mai mari
    {
        for(i=0; i<size(service->listaOferte); i++)
        {
            Oferta *oferta = (Oferta*) get(service->listaOferte, i);
            if(oferta->suprafata >= suprafata)
                add(repo_nou, copyOferta(oferta));
        }
    }
    else if(ordine == 2)//mai mici
    {
        for(i=0; i<size(service->listaOferte); i++)
        {
            Oferta * oferta = (Oferta*) get(service->listaOferte, i);
            if(oferta->suprafata <= suprafata)
                add(repo_nou, copyOferta(oferta));
        }
    }
    return repo_nou;
}

ListaMea* service_selecteaza_sortare(Service* service, int criteriu, int ordine)
{
    int i, j;
    ListaMea* repo_nou;
    repo_nou = copyList(service->listaOferte, (CopyFct) copyOferta);
    for(i=0; i<size(service->listaOferte)-1; i++)
        for(j=i+1; j<size(service->listaOferte); j++)
        {
            Oferta *of1 = (Oferta*)get(repo_nou, i);
            Oferta *of2 = (Oferta*)get(repo_nou, j);
            if(criteriu == 1 && ordine == 1 && of1->suprafata > of2->suprafata && of1->suprafata != 0 && of2->suprafata !=0)
            {
                set(repo_nou, i, of2);
                set(repo_nou, j, of1);
            }
            else if(criteriu == 1 && ordine ==2 && of1->suprafata < of2->suprafata && of1->suprafata != 0 && of2->suprafata !=0)
            {
                set(repo_nou, i, of2);

                set(repo_nou, j, of1);
            }
            else if(criteriu == 2 && ordine == 1 && of1->pret >of2->pret && of1->pret !=0 && of2->pret != 0)
            {
                set(repo_nou, i, of2);
                set(repo_nou, j, of1);
            }
            else if(criteriu == 2 && ordine == 2 && of1->pret < of2->pret && of1->pret !=0 && of2->pret !=0) {
                set(repo_nou, i, of2);
                set(repo_nou, j, of1);
            }
        }
    return repo_nou;

}
