#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wvisibility"
#ifndef LAB2_4_UI_H
#define LAB2_4_UI_H
void afisare_lista_oferte(struct ListaMea *lista);
void menu();
void creare_oferta_ui(struct ListaMea *lista);
void modificare_oferta_ui(struct ListaMea *lista);
void stergere_oferta_ui(struct ListaMea *lista);
void ordonare_ui(struct ListaMea *lista);
void run();
#endif //LAB2_4_UI_H
#pragma clang diagnostic pop
