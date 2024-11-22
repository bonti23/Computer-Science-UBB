#include <stdio.h>
#include "cheltuieli.h"
#include "Validatori.h"
#include "implicit_input.h"
#include "ListaMea.h"
#include "service.h"

void callAllTests()
{
    testCheltuieli();
    testElemTypeList();
    testService();
    testValidate();
}

void printmainmenu()
{
    printf("1. Adauga cheltuiala\n2. Sterge cheltuiala\n3. Modifica cheltuiala\n");
    printf("4. Filtrati cheltuielile dupa suma\n");
    printf("5. Filtrati cheltuielile dupa zi\n");
    printf("6. Filtrati cheltuielile dupa tip\n");
    printf("7. Filtrati cheltuielile dupa zile pare\n");
    printf("8. Sortati cheltuielile dupa suma\n");
    printf("9. Sortati cheltuielile dupa tip\n");
    printf("10. Undo ultima operatie de adaugare / modificare / stergere\n");
    printf("11. Arata toate cheltuielile.\n0. Iesire\n");
}

void afisare(ElemTypeList* l)
{
    printf("Lista cheltuielilor:\n");
    for (int i = 0; i < l->lg; i++)
    {
        printf(" Cheltuiala %d: ", i + 1);
        Cheltuieli* c = get(l, i);
        printf("tip %s; ziua %d; suma %d.\n", c->tip, c->ziua, c->suma);
    }
}

void addcheltuiala(CheltuieliAndUndo* ch) {
    char tip[100];
    printf("Dati tipul cheltuielii: ");
    scanf(" %s", tip);
    while (validateTip(tip)) {
        printf("Tipul nu este corect! Dati un tip: ");
        scanf(" %s", tip);
    }
    int ziua;
    printf("Dati ziua in care s-a efectuat cheltuiala: ");
    scanf("%d", &ziua);
    while (validateZiua(ziua))
    {
        printf("Ziua nu se incadreaza intr-o luna! Dati o zi corecta: ");
        scanf("%d", &ziua);
    }
    int suma;
    printf("Dati suma cheltuita: ");
    scanf("%d", &suma);
    while (validateSuma(suma))
    {
        printf("Suma cheltuita trebuie sa fie un intreg pozitiv! Dati o noua suma: ");
        scanf("%d", &suma);
    }

    addCheltuiala(ch, ziua, suma, tip);
}

void stergecheltuiala(CheltuieliAndUndo* ch)
{
    int numar;
    printf("Dati pozitia cheltuielii care trebuie stearsa: ");
    scanf("%d", &numar);
    while (numar<0 || numar>lungime(ch->allCheltuieli))
    {
        printf("Pozitia nu exista! Alegeti o pozitie existenta: ");
        scanf("%d", &numar);
    }
    stergeCheltuiala(ch, numar - 1);
    printf("Cheltuiala %d a fost stearsa!\n", numar);
}

void modificacheltuiala(CheltuieliAndUndo* ch)
{
    int nr;
    printf("Dati pozitia cheltuielii pe care o modificati: ");
    scanf("%d", &nr);
    while (nr<0 || nr>lungime(ch->allCheltuieli))
    {
        printf("Cheltuiala nu exista! Alegeti o pozitie existenta: ");
        scanf("%d", &nr);
    }
    char newtip[100];
    printf("Dati tipul nou al cheltuielii: ");
    scanf("%s", newtip);
    while (validateTip(newtip)) {
        printf("Tipul nu este corect! Dati un tip: ");
        scanf("%s", newtip);
    }
    int ziua, suma;
    printf("Dati ziua noua: ");
    scanf("%d", &ziua);
    while (validateZiua(ziua))
    {
        printf("Ziua nu se incadreaza intr-o luna! Dati o zi corecta: ");
        scanf("%d", &ziua);
    }
    printf("Dati suma nou: ");
    scanf("%d", &suma);
    while (validateSuma(suma)) {
        printf("Suma cheltuita trebuie sa fie un intreg pozitiv! Dati o noua suma: ");
        scanf("%d", &suma);
    }
    modificaCheltuiala(ch, nr - 1, ziua, suma, newtip);
    printf("Cheltuiala %d a fost modificata\n", nr);
}

