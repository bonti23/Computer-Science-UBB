#ifndef INC_2ND_4TH_LAB_1ST_PART_DOMAIN_H
#define INC_2ND_4TH_LAB_1ST_PART_DOMAIN_H

struct oferta{
    char tip[25]; //teren, casa, apartament
    int suprafata;
    char adresa[25];
    int pret;
};

struct ListaMea{
    struct oferta lista_oferte[100];
    int lungime;
};

#endif //INC_2ND_4TH_LAB_1ST_PART_DOMAIN_H
