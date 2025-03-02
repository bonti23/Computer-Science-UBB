#ifndef LABORATOR_6_OOP_CONSOLE_H
#define LABORATOR_6_OOP_CONSOLE_H
#include "Service.h"

class Console {
    Service& service;

    void add();
    void print(const vector<Contract>& contracts);

public:
    Console(Service& service) noexcept:service(service) {
    }
    Console(const Console& console) = delete;

    void start();

};
#endif //LABORATOR_6_OOP_CONSOLE_H
