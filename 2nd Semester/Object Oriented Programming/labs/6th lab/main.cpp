#include <iostream>
#include "Repository.h"
#include "Validator.h"
#include "Service.h"
#include "Console.h"

int main() {
    Repository repo;
    Validator validator;
    Service service(repo, validator);
    Console ui(service);
    ui.start();
    return 0;
}