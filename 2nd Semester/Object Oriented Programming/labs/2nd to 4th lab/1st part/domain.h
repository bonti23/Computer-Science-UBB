#ifndef LAB2_4_OFERTA_H
#define LAB2_4_OFERTA_H
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
#endif //LAB2_4_OFERTA_H
