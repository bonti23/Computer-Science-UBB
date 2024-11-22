#include "domain.h"
#include <string.h>
#include <stdlib.h>

/*
 * creeaza o oferta cu string-ul tip, int-ul suprafata, strinng-ul adresa si int-ul pret
 * returneaza o oferta cu string-ul tip, int-ul suprafata, strinng-ul adresa si int-ul pret
 */

Oferta* creator_oferta(char* tip, int suprafata, char *adresa, int pret){
    Oferta *oferta = malloc(sizeof(Oferta));//alocam spatiu pt un obiect de tip Oferta
    oferta->tip = malloc(strlen(tip) + 1);
    strncpy(oferta->tip, tip, strlen(tip) + 1);
    oferta->suprafata = suprafata;
    oferta->adresa = malloc(strlen(adresa) + 1);
    strncpy(oferta->adresa, adresa, strlen(adresa) + 1);
    oferta->pret = pret;
    return oferta;
}

/*
 * distruge o oferta
 */
void distruge_oferta(Oferta* oferta)
{
    free(oferta->tip);
    free(oferta->adresa);
    free(oferta);
}

/*
 * copiaza o oferta
 * returneaza o copie a ofertei oferta
 */
Oferta* copyOferta(Oferta* oferta){
    return creator_oferta(oferta->tip, oferta->suprafata, oferta->adresa, oferta->pret);
}