void sortbytip(CheltuieliAndUndo* list)
{
    char mode;
    printf("Introduceti d pentru sortare descrescatoare, respectiv c pentru sortare crescatoare: ");
    scanf(" %c", &mode);
    if (mode == 'd' || mode == 'c')
    {
        ElemTypeList* allCheltuieli = sortbyType(list, mode);
        afisare(allCheltuieli);
        destroyList(allCheltuieli);
    }
    else printf("Mod invalid!\n");
}

void sortbysuma(CheltuieliAndUndo* list)
{
    char mode;
    printf("Introduceti d pentru sortare descrescatoare, respectiv c pentru sortare crescatoare: ");
    scanf(" %c", &mode);
    if (mode == 'd' || mode == 'c')
    {
        ElemTypeList* allCheltuieli = sortbysum(list, mode);
        afisare(allCheltuieli);
        destroyList(allCheltuieli);
    }
    else printf("Mod invalid!\n");
}

void filterbysuma(CheltuieliAndUndo* list)
{
    int sum;
    printf("introduceti suma dupa care doriti sa filtrati: ");
    scanf("%d", &sum);
    ElemTypeList* new_list = filtersum(list, sum);
    afisare(new_list);
    destroyList(new_list);
}

void filterbyzi(CheltuieliAndUndo* list)
{
    int zi;
    printf("introduceti ziua dupa care doriti sa filtrati: ");
    scanf("%d", &zi);
    ElemTypeList* new_list = filterzi(list, zi);
    afisare(new_list);
    destroyList(new_list);
}

void filterbytip(CheltuieliAndUndo* list)
{
    char tip[20];
    printf("introduceti tipul dupa care doriti sa filtrati: ");
    scanf("%s", tip);
    ElemTypeList* new_list = filtertip(list, tip);
    afisare(new_list);
    destroyList(new_list);
}

void filterbyzipara(CheltuieliAndUndo* list)
{
    ElemTypeList* new_list = filterzipara(list);
    afisare(new_list);
    destroyList(new_list);
}

void startApp() {
    CheltuieliAndUndo list = createCheltuieliAndUndo();
    addImplicit(list.allCheltuieli);
    afisare(list.allCheltuieli);
    printmainmenu();
    while (1) {
        printf("Comanda: ");
        int cmd;
        scanf("%d", &cmd);
        if (cmd == 0)
            break;
        else if (cmd == 1)
        {
            addcheltuiala(&list);
            //afisare(list.allCheltuieli);
        }
        else if (cmd == 2)
        {
            stergecheltuiala(&list);
            //afisare(list.allCheltuieli);
        }
        else if (cmd == 3)
        {
            modificacheltuiala(&list);
            //afisare(list.allCheltuieli);
        }
        else if (cmd == 4)
        {
            filterbysuma(&list);
        }
        else if (cmd == 5)
        {
            filterbyzi(&list);
        }
        else if (cmd == 6)
        {
            filterbytip(&list);
        }
        else if (cmd == 7)
        {
            filterbyzipara(&list);
        }
        else if (cmd == 8)
        {
            sortbysuma(&list);
        }
        else if (cmd == 9)
        {
            sortbytip(&list);
        }
        else if (cmd == 10)
        {
            if (undo(&list) != 0)
                printf("Nu se mai poate face undo!\n");
            else
                afisare(list.allCheltuieli);
        }
        else if (cmd == 11)
        {
            afisare(list.allCheltuieli);
        }
    }
    destroyCheltuieliAndUndo(&list);
    //int* x = malloc(sizeof(int));
}
