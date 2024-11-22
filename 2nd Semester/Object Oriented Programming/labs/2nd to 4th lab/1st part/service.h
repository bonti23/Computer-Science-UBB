#ifndef INC_2ND_4TH_LAB_1ST_PART_SERVICE_H
#define INC_2ND_4TH_LAB_1ST_PART_SERVICE_H
struct ListaMea vid();
struct oferta create_service(char tip[25], int suprafata, char adresa[25], int pret);
void add_service(struct oferta oferta, struct ListaMea *lista);
void modify_service(struct ListaMea *lista, int index, char tip[25], int suprafata, char adresa[25], int pret);
void delete_service(struct ListaMea *lista, int index);
struct ListaMea *filtrare_service(struct ListaMea* lista, char criteriu[25], char tipul[25]);
void ordonare_service(struct ListaMea* lista, char ordonare[20]);
void validate_service(char erori[100], char tip[25], int suprafata, char adresa[25], int pret);
#endif //INC_2ND_4TH_LAB_1ST_PART_SERVICE_H
