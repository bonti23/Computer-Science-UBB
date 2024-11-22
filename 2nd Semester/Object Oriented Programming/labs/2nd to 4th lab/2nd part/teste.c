#include <assert.h>
#include <stdio.h>
#include "service.h"
#include "lista_mea.h"
#include "domain.h"
#include "teste.h"
#include <stdlib.h>
#include <string.h>

// Rulează testele pentru domain
void teste_domain()
{
    printf("Start teste domain...\n");
    Oferta* test = creator_oferta("apartament", 3000, "ceahlau", 1200);
    assert(test->suprafata == 3000);
    assert(test->pret == 1200);
    assert(strcmp(test->tip, "apartament") == 0);
    assert(strcmp(test->adresa, "ceahlau") == 0);
    distruge_oferta(test);
    printf("Finish teste domain...\n");
}

// Rulează testele pentru repository
void teste_repo()
{
    printf("Start teste repo...\n");
    testCreateList();
    testIterateList();
    testCopyList();
    testResize();
    testListOfLists();
    testListOfInts();
    testRemoveLast();
    printf("Finish teste repo...\n");
}


// Rulează testele pentru service
void  teste_service()
{
    printf("Start teste service...\n");
    Service repo_test_service;
    repo_test_service = createService();

    service_add(&repo_test_service, "apartament", 3000, "ceahlau", 1200);
    Oferta *of = (Oferta*)get(repo_test_service.listaOferte, 0);
    assert(of->suprafata == 3000);
    assert(of->pret == 1200);
    assert(strcmp(of->tip, "apartament") == 0);
    assert(strcmp(of->adresa, "ceahlau") == 0);

    service_delete(&repo_test_service, 0);
    assert(size(repo_test_service.listaOferte) == 0);

    printf("Finish teste service...\n");
}

void test_all()
{
    teste_domain();
    //teste_repo();
    teste_service();
}
