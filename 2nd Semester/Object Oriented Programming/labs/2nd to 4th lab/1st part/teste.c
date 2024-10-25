#include "service.h"
#include "domain.h"
#include "assert.h"
#include "string.h"
#include "stdio.h"

void test_creeazaVid() {
    struct ListaMea lista = creeazavid();
    assert(lista.lungime == 0);
}

void test_creare_oferta_service() {
    struct oferta o = creare("teren", 50, "mestecenilor", 15000);
    assert(strcmp(o.tip, "teren")==0);
    assert(o.suprafata==(int)50);
    assert(strcmp(o.adresa, "mestecenilor") == 0 );
    assert(o.pret == (int)15000);
}

void test_adauga_oferta_service() {
    struct ListaMea lista = creeazavid();
    struct oferta o = creare("teren", 50, "mestecenilor", 15000);
    adaugare(o, &lista);
    assert(lista.lungime == 1);
}

void test_validare() {
    char erori1[100] = "";
    int ok = 1;

    validare(erori1, &ok, "teren", 50, "mestecenilor", 15000);
    assert(ok == 1);

    validare(erori1, &ok, "masina", 50, "mestecenilor", 15000);
    assert(ok == 0);

}

void test_validare_index() {
    struct ListaMea lista = {{0}, 0};
    struct oferta oferta = creare("teren", 50, "mestecenilor", 15000);
    adaugare(oferta, &lista);

    int ok = 1;
    char erori[100] = "";

    validare_index(&lista, 1, erori, &ok);
    assert(ok == 1);

    validare_index(&lista, 2, erori, &ok);//mai mare decat len de lista
    assert(ok == 0);

    validare_index(&lista, 0, erori, &ok);// -||- mai mic
    assert(ok == 0);
}

void test_modificare_service(){
    struct ListaMea lista;
    lista.lungime = 0;
    struct oferta oferta = creare("teren", 50, "mestecenilor", 15000);
    adaugare(oferta, &lista);
    modificare(&lista, 1, "apartament", 500, "caisilor", 100);

    assert(lista.lungime == 1);
    assert(strcmp(lista.lista_oferte[0].tip, "apartament")==0);
    assert(lista.lista_oferte[0].suprafata == (int)500);
    assert(strcmp(lista.lista_oferte[0].adresa, "caisilor") == 0);
    assert(lista.lista_oferte[0].pret == (int)100);
}

void test_stergere(){
    struct ListaMea lista;
    lista.lungime = 0;
    struct oferta oferta1 = creare("teren", 50, "mestecenilor", 15000);
    adaugare(oferta1, &lista);
    struct oferta oferta2 = creare("apartament", 500, "caisilor", 100);
    adaugare(oferta2, &lista);
    stergere(&lista, 1);
    assert(lista.lungime == 1);
    assert(strcmp(lista.lista_oferte[0].tip, "apartament")==0);
    assert(lista.lista_oferte[0].suprafata == (int)500);
    assert(strcmp(lista.lista_oferte[0].adresa, "caisilor") == 0);
    assert(lista.lista_oferte[0].pret == (int)100);
}

void test_all(){
    test_creeazaVid();
    test_creare_oferta_service();
    test_adauga_oferta_service();
    test_validare();
    test_validare_index();
    test_modificare_service();
    test_stergere();
}
