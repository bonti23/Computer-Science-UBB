//
// Created by Alexandra Bonțidean on 19.03.2024.
//

#ifndef LAB2_4_LISTA_MEA_H
#define LAB2_4_LISTA_MEA_H
typedef void* ElemType;
typedef void(*DestroyFct) (ElemType);
typedef ElemType(*CopyFct) (ElemType);

typedef struct lista{
    int lg, cp;
    ElemType* list;
    DestroyFct destroy;
}ListaMea;

ListaMea* creeazaVid(DestroyFct destroy);
void destroyList(ListaMea* lista);
ElemType get(ListaMea* lista, int poz);
ElemType set(ListaMea* lista, int poz, ElemType p);
int size(ListaMea* lista);
void add(ListaMea* lista, ElemType p);
ElemType removeLast(ListaMea* lista);
void removeP(ListaMea* lista, int poz);
ListaMea* copyList(ListaMea* lista, CopyFct copy);

void testCreateList();
void testIterateList();
void testCopyList();
void testResize();
void testListOfLists();
void testListOfInts();
void testRemoveLast();
#endif //LAB2_4_LISTA_MEA_H
