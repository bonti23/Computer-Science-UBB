#ifndef LAB2_4_DOMAIN_H
#define LAB2_4_DOMAIN_H

//structul care tine locul unui "obiect" de tip oferta
//param tip: tipul ofertei (casa/apartament/teren)
//type: string
//param suprafata: suprafata ofertei
//type: int
//param adresa: adresa ofertei
//type: string
//param pret: pretul ofertei
//type: int
typedef struct oferta{
    char* tip;
    int suprafata;
    char* adresa;
    int pret;
}Oferta;

Oferta* creator_oferta(char* tip, int suprafata, char *adresa, int pret);
void distruge_oferta(Oferta* oferta);
Oferta* copyOferta(Oferta* oferta);

#endif //LAB2_4_DOMAIN_H
