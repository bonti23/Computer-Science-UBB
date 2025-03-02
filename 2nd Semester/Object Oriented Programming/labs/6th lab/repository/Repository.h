
#include <iostream>
#include <string>
#include <vector>
#include "Contract.h"

#ifndef LABORATOR_6_OOP_REPOSITORY_H
#define LABORATOR_6_OOP_REPOSITORY_H

using namespace std;

class Repository{
private:
    vector<Contract> all;
    bool exist(const Contract &contract) const;
public:
    Repository()=default;

    Repository(const Repository& repo)=delete;

    void store(const Contract& contract);
    const Contract& find(string name, string type) const;
    const vector<Contract>& getAll() const noexcept;
};

class RepositoryException {
    string messages;
public:
    RepositoryException(string m) :messages{ m } {}
    friend ostream& operator<<(ostream& out, const RepositoryException& exception);
};
#endif //LABORATOR_6_OOP_REPOSITORY_H
