
#ifndef LABORATOR_6_OOP_SERVICE_H
#define LABORATOR_6_OOP_SERVICE_H

#include "Contract.h"
#include "Repository.h"
#include <vector>
#include <string>
#include <functional>
#include "Validator.h"

using namespace std;

class Service{
private:
    Repository& repo;
    Validator& validator;

    vector<Contract> sortare(bool(*smaller)(const Contract&, const Contract&));
    vector<Contract> filtreaza(function<bool(const Contract&)> fct);

public:

    Service(Repository& repo, Validator& validator) noexcept :
        repo(repo),
        validator(validator){}

    Service(const Service& service) = delete;

    const vector<Contract>& getAll() noexcept {
        return repo.getAll();
    }
    void addContract(const string& name, int hours, const string& type, const string& professor);

    vector<Contract> sortByName();

    vector<Contract> sortByProfessor();

    vector<Contract> sortByTypeHours();

    vector<Contract> filterProfessor(const string& professor);

    vector<Contract> filterHours(int hours) noexcept;
};

#endif //LABORATOR_6_OOP_SERVICE_H
