
#ifndef LAB2_4_UI_H
#define LAB2_4_UI_H

#include "service.h"

void print_menu();

void ui_add(Service* repo_ui);

void ui_delete(Service* repo_ui);

void ui_modifica(Service* repo);

void ui_print(Service* repo);

void ui_printeaza_dupa_tip(Service * repo);

void ui_printeaza_dupa_suprafata(Service* repo);

void ui_selecteaza_sortarea(Service* repo_ui, int crietriu, int ordine);

void ui_undo(Service* service);

void run();

#endif //LAB2_4_UI_H
